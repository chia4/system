package com.gzu.system.controller;

import com.gzu.system.pojo.Agency;
import com.gzu.system.service.AgencyService;
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
public class AgencyController {
    @Autowired
    AgencyService agencyService;

    @GetMapping("/agency")
    public String index(HttpSession session) {
        HashMap<String, String> userLoginMap = (HashMap<String, String>) session.getAttribute("userLoginMap");
        if (agencyService.getInformation(userLoginMap.get("username")) == null) {
            return "forward:/agency/complete.html";
        } else {
            return "forward:/agency/agency.html";
        }
    }


    @ResponseBody
    @GetMapping("/agency/get-agency-data")
    public Agency getPlaceData(HttpSession session) {
        HashMap<String, String> userLoginMap = (HashMap<String, String>) session.getAttribute("userLoginMap");
        String username = userLoginMap.get("username");
        return agencyService.getInformation(username);
    }


    @PostMapping("/agency/post-complete-data")
    public String placeData(@RequestParam("agency-name") String agencyName, @RequestParam("agency-address") String agencyAddress,
                            HttpSession session, RedirectAttributes redirectAttributes
    ) {
        HashMap<String, String> userLoginMap = (HashMap<String, String>) session.getAttribute("userLoginMap");
        String username = userLoginMap.get("username");
        if (agencyService.getInformation(username) == null) {
            int completeStatus = agencyService.completeInformation(username, agencyName, agencyAddress);
            switch (completeStatus) {
                case 0:
                    break;
                case 1:
                    redirectAttributes.addAttribute("error", "机构名被其他用户登记");
                    break;
                case 2:
                    redirectAttributes.addAttribute("error", "发生错误");
                    break;
            }
            return "redirect:/agency";
        }
        throw new RuntimeException("发生错误");
    }
}
