package com.poly.WebGiaDung.controller;

import com.poly.WebGiaDung.entity.Product;
import com.poly.WebGiaDung.service.ProductService;
import com.poly.WebGiaDung.utils.SlugGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private static final int PRODUCT_PER_PAGE = 12;
    private final ProductService productService;
    @GetMapping("/product")
    public String viewProductDetailPage(@RequestParam(name = "id") Integer id, Model model){
        Optional<Product> product = productService.findById(id);
        model.addAttribute("productDetail", product.get());
        return "user/product_detail";
    }

    @GetMapping("/list-product")
    public String viewListProductPage(
            @RequestParam(name="page", defaultValue = "1", required = false)  int page,
            @RequestParam(name="sortBy",defaultValue = "id", required = false) String sortBy,
            @RequestParam(name="orderBy", defaultValue = "asc",  required = false) String orderBy,
            @RequestParam(name="category", defaultValue = "",  required = false) String categoryShow,
            @RequestParam(name="keyword", defaultValue = "",  required = false) String keyword,
            Model model, HttpServletRequest request){
        Pageable pageable = PageRequest.of(page - 1, PRODUCT_PER_PAGE)
                .withSort(Sort.by(Sort.Direction.fromString(orderBy), sortBy));

        Page<Product> listProduct ;

        if(!categoryShow.equals("")){
            String slug = SlugGenerator.generateSlug(categoryShow);
            listProduct = productService.getListProductsByCategory(slug, pageable);
        }else if(!keyword.equals("")){
            listProduct = productService.findByKeywordAndActive(keyword, pageable);
        }else{
            listProduct = productService.getAllAndActiveTrue(pageable);
        }
        model.addAttribute("listProducts", listProduct.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", listProduct.getTotalPages());
        model.addAttribute("keyword", keyword);
        model.addAttribute("dataSort", sortBy + "-" + orderBy);
        return "user/list_product";
    }

    @GetMapping("/list-product/brand")
    public String getProductByBrand(
            @RequestParam(name="page", defaultValue = "1", required = false)  int page,
            @RequestParam(name="sortBy",defaultValue = "id", required = false) String sortBy,
            @RequestParam(name="orderBy", defaultValue = "asc",  required = false) String orderBy,
            @RequestParam(name="id", defaultValue = "",  required = false) Integer brandId,
            Model model, HttpServletRequest request){
        Pageable pageable = PageRequest.of(page - 1, PRODUCT_PER_PAGE)
                .withSort(Sort.by(Sort.Direction.fromString(orderBy), sortBy));

        Page<Product> listProduct = productService.getByBrandIdAndActive(brandId, pageable);
        model.addAttribute("listProducts", listProduct.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", listProduct.getTotalPages());
        return "/user/list_product.html";
    }
}