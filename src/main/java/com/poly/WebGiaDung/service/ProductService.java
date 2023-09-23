package com.poly.WebGiaDung.service;

import com.poly.WebGiaDung.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getTopDiscount();

    Product create(Product product1);
    Product findBySlug(String slug);

    List<Product> findByCategoryId(Integer id);
    Optional<Product> findById(Integer id);
    Page<Product> getListProductsByCategory(String categorySlug, Pageable pageable);
    Page<Product> findByKeywordAndActive(String keyword, Pageable pageable);
    Page<Product> getAllAndActiveTrue(Pageable pageable);

    Page<Product> getByBrandIdAndActive(Integer brandId, Pageable pageable);
}
