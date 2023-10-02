package com.poly.WebGiaDung.controller;


import com.poly.WebGiaDung.entity.Order;
import com.poly.WebGiaDung.entity.Product;
import com.poly.WebGiaDung.security.MyUserDetails;
import com.poly.WebGiaDung.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @GetMapping("/order")
    public String viewOrderPage(){
        return "user/pay";
    }

    @GetMapping("/order/history")
    public String viewOrderHistoryPage(
            @RequestParam(name = "status", required = false, defaultValue = "")String status,
            @AuthenticationPrincipal MyUserDetails myUserDetails,
            Model model){
        if(myUserDetails != null){
            List<Order> listOrder ;
            if(!status.equals("")){
                listOrder = orderService.findByUserAndStatus(myUserDetails.getUserApp(), status);
            }else{
                listOrder = orderService.findByUser(myUserDetails.getUserApp());
            }
            model.addAttribute("listOrder", listOrder);
            model.addAttribute("status", status);

        }
        return "user/order_history";
    }
}
