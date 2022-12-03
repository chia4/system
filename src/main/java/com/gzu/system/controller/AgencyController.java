package com.gzu.system.controller;

import com.gzu.system.pojo.Agency;
import com.gzu.system.pojo.CovidTestAuthorization;
import com.gzu.system.service.AgencyService;
import com.gzu.system.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;

@Controller
public class AgencyController {
    @Autowired
    AgencyService agencyService;

    @Autowired
    PeopleService peopleService;

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


    /**
     * 只用来接受来自QRCodeController的AfterScanning的内部转发，
     * 保证被扫描用户的身份是经过验证的
     */
    @GetMapping("/agency/registration-sampling")
    public String registrationSampling(HttpSession session, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String peopleUsername = (String) request.getAttribute("peopleUsername");
        if (peopleUsername == null) {
            return "redirect:/agency";
        }
        HashMap<String, String> userLoginMap = (HashMap<String, String>) session.getAttribute("userLoginMap");
        String username = userLoginMap.get("username");
        CovidTestAuthorization covidTestAuthorization = agencyService.registrationSampling(peopleUsername, username);
        if (covidTestAuthorization != null) {
            redirectAttributes.addAttribute("peopleUsername", peopleUsername);
            redirectAttributes.addAttribute("authorizationTime", covidTestAuthorization.getAuthorizationTime());
        }
        return "redirect:/agency/registration-sampling-result";
    }

    @GetMapping("/agency/registration-sampling-result")
    public String registrationSamplingResult() {
        return "forward:/agency/registration-sampling-result.html";
    }

    @ResponseBody
    @GetMapping("/agency/get-authorization")
    public ArrayList<CovidTestAuthorization> getAuthorization(HttpSession session) {
        HashMap<String, String> userLoginMap = (HashMap<String, String>) session.getAttribute("userLoginMap");
        String username = userLoginMap.get("username");
        ArrayList<CovidTestAuthorization> authorizations = agencyService.getAuthorization(username);
        if (authorizations == null) {
            authorizations = new ArrayList<>();
            authorizations.add(new CovidTestAuthorization());
        }
        return authorizations;
    }
}
