package com.poly.WebGiaDung.service.impl;

import com.poly.WebGiaDung.entity.BrandApp;
import com.poly.WebGiaDung.repo.BrandRepo;
import com.poly.WebGiaDung.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {
    private final BrandRepo brandRepo;
    @Override
    public List<BrandApp> getAll() {
        return brandRepo.findAll();
    }

    @Override
    public BrandApp create(BrandApp brand) {
        return brandRepo.save(brand);
    }

    @Override
    public Optional<BrandApp> findById(Integer brandId) {
        return brandRepo.findById(brandId);
    }

    @Override
    public Page<BrandApp> getAllWithPagination(Pageable pageable) {
        return brandRepo.findAll(pageable);
    }


    @Override
    public void deleteById(Integer id) {
        brandRepo.deleteById(id);
    }

    @Override
    public Page<BrandApp> findByKeyword(String keyword, Pageable pageable) {
        return brandRepo.findByKeyword(keyword, pageable);
    }

    @Override
    public Page<BrandApp> getWithSortAndPagination(Pageable pageable) {
        return brandRepo.findAll(pageable);
    }
}
