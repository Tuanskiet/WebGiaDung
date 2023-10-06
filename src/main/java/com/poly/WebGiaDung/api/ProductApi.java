package com.poly.WebGiaDung.api;


import com.poly.WebGiaDung.dto.ProductRequest;
import com.poly.WebGiaDung.repo.ProductRepo;
import com.poly.WebGiaDung.service.ProductService;
import com.poly.WebGiaDung.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping("/admin/manager-product/change-status")
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

}
