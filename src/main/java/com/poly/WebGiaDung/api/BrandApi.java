package com.poly.WebGiaDung.api;


import com.poly.WebGiaDung.entity.BrandApp;
import com.poly.WebGiaDung.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BrandApi {
    private final BrandService brandService;
    @GetMapping("/load-more-brand")
    public ResponseEntity<?> loadMoreBrand(@RequestParam(name = "page") Integer page){
        Pageable pageable = PageRequest.of(page, 3);
        Page<BrandApp> brandAppPage = brandService.getAllWithPagination(pageable);
        return ResponseEntity.ok().body(brandAppPage.getContent());
    }
}
