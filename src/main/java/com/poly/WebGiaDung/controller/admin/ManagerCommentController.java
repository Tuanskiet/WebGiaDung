package com.poly.WebGiaDung.controller.admin;


import com.poly.WebGiaDung.entity.Evaluate;
import com.poly.WebGiaDung.entity.Product;
import com.poly.WebGiaDung.security.MyUserDetails;
import com.poly.WebGiaDung.service.EvaluateService;
import com.poly.WebGiaDung.service.ProductService;
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

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ManagerCommentController {
    private final int PRODUCT_PER_PAGE = 5;

    private final ProductService productService;
    private final EvaluateService evaluateService;

    @GetMapping("/admin/manager-comment/{productId}")
    public String viewListCommentPage(
            @PathVariable(name = "productId") Integer productId,
            @RequestParam(name="page", defaultValue = "1", required = false)  int page,
            @RequestParam(name="keyword",  required = false) String keyword,
                                      Model model ){
        Page<Evaluate> evaluateList = null;
                Pageable pageable = PageRequest.of(page - 1, PRODUCT_PER_PAGE);
        if(keyword != null){
            evaluateList = evaluateService.findByKeyword(keyword, productId, pageable);
            model.addAttribute("keyword", keyword);
        }else{
            evaluateList = evaluateService.getCommentsWithPagination(productId, pageable);
        }
        model.addAttribute("productId", productId);
        if(evaluateList != null){
            model.addAttribute("listComments", evaluateList.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", evaluateList.getTotalPages());
            model.addAttribute("totalElements", evaluateList.getTotalElements());
            return "admin/manager_comment";
        }else{
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", 0);
            model.addAttribute("totalElements", 0);
            return "admin/manager_comment";
        }
    }
}
