package com.poly.WebGiaDung.controller;

import com.poly.WebGiaDung.entity.CartItem;
import com.poly.WebGiaDung.security.MyUserDetails;
import com.poly.WebGiaDung.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {
    private final CartItemService cartItemService;

    @GetMapping("/cart")
    public String mainPage(Model model, @AuthenticationPrincipal MyUserDetails userDetails, HttpSession session) {
        if(userDetails != null){
            List<CartItem> cartItemList = cartItemService.getCartsByUser(userDetails.getUserApp());
            model.addAttribute("listCart", cartItemList);
            session.setAttribute("sizeCart", cartItemService.getSize(userDetails.getUserApp()));
            return "user/cart";
        }
        return "login";
    }

}
