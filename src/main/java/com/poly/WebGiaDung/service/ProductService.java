package com.poly.WebGiaDung.service;

import com.poly.WebGiaDung.dto.ProductRequest;
import com.poly.WebGiaDung.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getTopDiscount();

    Product create(ProductRequest productRequest);
    Product findBySlug(String slug);

    List<Product> findByCategoryId(Integer id);
    Optional<Product> findById(Integer id);
    Page<Product> getListProductsByCategory(String categorySlug, Pageable pageable);
    Page<Product> findByKeywordAndActive(String keyword, Pageable pageable);
    Page<Product> getAllAndActiveTrue(Pageable pageable);

//    Page<Product> getByBrandIdAndActive(Integer brandId, Pageable pageable);

    Page<Product> findByKeyword(String keyword, Pageable pageable);

    Page<Product> getProductsWithSortAndPagination(Pageable pageable);

    Product create(Product product);

    void update(Product product);
    void updateStatus(Integer id, Boolean statusChanged);

    void deleteById(Integer id);

    List<Product> getListProductsByCategoryId(Integer id);

    List<Product> findByKeyword(String keyword);
}
