package com.poly.WebGiaDung.service;

import com.poly.WebGiaDung.entity.BrandApp;

import java.util.List;
import java.util.Optional;

public interface BrandService {
    List<BrandApp> getAll();

    BrandApp create(BrandApp brand);

    Optional<BrandApp> findById(Integer brandId);
}
