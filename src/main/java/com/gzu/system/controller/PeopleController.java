package com.gzu.system.controller;

import com.gzu.system.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
public class PeopleController {

    @Autowired
    PeopleService peopleService;

    @RequestMapping("/people")
    public String index(HttpSession session) {
        HashMap<String, String> userLoginMap = (HashMap<String, String>) session.getAttribute("userLoginMap");
        if (peopleService.getInformation(userLoginMap.get("username")) == null) {
            return "forward:/people/complete.html";
        } else {
            return "forward:/people/people.html";
        }
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
}
