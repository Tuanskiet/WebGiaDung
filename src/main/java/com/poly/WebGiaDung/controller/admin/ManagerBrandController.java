package com.poly.WebGiaDung.controller.admin;

import com.poly.WebGiaDung.dto.MyCategoryDto;
import com.poly.WebGiaDung.entity.BrandApp;
import com.poly.WebGiaDung.entity.MyCategory;
import com.poly.WebGiaDung.service.BrandService;
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
public class ManagerBrandController {
    private final BrandService brandService;
    private static final int ITEM_PER_PAGE = 5;
    @GetMapping("/admin/manager-brand")
    public String  viewManagerBrandPage(
                @RequestParam(name="page", defaultValue = "1", required = false)  int page,
                @RequestParam(name="sortBy",defaultValue = "id", required = false) String sortBy,
                @RequestParam(name="orderBy", defaultValue = "asc",  required = false) String orderBy,
                @RequestParam(name="keyword",  required = false) String keyword,
                Model model){
        Pageable pageable = PageRequest.of(page -1, ITEM_PER_PAGE)
                        .withSort(Sort.by(Sort.Direction.fromString(orderBy), sortBy));
        Page<BrandApp> listBrandApps = null;
        if(keyword != null){
            listBrandApps = brandService.findByKeyword(keyword, pageable);
            model.addAttribute("keyword", keyword);
        }else{
            listBrandApps = brandService.getWithSortAndPagination(pageable);
        }
        model.addAttribute("listBrands", listBrandApps.getContent());
        model.addAttribute("totalPages", listBrandApps.getTotalPages());
        model.addAttribute("totalElements", listBrandApps.getTotalElements());
        model.addAttribute("currentPage", page);
        model.addAttribute("newBrand", new BrandApp());
        return "admin/manager_brand";
    }
    @PostMapping("/admin/manager-brand/add")
    public String doCreateNewBrand(@ModelAttribute(name = "newBrand") BrandApp brandApp,
                                  Model model){
        try{
            brandService.create(brandApp);
            model.addAttribute("success", MessageUtils.Product.ADD_SUCCESS.getVal());
        }catch(Exception ex){
            model.addAttribute("error", MessageUtils.Product.ADD_FAILED.getVal());
        }
        return "redirect:/admin/manager-brand";
    }

    @DeleteMapping("/admin/manager-brand/delete")
    @ResponseBody
    public ResponseEntity<?> doDeleteBrand(@RequestParam(name = "id") Integer id){
        try{
            brandService.deleteById(id);
        }catch(Exception e){
            return ResponseEntity.status(500).body(MessageUtils.Product.ERROR_FOREIGN_KEY.getVal());
        }
        return ResponseEntity.status(204).body("DELETED");
    }

    @GetMapping("/admin/manager-brand/edit")
    @ResponseBody
    public BrandApp editBrand(@RequestParam(name = "id") Integer id,
                                        Model model){
        model.addAttribute("mode", "edit");
        BrandApp brandApp = brandService.findById(id).get();
        return brandApp;
    }


}
