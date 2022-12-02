package com.gzu.system.controller;

import com.gzu.system.pojo.People;
import com.gzu.system.pojo.Place;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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

    @GetMapping("/qrcode")
    @ResponseBody
    public String AfterScanning(@RequestParam("username") String username, HttpSession session) {
        HashMap<String, String> userLoginMap = (HashMap<String, String>) session.getAttribute("userLoginMap");
        String scannerUsername = userLoginMap.get("username");
        return "You are " + scannerUsername + ", and you are scanning " + username;
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
}
