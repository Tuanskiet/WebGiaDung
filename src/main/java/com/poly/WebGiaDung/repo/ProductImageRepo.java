package com.poly.WebGiaDung.repo;

import com.poly.WebGiaDung.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepo extends JpaRepository<ProductImage, Integer> {
}
