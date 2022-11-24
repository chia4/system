package com.gzu.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
public class DemoController {
    @GetMapping("/people")
    public String people(HttpSession session) {
        if (session.getAttribute("userLoginMap") == null) {
            return "redirect:/login";
        }
        if (!((HashMap<String, String>) session.getAttribute("userLoginMap")).get("userType").equals("PEOPLE")) {
            return "redirect:/";
        }
        return "forward:/user-demo.html";
    }

    @GetMapping("/place")
    public String place(HttpSession session) {
        if (session.getAttribute("userLoginMap") == null) {
            return "redirect:/login";
        }
        if (!((HashMap<String, String>) session.getAttribute("userLoginMap")).get("userType").equals("PLACE")) {
            return "redirect:/";
        }
        return "forward:/user-demo.html";
    }

    @GetMapping("/agency")
    public String agency(HttpSession session) {
        if (session.getAttribute("userLoginMap") == null) {
            return "redirect:/login";
        }
        if (!((HashMap<String, String>) session.getAttribute("userLoginMap")).get("userType").equals("AGENCY")) {
            return "redirect:/";
        }
        return "forward:/user-demo.html";
    }
}
