package com.poly.WebGiaDung.repo;

import com.poly.WebGiaDung.entity.Evaluate;
import com.poly.WebGiaDung.entity.Product;
import com.poly.WebGiaDung.entity.UserApp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluateRepo extends JpaRepository<Evaluate, Integer> {
    List<Evaluate> findAllByProduct(Product product);

    @Query(value = "SELECT o FROM Evaluate o WHERE CONCAT(o.nameUser, '', o.product.name) LIKE %?1% AND o.product = ?2")
    Page<Evaluate> findByKeyword(String keyword, Product product, Pageable pageable);

//    @Query(value = "SELECT o FROM Evaluate o WHERE o.product = :product")
    Page<Evaluate> findAllByProduct(Product product, Pageable pageable);
}
