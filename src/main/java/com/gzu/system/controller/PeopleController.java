package com.gzu.system.controller;

import com.gzu.system.pojo.People;
import com.gzu.system.pojo.Place;
import com.gzu.system.pojo.SimpleAuthorization;
import com.gzu.system.pojo.SimpleResult;
import com.gzu.system.service.PeopleService;
import com.gzu.system.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;

@Controller
public class PeopleController {

    @Autowired
    PeopleService peopleService;

    @Autowired
    PlaceService placeService;

    @GetMapping("/people")
    public String index(HttpSession session) {
        HashMap<String, String> userLoginMap = (HashMap<String, String>) session.getAttribute("userLoginMap");
        if (peopleService.getInformation(userLoginMap.get("username")) == null) {
            return "forward:/people/complete.html";
        } else {
            return "forward:/people/people.html";
        }
    }

    @ResponseBody
    @GetMapping("/people/get-people-data")
    public People getPeopleData(HttpSession session) {
        HashMap<String, String> userLoginMap = (HashMap<String, String>) session.getAttribute("userLoginMap");
        String username = userLoginMap.get("username");
        return peopleService.getInformation(username);
    }


    @PostMapping("/people/post-complete-data")
    public String peopleData(@RequestParam("full-name") String fullName, @RequestParam("phone-number") String phoneNumber,
                             @RequestParam("id-card-number") String idCardNumber, @RequestParam("user-gender") String userGender,
                             HttpSession session, RedirectAttributes redirectAttributes
    ) {
        HashMap<String, String> userLoginMap = (HashMap<String, String>) session.getAttribute("userLoginMap");
        String username = userLoginMap.get("username");
        if (peopleService.getInformation(username) == null) {
            int completeStatus = peopleService.completeInformation(username, fullName, phoneNumber, idCardNumber, userGender);
            switch (completeStatus) {
                case 0:
                    break;
                case 1:
                    redirectAttributes.addAttribute("error", "身份证被其他账号登记");
                    break;
                case 2:
                    redirectAttributes.addAttribute("error", "发生错误");
                    break;
            }
            return "redirect:/people";
        }
        throw new RuntimeException("发生错误");
    }

    /**
     * 只用来接受来自QRCodeController的AfterScanning的内部转发，
     * 保证被扫描用户的身份是经过验证的
     */
    @GetMapping("/people/record-track")
    public String recordTrack(HttpSession session, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String placeUsername = (String) request.getAttribute("placeUsername");
        if (placeUsername == null) {
            return "redirect:/people";
        }
        HashMap<String, String> userLoginMap = (HashMap<String, String>) session.getAttribute("userLoginMap");
        String username = userLoginMap.get("username");
        Place place = placeService.getInformation(placeUsername);
        int status = placeService.recordTrack(username, placeUsername);
        if (status == 0) {
            redirectAttributes.addAttribute("placeName", place.getPlaceName());
        }
        return "redirect:/people/record-track-result";
    }

    @GetMapping("/people/record-track-result")
    public String recordTrackResult() {
        return "forward:/people/record-track-result.html";
    }

    @GetMapping("/people/get-result")
    @ResponseBody
    public ArrayList<SimpleResult> getResult(HttpSession session) {
        HashMap<String, String> userLoginMap = (HashMap<String, String>) session.getAttribute("userLoginMap");
        String username = userLoginMap.get("username");
        ArrayList<SimpleResult> simpleResults = peopleService.getSimpleResult(username);
        // 只返回最近十次结果(数据库已经排降序返回)
        if (simpleResults.size() > 10) {
            return (ArrayList<SimpleResult>) simpleResults.subList(0, 10);
        } else {
            return simpleResults;
        }
    }

    @GetMapping("/people/get-authorization")
    @ResponseBody
    public ArrayList<SimpleAuthorization> getAuthorization(HttpSession session) {
        HashMap<String, String> userLoginMap = (HashMap<String, String>) session.getAttribute("userLoginMap");
        String username = userLoginMap.get("username");
        ArrayList<SimpleAuthorization> simpleAuthorizations = peopleService.getSimpleAuthorization(username);
        // 只返回最近十次授权(数据库已经排降序返回)
        if (simpleAuthorizations.size() > 10) {
            return (ArrayList<SimpleAuthorization>) simpleAuthorizations.subList(0, 10);
        } else {
            return simpleAuthorizations;
        }
    }
}
