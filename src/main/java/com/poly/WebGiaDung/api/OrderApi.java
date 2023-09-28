package com.poly.WebGiaDung.api;

import com.poly.WebGiaDung.dto.CartDto;
import com.poly.WebGiaDung.dto.OrderItemResponse;
import com.poly.WebGiaDung.entity.Product;
import com.poly.WebGiaDung.security.MyUserDetails;
import com.poly.WebGiaDung.service.CartItemService;
import com.poly.WebGiaDung.service.OrderService;
import com.poly.WebGiaDung.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
public class OrderApi {
    private  final OrderService orderService;
    private  final ProductService productService;
    private  final CartItemService cartItemService;
    @PostMapping("/get-data-order")
    public List<OrderItemResponse> getDataOrder(
            @RequestBody List<CartDto> cartDtoList
    ){
        List<OrderItemResponse> listCartItemOrder = cartDtoList
                .stream().map(cartDto -> {
                    Product product = productService.findById(cartDto.getProductId()).get();
                    OrderItemResponse orderItemResponse = new OrderItemResponse();
                    orderItemResponse.setProductId(cartDto.getProductId());
                    orderItemResponse.setQuantity(cartDto.getQuantity());
                    orderItemResponse.setName(product.getName());
                    orderItemResponse.setPrice(product.getPrice());
                    return orderItemResponse;
                }).collect(Collectors.toList());
        return listCartItemOrder;
    }
}
