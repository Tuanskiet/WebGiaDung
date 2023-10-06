package com.poly.WebGiaDung.service.impl;

import com.poly.WebGiaDung.entity.Product;
import com.poly.WebGiaDung.entity.ProductImage;
import com.poly.WebGiaDung.entity.ProductInfo;
import com.poly.WebGiaDung.repo.ProductInfoRepo;
import com.poly.WebGiaDung.service.ProductInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductInfoServiceImpl implements ProductInfoService {

    private final ProductInfoRepo productInfoRepo;

    @Override
    public ProductInfo insert(ProductInfo productInfo) {
        return productInfoRepo.save(productInfo);
    }

    @Override
    public void deleteByProduct(Product product) {
        List<ProductInfo> productInfoList = productInfoRepo.findByProduct(product);
        productInfoList.forEach(item -> {
            productInfoRepo.deleteById(item.getId());
        });
    }
}
