package com.poly.WebGiaDung.api;


import com.poly.WebGiaDung.dto.MyCategoryDto;
import com.poly.WebGiaDung.dto.ReponseObject;
import com.poly.WebGiaDung.entity.MyCategory;
import com.poly.WebGiaDung.entity.Product;
import com.poly.WebGiaDung.service.MyCategoryService;
import com.poly.WebGiaDung.service.ProductService;
import com.poly.WebGiaDung.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryApi {
    private final ProductService productService;
    private final MyCategoryService categoryService;

    @GetMapping("/category")
    public ResponseEntity<?> getListProductByCategoryId(@RequestParam(name = "id") Integer id){
        List<Product> listProduct = productService.findByCategoryId(id);
        return ResponseEntity.status(200).body(listProduct);
    }

    @DeleteMapping("/admin/manager-category/delete")
    public ResponseEntity<?> doDeleteCategories(@RequestParam(name = "id") Integer id){
        try{
            categoryService.deleteById(id);
        }catch(Exception e){
            return ResponseEntity.status(500).body(MessageUtils.Product.ERROR_FOREIGN_KEY.getVal());
        }
        return ResponseEntity.status(204).body("DELETED");
    }

    @GetMapping("/admin/manager-category/edit")
    public MyCategoryDto editCategories(@RequestParam(name = "id") Integer id,
                                        Model model){
        model.addAttribute("mode", "edit");
        MyCategoryDto categoryDto = new MyCategoryDto();
        MyCategory myCategory = categoryService.findById(id).get();
        BeanUtils.copyProperties(myCategory, categoryDto);
        return categoryDto;
    }

    @GetMapping("/admin/manager-category/change-status")
    public ResponseEntity<?> changeStatusCategories(
            @RequestParam(name = "id") Integer id,
            @RequestParam(name = "statusChanged") Boolean statusChanged){
        categoryService.updateStatus(id, statusChanged);
        return ResponseEntity.status(200).body("UPDATED");
    }
}
