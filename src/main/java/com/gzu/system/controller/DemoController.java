package com.gzu.system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    @GetMapping("/people")
    public String people() {
        return "区域疫情管控系统 | 用户 | 待开发";
    }

    @GetMapping("/place")
    public String place() {
        return "区域疫情管控系统 | 场所 | 待开发";
    }

    @GetMapping("/agency")
    public String agency() {
        return "区域疫情管控系统 | 机构 | 待开发";
    }
}
