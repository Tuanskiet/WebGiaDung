package com.poly.WebGiaDung.service.impl;

import com.poly.WebGiaDung.dto.CartDto;
import com.poly.WebGiaDung.entity.CartItem;
import com.poly.WebGiaDung.entity.Product;
import com.poly.WebGiaDung.entity.UserApp;
import com.poly.WebGiaDung.repo.CartItemRepo;
import com.poly.WebGiaDung.service.CartItemService;
import com.poly.WebGiaDung.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepo cartItemRepo;
    private final ProductService productService;

    @Override
    public void addToCart(CartDto cartDto, UserApp userApp) {
        Product product = productService.findById(cartDto.getProductId()).get();
        CartItem cartItem = new CartItem(cartDto.getQuantity(), product, userApp);
        // cart is exist | + 1
        CartItem oldCart = getExistCartItem(userApp, product);
        if(oldCart != null){
            Integer newQuantity = oldCart.getQuantity() + cartDto.getQuantity();
            oldCart.setQuantity(newQuantity);
            cartItemRepo.save(oldCart);
        }else{ // new cart item
            cartItemRepo.save(cartItem);
        }
    }

    @Override
    public List<CartItem> getCartsByUser(UserApp currentUser) {
        List<CartItem> cartItemList = cartItemRepo.findByUserApp(currentUser);
        return cartItemList;
    }


    @Override
    public void deleteCartItem(Integer productId, UserApp currentUser) {
        Product product = getProductById(productId);
        cartItemRepo.deleteByProductAndUserApp(product, currentUser);
    }

    @Override
    public void updateCartItem(String action, Integer productId, UserApp currentUser) {
        CartItem currentCart =  cartItemRepo.findByUserAppAndProduct(currentUser, getProductById(productId));
        if(action.equals("decrease")){
            currentCart.setQuantity( currentCart.getQuantity() - 1);
        }else{ //increase
            currentCart.setQuantity( currentCart.getQuantity() + 1);
        }
        cartItemRepo.save(currentCart);
    }

    @Override
    public int getSize(UserApp userApp) {
        List<CartItem>  cartItemList = getCartsByUser(userApp);
        return cartItemList.size();
    }

    private CartItem getExistCartItem(UserApp userApp, Product product) {
        CartItem cartItem = cartItemRepo.findByUserAppAndProduct(userApp, product);
        return cartItem;
    }

    private Product getProductById(Integer productId){
        Optional<Product> product = productService.findById(productId);
        return product.orElseThrow();
    }
}
