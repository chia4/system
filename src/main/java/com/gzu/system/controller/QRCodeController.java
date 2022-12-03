package com.gzu.system.controller;

import com.gzu.system.pojo.People;
import com.gzu.system.pojo.Place;
import com.gzu.system.pojo.UserLogin;
import com.gzu.system.service.PeopleService;
import com.gzu.system.service.PlaceService;
import com.gzu.system.service.UserLoginService;
import com.gzu.system.service.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
public class QRCodeController {
    @Autowired
    UtilService utilService;
    @Autowired
    UserLoginService userLoginService;
    @Autowired
    PeopleService peopleService;
    @Autowired
    PlaceService placeService;

    /**
     * 所有二维码扫描后将进入这个uri
     */
    @GetMapping("/qrcode")
    public String AfterScanning(
            @RequestParam("username") String scannedUsername, HttpSession session, RedirectAttributes redirectAttributes,
            HttpServletRequest request
    ) {
        // 根据cookie获取扫描者用户类型
        HashMap<String, String> userLoginMap = (HashMap<String, String>) session.getAttribute("userLoginMap");
        String scannerUserType = userLoginMap.get("userType");

        // 将英文枚举类型转换成中文，方便后面扫描类型组合错误报错给用户
        HashMap<String, String> userTypeTranslation = new HashMap<>();
        userTypeTranslation.put("PEOPLE", "大众");
        userTypeTranslation.put("PLACE", "场所");
        userTypeTranslation.put("AGENCY", "机构");

        // 获取被扫描者用户类型
        UserLogin scannedUser = userLoginService.getUserByUsername(scannedUsername);
        String scannedUserType = String.valueOf(scannedUser.getType());

        // 如果是唯二两种用户扫码组合: 1. 大众扫场所 2. 机构扫大众, 将被扫描用户的用户名
        // 放在request attribute内部转发到相应controller
        if (scannerUserType.equals("PEOPLE") && scannedUserType.equals("PLACE")) {
            request.setAttribute("placeUsername", scannedUsername);
            return "forward:/people/record-track";
        } else if (scannerUserType.equals("AGENCY") && scannedUserType.equals("PEOPLE")) {
            request.setAttribute("peopleUsername", scannedUsername);
            return "forward:/agency/registration-sampling";
        }

        // 如果用户扫码组合错误，带着错误信息跳转到报错页面
        String errorString = "错误的扫描：" + userTypeTranslation.get(scannerUserType) + "扫描" + userTypeTranslation.get(scannedUserType);
        redirectAttributes.addAttribute("error", errorString);
        return "redirect:/qrcode/error";
    }

    @GetMapping(value = "/get-png-qrcode", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public byte[] getQRCode(HttpSession session) {
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        HashMap<String, String> userLoginMap = (HashMap<String, String>) session.getAttribute("userLoginMap");
        byte[] pngData = null;
        String username = userLoginMap.get("username");
        String userType = userLoginMap.get("userType");
        int recordTime = 0;
        if (userType.equals("PEOPLE")) {
            People peopleUser = peopleService.getInformation(username);
            recordTime = peopleUser.getGreenCodeAfter();
        } else if (userType.equals("PLACE")) {
            Place placeUser = placeService.getInformation(username);
            recordTime = placeUser.getLowRiskAfter();
        }
        if (userType.equals("PEOPLE") || userType.equals("PLACE")) {
            int currentTime = (int) (System.currentTimeMillis() / 1000);
            if (currentTime > recordTime) {
                pngData = utilService.generateQRCode(baseUrl + "/qrcode?username=" + username, 200, 200, "#228B22");
            } else {
                pngData = utilService.generateQRCode(baseUrl + "/qrcode?username=" + username, 200, 200, "#FF0000");
            }
        }
        return pngData;
    }

    @GetMapping("/qrcode/error")
    public String qrcodeError() {
        return "forward:/qrcode/error.html";
    }
}
