package com.poly.WebGiaDung.service;

import com.poly.WebGiaDung.entity.BrandApp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BrandService {
    List<BrandApp> getAll();

    BrandApp create(BrandApp brand);

    Optional<BrandApp> findById(Integer brandId);

    Page<BrandApp> getAllWithPagination(Pageable pageable);
}
