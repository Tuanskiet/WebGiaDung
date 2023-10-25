package com.poly.WebGiaDung.repo;

import com.poly.WebGiaDung.entity.MyCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MyCategoryRepo extends JpaRepository<MyCategory, Integer> {
    MyCategory findMyCategoryBySlug(String slug);
    @Query(value = "SELECT * FROM my_categories  WHERE name LIKE %:keyword%", nativeQuery = true)
    Page<MyCategory> findByKeyword(String keyword, Pageable pageable);

    List<MyCategory> findByIsActiveTrue();

    Optional<MyCategory> findBySlug(String slug);

    List<MyCategory> findTop5ByTypeAndIsActiveTrue(String type);
}
