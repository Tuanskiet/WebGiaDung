package com.poly.WebGiaDung.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountController {
    @GetMapping("/login")
    String viewLoginForm() {
        return "login";
    }
}
