package com.poly.WebGiaDung.service;

import com.poly.WebGiaDung.entity.MyCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface MyCategoryService {
    List<MyCategory> getAllCategory();

    MyCategory create(MyCategory category);

    Page<MyCategory> findByKeyword(String keyword, Pageable pageable);

    Page<MyCategory> getWithSortAndPagination(Pageable pageable);

    void deleteById(Integer id);

    Optional<MyCategory> findById(Integer id);

    void updateStatus(Integer id, Boolean statusChanged);

    List<MyCategory> getAllCategoryActive();

    Optional<MyCategory> findBySlug(String slug);

    List<MyCategory> getTop5ByType(String product);
}
