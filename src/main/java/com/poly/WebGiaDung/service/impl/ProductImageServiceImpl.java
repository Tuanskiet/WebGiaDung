package com.poly.WebGiaDung.service.impl;

import com.poly.WebGiaDung.entity.Product;
import com.poly.WebGiaDung.entity.ProductImage;
import com.poly.WebGiaDung.repo.ProductImageRepo;
import com.poly.WebGiaDung.service.ProductImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductImageServiceImpl implements ProductImageService {

    private final ProductImageRepo productImageRepo;
    @Override
    public ProductImage insert(ProductImage productImage) {
        return productImageRepo.save(productImage);
    }


    @Transactional
    @Override
    public void deleteByProduct(Product product) {
        List<ProductImage> productImageList = productImageRepo.findByProduct(product);
        productImageList.forEach(item -> {
            productImageRepo.deleteById(item.getId());
        });
    }
}
