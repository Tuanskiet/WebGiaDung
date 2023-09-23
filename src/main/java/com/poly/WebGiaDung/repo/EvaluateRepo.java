package com.poly.WebGiaDung.repo;

import com.poly.WebGiaDung.entity.Evaluate;
import com.poly.WebGiaDung.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluateRepo extends JpaRepository<Evaluate, Integer> {

    List<Evaluate> findAllByProduct(Product product);
}
