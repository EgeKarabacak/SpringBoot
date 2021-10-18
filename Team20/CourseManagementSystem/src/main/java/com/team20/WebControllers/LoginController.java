package com.team20.WebControllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginController {
    @Value("${spring.application.name}")
    String appName;

    @GetMapping("/login")
    public String homePage() {
        return "login";
    }
}