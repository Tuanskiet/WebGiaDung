package com.poly.WebGiaDung.service;

import com.poly.WebGiaDung.dto.CartDto;
import com.poly.WebGiaDung.entity.CartItem;
import com.poly.WebGiaDung.entity.UserApp;

import java.util.List;

public interface CartItemService {
    void addToCart(CartDto cartDto, UserApp userApp);

    List<CartItem> getCartsByUser(UserApp currentUser);

    void deleteCartItem(Integer productId, UserApp currentUser);

    void updateCartItem(String action, Integer productId, UserApp currentUser);
}
