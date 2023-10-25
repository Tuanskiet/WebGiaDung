package com.poly.WebGiaDung.controller.admin;

import com.poly.WebGiaDung.dto.MyCategoryDto;
import com.poly.WebGiaDung.entity.MyCategory;
import com.poly.WebGiaDung.service.MyCategoryService;
import com.poly.WebGiaDung.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ManagerCategoriesController {
    private final MyCategoryService categoryService;
    private static final int ITEM_PER_PAGE = 5;
    @GetMapping("/admin/manager-category")
    public String  viewManagerCategoriesPage(
                @RequestParam(name="page", defaultValue = "1", required = false)  int page,
                @RequestParam(name="sortBy",defaultValue = "id", required = false) String sortBy,
                @RequestParam(name="orderBy", defaultValue = "asc",  required = false) String orderBy,
                @RequestParam(name="keyword",  required = false) String keyword,
                Model model){
        Pageable pageable = PageRequest.of(page -1, ITEM_PER_PAGE)
                        .withSort(Sort.by(Sort.Direction.fromString(orderBy), sortBy));
        Page<MyCategory> listCategories = null;
        if(keyword != null){
            listCategories = categoryService.findByKeyword(keyword, pageable);
            model.addAttribute("keyword", keyword);
        }else{
            listCategories = categoryService.getWithSortAndPagination(pageable);
        }
        model.addAttribute("listCategories", listCategories.getContent());
        model.addAttribute("totalPages", listCategories.getTotalPages());
        model.addAttribute("totalElements", listCategories.getTotalElements());
        model.addAttribute("currentPage", page);
        model.addAttribute("newCategory", new MyCategory());
        return "admin/manager_category";
    }
    @PostMapping("/admin/manager-category/add")
    public String doCreateNewCategories(@ModelAttribute(name = "newCategory") MyCategory myCategory,
                                  Model model){
        try{
            categoryService.create(myCategory);
            model.addAttribute("success", MessageUtils.Product.ADD_SUCCESS.getVal());
        }catch(Exception ex){
            model.addAttribute("error", MessageUtils.Product.ADD_FAILED.getVal());
        }
        return "redirect:/admin/manager-category";
    }




}
