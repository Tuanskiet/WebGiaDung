package com.poly.WebGiaDung.repo;

import com.poly.WebGiaDung.entity.Product;
import com.poly.WebGiaDung.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepo extends JpaRepository<ProductImage, Integer> {


    List<ProductImage> findByProduct(Product product);
}
