package com.poly.WebGiaDung.repo;

import com.poly.WebGiaDung.entity.BrandApp;
import com.poly.WebGiaDung.entity.MyCategory;
import com.poly.WebGiaDung.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {
    Product findBySlug(String slug);

    @Query(value = "SELECT * FROM products p WHERE is_active = true ORDER BY percent_discount DESC", nativeQuery = true )
    Page<Product> getTopDiscount(Pageable pageable);

    @Query(value = "SELECT * FROM products p WHERE is_active = true ORDER BY date_release DESC", nativeQuery = true )
    Page<Product> getTopDateRelease(Pageable pageable);

    @Query(value = "SELECT p FROM Product p WHERE p.category.slug = :categorySlug AND isActive = true" )
    Page<Product> getProductActiveByCategory(String categorySlug, Pageable pageable);

    @Query(value = "SELECT * FROM products WHERE name LIKE %:keyword%", nativeQuery = true)
    Page<Product> findByKeyword(String keyword, Pageable pageable);
    @Query(value = "SELECT * FROM products  WHERE name LIKE %:keyword%", nativeQuery = true)
    List<Product> findByKeyword(String keyword);

    List<Product> findByCategory(MyCategory category);

    @Query(value = "SELECT p FROM Product p WHERE p.isActive = true" )
    Page<Product> findByActiveTrue(Pageable pageable);

    @Query(value = "SELECT p FROM Product p  WHERE name LIKE %:keyword% AND p.isActive = true" )
    Page<Product> findByKeywordAndActive(String keyword, Pageable pageable);

    @Modifying
    @Query("UPDATE Product p SET p.percentDiscount = :percentDiscount WHERE p.category = :category")
    void updatePercentDiscountByCategory(@Param("percentDiscount") Double percentDiscount, @Param("category") MyCategory category);

    @Query(value = "SELECT p FROM Product p WHERE p.isActive = true AND p.category.id = :categoryId" )
    List<Product> getListProductByCategoryId(Integer categoryId);

    @Query(value = "SELECT p FROM Product p WHERE p.isActive = true AND p.brandApp.id = :brandAppId " )
    Page<Product> findByBrandAndActive(Integer brandAppId, Pageable pageable);
}
