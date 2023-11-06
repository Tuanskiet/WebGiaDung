package com.poly.WebGiaDung.api;


import com.poly.WebGiaDung.dto.DataCompare;
import com.poly.WebGiaDung.dto.ProductRequest;
import com.poly.WebGiaDung.entity.Product;
import com.poly.WebGiaDung.entity.ProductInfo;
import com.poly.WebGiaDung.repo.ProductRepo;
import com.poly.WebGiaDung.service.ProductService;
import com.poly.WebGiaDung.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ProductApi {

    private final ProductService productService;

    @PostMapping(value = "/admin/manager-product/add", produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<?> addProduct(
            @RequestBody ProductRequest productRequest
            ){

        try{
            productService.create(productRequest);
        }catch(Exception ex){
            return ResponseEntity.status(500).body(ex.getMessage());
        }
        return ResponseEntity.ok().body("CREATED");
    }


    @GetMapping("/admin/manager-product/change-status")
    public ResponseEntity<?> changeStatusProduct(
            @RequestParam(name = "id") Integer id,
            @RequestParam(name = "statusChanged") Boolean statusChanged){
        productService.updateStatus(id, statusChanged);
        return ResponseEntity.status(200).body("UPDATED");
    }

    @DeleteMapping("/admin/manager-product/delete")
    public ResponseEntity<?> doDeleteProduct(@RequestParam(name = "id") Integer id){
        try{
            productService.deleteById(id);
        }catch(Exception e){
            return ResponseEntity.status(500).body(MessageUtils.Product.ERROR_FOREIGN_KEY);
        }
        return ResponseEntity.status(204).body("DELETED");
    }

    //user
    @GetMapping("/detail-product")
    public Product viewProductPage(@RequestParam(name = "productId") Integer productId, Model model){
        Optional<Product> product = productService.findById(productId);
        return product.orElse(null);
    }

    @PostMapping("/product/data-compare")
    public ResponseEntity<?> doCompareProduct(@RequestBody List<Integer> productIds, HttpSession session) {
        List<Product> products = new ArrayList<>();
        Map<String, List<String>> listCompare = new HashMap<>();
        List<String> listProductNames = new ArrayList<>();

        for (Integer pId : productIds) {
            Optional<Product> product = productService.findById(pId);
            if (!product.isPresent()) {
                return ResponseEntity.badRequest().body("Product id doesn't exist!");
            }

            Product foundProduct = product.get();
            products.add(foundProduct);
            listProductNames.add(foundProduct.getName());

            for (ProductInfo productInfo : foundProduct.getProductInfo()) {
                listCompare.computeIfAbsent(productInfo.getKey(), key -> new ArrayList<>())
                        .add(productInfo.getValue());
            }
        }

        DataCompare dataCompare = new DataCompare();
        dataCompare.setListProductName(listProductNames);
        dataCompare.setListCompare(listCompare);

        return ResponseEntity.ok(dataCompare);
    }

}
