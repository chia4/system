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
            return "forward:/login.html";
        } else {
            switch ((int) session.getAttribute("userType")) {
                case 0:
                    return "redirect:/people";
                case 1:
                    return "redirect:/place";
                case 2:
                    return "redirect:/agency";
            }
        }
        throw new RuntimeException("发生错误");
    }

    @PostMapping("/post-login-data")
    public String loginData(
            @RequestParam("user-name") String userName, @RequestParam("password") String password,
            HttpSession session, RedirectAttributes redirectAttributes
    ) {
        int loginStatus = userLoginService.login(userName, password);
        switch (loginStatus) {
            case 0:
            case 1:
            case 2:
                session.setAttribute("username", userName);
                session.setAttribute("userType", loginStatus);
                return "forward:/";
            case 3:
                redirectAttributes.addAttribute("error", "密码不正确");
                return "redirect:/";
            case 4:
                redirectAttributes.addAttribute("error", "用户不存在");
                return "redirect:/";
            case 5:
                redirectAttributes.addAttribute("error", "发生错误");
                return "redirect:/";
        }
        throw new RuntimeException("发生错误");
    }

}
