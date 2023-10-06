package com.poly.WebGiaDung.repo;

import com.poly.WebGiaDung.entity.Product;
import com.poly.WebGiaDung.entity.ProductImage;
import com.poly.WebGiaDung.entity.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductInfoRepo extends JpaRepository<ProductInfo, Integer> {
    List<ProductInfo> findByProduct(Product product);
}
