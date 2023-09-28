package com.poly.WebGiaDung.controller;

import com.poly.WebGiaDung.dto.CartDto;
import com.poly.WebGiaDung.entity.MyCategory;
import com.poly.WebGiaDung.security.MyUserDetails;
import com.poly.WebGiaDung.service.BrandService;
import com.poly.WebGiaDung.service.CartItemService;
import com.poly.WebGiaDung.service.MyCategoryService;
import com.poly.WebGiaDung.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final MyCategoryService myCategoryService;
    private final BrandService brandService;
    private final ProductService productService;
    private final CartItemService cartItemService;

    @GetMapping("/")
    public String mainPage(Model model, HttpSession httpSession,
                            @AuthenticationPrincipal MyUserDetails myUserDetails){
        List<MyCategory> myCategoryList = myCategoryService.getAllCategoryActive();
        httpSession.setAttribute("dataCategory", myCategoryList);
        model.addAttribute("dataBrands", brandService.getAll());
        model.addAttribute("listTopDiscount", productService.getTopDiscount());
        int sizeCart = 0;
        if(myUserDetails != null) sizeCart = cartItemService.getSize(myUserDetails.getUserApp());
        httpSession.setAttribute("sizeCart", sizeCart);
        return "user/index";
    }



}
