package com.gzu.system.controller;

import com.gzu.system.service.UserLoginService;
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
public class UserLoginController {
    @Autowired
    UserLoginService userLoginService;

    /**
     * 根目录，将不同用户路由到各个页面
     */
    @GetMapping("")
    public String index(HttpSession session) {
        if (session.getAttribute("userLoginMap") == null) {
            return "redirect:/login";
        } else {
            switch (((HashMap<String, String>) session.getAttribute("userLoginMap")).get("userType")) {
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
    public String login(HttpSession session) {
        if (session.getAttribute("userLoginMap") != null) {
            return "redirect:/";
        }
        return "forward:/login.html";
    }

    @GetMapping("/register")
    public String register(HttpSession session) {
        if (session.getAttribute("userLoginMap") != null) {
            return "redirect:/";
        }
        return "forward:/register.html";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.setAttribute("userLoginMap", null);
        return "redirect:/login";
    }

    /**
     * 返回储存在当前会话session中的用户名和用户类型
     */
    @ResponseBody
    @GetMapping("/user-login-data")
    public HashMap<String, String> userLoginData(HttpSession session) {
        return (HashMap<String, String>) session.getAttribute("userLoginMap");
    }

    /**
     * "/login"页面提交的登录信息表单
     */
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
                HashMap<String, String> userLoginMap = new HashMap<>();
                switch (loginStatus) {
                    case 0:
                        userLoginMap.put("userType", "PEOPLE");
                        break;
                    case 1:
                        userLoginMap.put("userType", "PLACE");
                        break;
                    case 2:
                        userLoginMap.put("userType", "AGENCY");
                        break;
                }
                userLoginMap.put("username", username);
                session.setAttribute("userLoginMap", userLoginMap);
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

    /**
     * "/register"页面提交的注册信息表单
     */
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
                HashMap<String, String> userLoginMap = new HashMap<>();
                userLoginMap.put("username", username);
                userLoginMap.put("userType", type.toUpperCase());
                session.setAttribute("userLoginMap", userLoginMap);
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
