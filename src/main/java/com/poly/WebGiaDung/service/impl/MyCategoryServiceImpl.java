package com.poly.WebGiaDung.service.impl;

import com.poly.WebGiaDung.entity.MyCategory;
import com.poly.WebGiaDung.repo.MyCategoryRepo;
import com.poly.WebGiaDung.service.MyCategoryService;
import com.poly.WebGiaDung.utils.SlugGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyCategoryServiceImpl implements MyCategoryService {
    private final MyCategoryRepo categoryRepo;
    @Override
    public List<MyCategory> getAllCategory() {
        return categoryRepo.findAll();
    }

    @Override
    public MyCategory create(MyCategory category) {
        if(findCategoryBySlug(category.getSlug()) != null){
            throw new RuntimeException("Category already Exists!");
        }
        String slug = SlugGenerator.generateSlug(category.getName());
        category.setSlug(slug);
        return categoryRepo.save(category);
    }

    @Override
    public Page<MyCategory> findByKeyword(String keyword, Pageable pageable) {
        return categoryRepo.findByKeyword(keyword, pageable);
    }

    @Override
    public Page<MyCategory> getWithSortAndPagination(Pageable pageable) {
        return categoryRepo.findAll(pageable);
    }

    @Override
    public void deleteById(Integer id) {
//        Optional<MyCategory> categoryDelete = findById(id);
        categoryRepo.deleteById(id);
    }

    @Override
    public Optional<MyCategory> findById(Integer id) {
        Optional<MyCategory> category = categoryRepo.findById(id);
        if(category.isEmpty()){
            throw new RuntimeException("Category doesn't exist!");
        }
        return category;
    }

    @Override
    public void updateStatus(Integer id, Boolean statusChanged) {
        Optional<MyCategory> category = categoryRepo.findById(id);
        category.get().setIsActive(statusChanged);
        categoryRepo.save(category.get());
    }

    @Override
    public List<MyCategory> getAllCategoryActive() {
        return categoryRepo.findByIsActiveTrue();
    }

    @Override
    public Optional<MyCategory> findBySlug(String slug) {
        return categoryRepo.findBySlug(slug);
    }

    @Override
    public List<MyCategory> getTop5ByType(String type) {
        return categoryRepo.findTop5ByTypeAndIsActiveTrue(type);
    }

    @Override
    public MyCategory findFirst() {
        return categoryRepo.findTop1ByOrderById();
    }

    private MyCategory findCategoryBySlug(String slug){
        return categoryRepo.findMyCategoryBySlug(slug);
    }
}
