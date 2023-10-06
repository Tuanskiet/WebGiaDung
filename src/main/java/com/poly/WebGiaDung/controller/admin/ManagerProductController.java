package com.poly.WebGiaDung.controller.admin;


import com.poly.WebGiaDung.dto.ProductRequest;
import com.poly.WebGiaDung.entity.Product;
import com.poly.WebGiaDung.service.BrandService;
import com.poly.WebGiaDung.service.MyCategoryService;
import com.poly.WebGiaDung.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ManagerProductController {
    private static final int PRODUCT_PER_PAGE = 5;

    private final ProductService productService;
    private final MyCategoryService categoryService;
    private final BrandService brandService;

    @GetMapping("/admin/manager-product")
    public String viewListProductPage(
            @RequestParam(name="page", defaultValue = "1", required = false)  int page,
            @RequestParam(name="sortBy",defaultValue = "id", required = false) String sortBy,
            @RequestParam(name="orderBy", defaultValue = "desc",  required = false) String orderBy,
            @RequestParam(name="keyword",  required = false) String keyword,
            Model model){
        Pageable pageable = PageRequest.of(page - 1, PRODUCT_PER_PAGE)
                .withSort(Sort.by(Sort.Direction.fromString(orderBy), sortBy));
        Page<Product> listProduct = null;
        if(keyword != null){
            listProduct = productService.findByKeyword(keyword, pageable);
            model.addAttribute("keyword", keyword);
        }else{
            listProduct = productService.getProductsWithSortAndPagination(pageable);
        }
        model.addAttribute("listProducts", listProduct.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", listProduct.getTotalPages());
        model.addAttribute("totalElements", listProduct.getTotalElements());
        return "admin/manager_product";
    }

    @GetMapping("/admin/manager-product/add")
    public String  viewAddProductPage(Model model){
        model.addAttribute("listCategories", categoryService.getAllCategory());
        model.addAttribute("listBrands", brandService.getAll());
        return "admin/add_product";
    }

    @GetMapping("/admin/manager-product/edit")
    public String viewEditProductPage(@RequestParam(name = "id") Integer id, Model model){
        Optional<Product> product = productService.findById(id);
        if(product.isEmpty()){
            return "redirect:/admin/manager-product";
        }
        model.addAttribute("listCategories", categoryService.getAllCategory());
        model.addAttribute("listBrands", brandService.getAll());
        model.addAttribute("productEdit", product.get());
        return "admin/edit_product";
    }

    @PostMapping("/admin/manager-product/update")
    public String updateProduct(
            @ModelAttribute(name = "productEdit") Product product){


        productService.update(product);
        return "redirect:/admin/manager-product";
    }

}
