package com.poly.WebGiaDung.controller;

import com.poly.WebGiaDung.entity.Product;
import com.poly.WebGiaDung.security.MyUserDetails;
import com.poly.WebGiaDung.service.*;
import com.poly.WebGiaDung.utils.SlugGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private static final int PRODUCT_PER_PAGE = 6;
    private final ProductService productService;
    private final EvaluateService evaluateService;
    private final MyCategoryService categoryService;
    private final CartItemService cartItemService;

    @GetMapping("/product")
    public String viewProductDetailPage(@RequestParam(name = "id") Integer id, Model model){
        Optional<Product> product = productService.findById(id);
        model.addAttribute("productDetail", product.get());
        model.addAttribute("evaluateList", evaluateService.findByProduct(product.get()));
        return "user/product_detail";
    }

    @GetMapping("/list-product")
    public String viewListProductPage(
            @RequestParam(name="page", defaultValue = "1", required = false)  int page,
            @RequestParam(name="sortBy",defaultValue = "id", required = false) String sortBy,
            @RequestParam(name="orderBy", defaultValue = "asc",  required = false) String orderBy,
            @RequestParam(name="category", defaultValue = "",  required = false) String categoryShow,
            @RequestParam(name="keyword", defaultValue = "",  required = false) String keyword,
            Model model, HttpSession session,
            @AuthenticationPrincipal MyUserDetails myUserDetails){
        Pageable pageable = PageRequest.of(page - 1, PRODUCT_PER_PAGE)
                .withSort(Sort.by(Sort.Direction.fromString(orderBy), sortBy));

        Page<Product> listProduct ;
        String breadcrumb = "";
        String slugCategory = SlugGenerator.generateSlug(categoryShow);
        if(!categoryShow.equals("")){
           ;
            listProduct = productService.getListProductsByCategory(slugCategory, pageable);

            String categoryName = categoryService.findBySlug(slugCategory).get().getName();
            breadcrumb = categoryName;
        }else if(!keyword.equals("")){
            listProduct = productService.findByKeywordAndActive(keyword, pageable);
            breadcrumb = keyword;
        }else{
            listProduct = productService.getAllAndActiveTrue(pageable);
        }
        model.addAttribute("listProducts", listProduct.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", listProduct.getTotalPages());
        model.addAttribute("keyword", keyword);
        model.addAttribute("slugCategory", slugCategory);
        model.addAttribute("dataSort", sortBy + "-" + orderBy);
        model.addAttribute("breadcrumb", breadcrumb);

        int sizeCart = 0;
        if(myUserDetails != null) sizeCart = cartItemService.getSize(myUserDetails.getUserApp());
        session.setAttribute("sizeCart", sizeCart);
        return "user/list_product";
    }

//    @GetMapping("/list-product/brand")
//    public String getProductByBrand(
//            @RequestParam(name="page", defaultValue = "1", required = false)  int page,
//            @RequestParam(name="sortBy",defaultValue = "id", required = false) String sortBy,
//            @RequestParam(name="orderBy", defaultValue = "asc",  required = false) String orderBy,
//            @RequestParam(name="id", defaultValue = "",  required = false) Integer brandId,
//            Model model, HttpServletRequest request){
//        Pageable pageable = PageRequest.of(page - 1, PRODUCT_PER_PAGE)
//                .withSort(Sort.by(Sort.Direction.fromString(orderBy), sortBy));
//
//        Page<Product> listProduct = productService.getByBrandIdAndActive(brandId, pageable);
//        model.addAttribute("listProducts", listProduct.getContent());
//        model.addAttribute("currentPage", page);
//        model.addAttribute("brandId", brandId);
//        model.addAttribute("totalPages", listProduct.getTotalPages());
//        model.addAttribute("dataSort", sortBy + "-" + orderBy);
//        return "user/list_product.html";
//    }
}
