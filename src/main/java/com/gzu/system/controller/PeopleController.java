package com.gzu.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller()
public class PeopleController {
    @GetMapping("/people")
    public String index() {
        return "redirect:/people/complete";
    }

    @GetMapping("/people/complete")
    public String complete() {
        return "forward:/people/complete.html";
    }
}
