package com.poly.WebGiaDung.api;

import com.poly.WebGiaDung.dto.CartDto;
import com.poly.WebGiaDung.dto.OrderDto;
import com.poly.WebGiaDung.dto.OrderItemResponse;
import com.poly.WebGiaDung.entity.Product;
import com.poly.WebGiaDung.entity.UserApp;
import com.poly.WebGiaDung.security.MyUserDetails;
import com.poly.WebGiaDung.service.CartItemService;
import com.poly.WebGiaDung.service.OrderService;
import com.poly.WebGiaDung.service.ProductService;
import com.poly.WebGiaDung.service.SendEmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@Slf4j
public class OrderApi {
    private final OrderService orderService;
    private final ProductService productService;
    private final CartItemService cartItemService;
    private final SendEmailService sendEmail;

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

    @PostMapping("/order")
    @ResponseBody
    public String doOrder(@RequestBody OrderDto orderDtoList,
                          @AuthenticationPrincipal MyUserDetails myUserDetails
                          ){
        orderService.create(orderDtoList, myUserDetails.getUserApp());
        //            sendEmail.sendMailWithInline(
//                    myUserDetails.getUserApp().getEmail(),orderDtoList);
//            sendEmail.sendMailHtml(cureUserApp.getEmail(), SendEmailService.BODY_HTML);
        log.info("sent email for : {}", myUserDetails.getUserApp().getEmail());
        return "OK";
    }
}
