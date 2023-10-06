package com.poly.WebGiaDung.service;

import com.poly.WebGiaDung.entity.Product;
import com.poly.WebGiaDung.entity.ProductImage;

public interface ProductImageService {
    ProductImage insert(ProductImage productImage);

    void deleteByProduct(Product productSaved);
}
