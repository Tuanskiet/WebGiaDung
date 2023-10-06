package com.poly.WebGiaDung.service;

import com.poly.WebGiaDung.entity.Product;
import com.poly.WebGiaDung.entity.ProductInfo;

public interface ProductInfoService {
    ProductInfo insert(ProductInfo productInfo);

    void deleteByProduct(Product productSaved);
}
