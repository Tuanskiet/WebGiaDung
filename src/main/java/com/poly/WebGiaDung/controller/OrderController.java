package com.poly.WebGiaDung.controller;


import com.poly.WebGiaDung.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class OrderController {
    @GetMapping("/order")
    public String viewOrderPage(){
        return "user/pay";
    }
}
