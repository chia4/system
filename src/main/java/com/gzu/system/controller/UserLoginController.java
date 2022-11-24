package com.gzu.system.controller;

import com.gzu.system.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class UserLoginController {
    @Autowired
    UserLoginService userLoginService;

    @GetMapping("")
    public String index(HttpSession session) {
        if (session.getAttribute("userType") == null) {
            return "redirect:/login";
        } else {
            switch ((String) session.getAttribute("userType")) {
                case "PEOPLE":
                    return "redirect:/people";
                case "PLACE":
                    return "redirect:/place";
                case "AGENCY":
                    return "redirect:/agency";
            }
        }
        throw new RuntimeException("发生错误");
    }

    @GetMapping("/login")
    public String login() {
        return "forward:/login.html";
    }

    @GetMapping("/register")
    public String register() {
        return "forward:/register.html";
    }

    @PostMapping("/post-login-data")
    public String loginData(
            @RequestParam("username") String username, @RequestParam("password") String password,
            HttpSession session, RedirectAttributes redirectAttributes
    ) {
        int loginStatus = userLoginService.login(username, password);
        switch (loginStatus) {
            case 0:
            case 1:
            case 2:
                switch (loginStatus) {
                    case 0:
                        session.setAttribute("userType", "PEOPLE");
                    case 1:
                        session.setAttribute("userType", "PLACE");
                    case 2:
                        session.setAttribute("userType", "AGENCY");
                }
                session.setAttribute("username", username);
                return "redirect:/";
            case 3:
                redirectAttributes.addAttribute("error", "密码不正确");
                return "redirect:/login";
            case 4:
                redirectAttributes.addAttribute("error", "用户不存在");
                return "redirect:/login";
            case 5:
                redirectAttributes.addAttribute("error", "发生错误");
                return "redirect:/login";
        }
        throw new RuntimeException("发生错误");
    }

    @PostMapping("/post-register-data")
    public String registerData(
            @RequestParam("type") String type, @RequestParam("username") String username, @RequestParam("password") String password,
            @RequestParam("check-password") String checkPassword, HttpSession session, RedirectAttributes redirectAttributes
    ) {
        if (!password.equals(checkPassword)) {
            redirectAttributes.addAttribute("error", "密码不一致");
            return "redirect:/register";
        }
        int registerStatus = userLoginService.register(username, password, type);
        switch (registerStatus) {
            case 0:
                session.setAttribute("username", username);
                session.setAttribute("userType", type.toUpperCase());
                return "redirect:/";
            case 1:
                redirectAttributes.addAttribute("error", "用户名已存在");
                return "redirect:/register";
            case 2:
                redirectAttributes.addAttribute("error", "发生错误");
                return "redirect:/register";
        }
        throw new RuntimeException("发生错误");
    }
}
