package com.gzu.system.controller;

import com.gzu.system.pojo.Place;
import com.gzu.system.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
public class PlaceController {
    @Autowired
    PlaceService placeService;

    @GetMapping("/place")
    public String index(HttpSession session) {
        HashMap<String, String> userLoginMap = (HashMap<String, String>) session.getAttribute("userLoginMap");
        if (placeService.getInformation(userLoginMap.get("username")) == null) {
            return "forward:/place/complete.html";
        } else {
            return "forward:/place/place.html";
        }
    }


    @ResponseBody
    @GetMapping("/place/get-people-data")
    public Place getPlaceData(HttpSession session) {
        HashMap<String, String> userLoginMap = (HashMap<String, String>) session.getAttribute("userLoginMap");
        String username = userLoginMap.get("username");
        return placeService.getInformation(username);
    }


    @PostMapping("/place/post-complete-data")
    public String placeData(@RequestParam("place-name") String placeName, @RequestParam("place-address") String placeAddress,
                            HttpSession session, RedirectAttributes redirectAttributes
    ) {
        HashMap<String, String> userLoginMap = (HashMap<String, String>) session.getAttribute("userLoginMap");
        String username = userLoginMap.get("username");
        if (placeService.getInformation(username) == null) {
            int completeStatus = placeService.completeInformation(username, placeName, placeAddress);
            switch (completeStatus) {
                case 0:
                    break;
                case 1:
                    redirectAttributes.addAttribute("error", "场所名被其他用户登记");
                    break;
                case 2:
                    redirectAttributes.addAttribute("error", "发生错误");
                    break;
            }
            return "redirect:/place";
        }
        throw new RuntimeException("发生错误");
    }
}
