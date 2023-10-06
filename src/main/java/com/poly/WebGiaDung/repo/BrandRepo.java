package com.poly.WebGiaDung.repo;

import com.poly.WebGiaDung.entity.BrandApp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BrandRepo extends JpaRepository<BrandApp, Integer> {

    @Query("SELECT c FROM BrandApp c WHERE c.name LIKE CONCAT('%', :keyword, '%')")
    Page<BrandApp> findByKeyword(String keyword, Pageable pageable);
}
