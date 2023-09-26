package com.poly.WebGiaDung.repo;

import com.poly.WebGiaDung.entity.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductInfoRepo extends JpaRepository<ProductInfo, Integer> {
}
