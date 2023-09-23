package com.poly.WebGiaDung.controller;

import com.poly.WebGiaDung.entity.MyCategory;
import com.poly.WebGiaDung.service.BrandService;
import com.poly.WebGiaDung.service.MyCategoryService;
import com.poly.WebGiaDung.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final MyCategoryService myCategoryService;
    private final BrandService brandService;
    private final ProductService productService;
    @GetMapping("/")
    public String mainPage(Model model, HttpSession httpSession){
        List<MyCategory> myCategoryList = myCategoryService.getAllCategoryActive();
        httpSession.setAttribute("dataCategory", myCategoryList);
        model.addAttribute("dataBrands", brandService.getAll());
        model.addAttribute("listTopDiscount", productService.getTopDiscount());
        return "user/index";
    }

}
