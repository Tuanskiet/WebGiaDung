package com.poly.WebGiaDung.api;


import com.poly.WebGiaDung.dto.ReponseObject;
import com.poly.WebGiaDung.entity.Product;
import com.poly.WebGiaDung.service.MyCategoryService;
import com.poly.WebGiaDung.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryApi {
    private final ProductService productService;
    @GetMapping("/category")
    public ResponseEntity<?> getListProductByCategoryId(@RequestParam(name = "id") Integer id){
        List<Product> listProduct = productService.findByCategoryId(id);
        return ResponseEntity.status(200).body(listProduct);
    }
}
