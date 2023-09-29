package com.poly.WebGiaDung.api;


import com.poly.WebGiaDung.dto.CartDto;
import com.poly.WebGiaDung.entity.CartItem;
import com.poly.WebGiaDung.entity.UserApp;
import com.poly.WebGiaDung.security.MyUserDetails;
import com.poly.WebGiaDung.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CartApi {

    private final CartItemService cartItemService;

    @PostMapping("/cart/add")
    public ResponseEntity<?> addToCart(
            @RequestParam(name = "productId") Integer productId,
            @RequestParam(name = "quantity") Integer quantity,
            @AuthenticationPrincipal MyUserDetails myUserDetails){
        if(myUserDetails != null){
            cartItemService.addToCart(new CartDto(quantity, productId), myUserDetails.getUserApp());
            return ResponseEntity.status(HttpStatus.OK).body(getSizeCart(myUserDetails.getUserApp()));
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
        }
    }

    @PostMapping("/cart/update")
    public ResponseEntity<?> updateCart(
            @RequestParam(name = "productId") Integer productId,
            @RequestParam(name = "action") String action,
            @AuthenticationPrincipal MyUserDetails myUserDetails){
        cartItemService.updateCartItem(action, productId, myUserDetails.getUserApp());
        return ResponseEntity.status(HttpStatus.OK).body(getSizeCart(myUserDetails.getUserApp()));
    }

    @DeleteMapping("/cart/delete")
    public ResponseEntity<?> deleteCart(
            @RequestParam(name = "productId") Integer productId,
            @AuthenticationPrincipal MyUserDetails myUserDetails){
        cartItemService.deleteCartItem( productId, myUserDetails.getUserApp());
        return ResponseEntity.status(HttpStatus.OK).body(getSizeCart(myUserDetails.getUserApp()));
    }

    public int getSizeCart(UserApp user){
        List<CartItem>  cartItemList = cartItemService.getCartsByUser(user);
        return cartItemList.size();
    }
}
