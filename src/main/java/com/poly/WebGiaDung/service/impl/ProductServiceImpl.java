package com.poly.WebGiaDung.service.impl;

import com.poly.WebGiaDung.entity.BrandApp;
import com.poly.WebGiaDung.entity.Product;
import com.poly.WebGiaDung.repo.ProductRepo;
import com.poly.WebGiaDung.service.BrandService;
import com.poly.WebGiaDung.service.ProductService;
import com.poly.WebGiaDung.utils.SlugGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private static final int PRODUCT_PER_TAB = 10;

    private final ProductRepo productRepo;
    private final BrandService brandService;
    @Override
    public List<Product> getTopDiscount() {
        Page<Product> pageProducts = productRepo.getTopDiscount( PageRequest.of(0,PRODUCT_PER_TAB));
        List<Product> productList = pageProducts.getContent();
        return productList;
    }

    @Override
    public Product create(Product product) {
        String slug = SlugGenerator.generateSlug(product.getName());
        if(findBySlug(slug) != null){
            throw new RuntimeException("Product already exist!");
        }
        product.setSlug(slug);
        Product productSaved = productRepo.save(product);
//        if(productSaved.getProductImages() != null){
//            productSaved.getProductImages().forEach(productImage -> {
//                productImage.setProduct(productSaved);
//                productImageRepo.save(productImage);
//            });
//        }
        return productSaved;
    }
    @Override
    public Product findBySlug(String slug) {
        return productRepo.findBySlug(slug);
    }

    @Override
    public List<Product> findByCategoryId(Integer id) {
        return productRepo.getListProductByCategoryId(id);
    }
    @Override
    public Optional<Product> findById(Integer id) {
        Optional<Product> product = productRepo.findById(id);
        if(product.isEmpty()){
            throw new RuntimeException("Product doesn't exist!");
        }
        return product;
    }
    @Override
    public Page<Product> getListProductsByCategory(String categorySlug, Pageable pageable) {
        return productRepo.getProductActiveByCategory(categorySlug, pageable);
    }
    @Override
    public Page<Product> findByKeywordAndActive(String keyword, Pageable pageable) {
        return productRepo.findByKeywordAndActive(keyword, pageable);
    }
    @Override
    public Page<Product> getAllAndActiveTrue(Pageable pageable) {
        return productRepo.findByActiveTrue(pageable);
    }

    @Override
    public Page<Product> getByBrandIdAndActive(Integer brandId, Pageable pageable) {
        Optional<BrandApp> brandApp = brandService.findById(brandId);
        if (brandApp.isEmpty()) throw new IllegalArgumentException("Brand does not exist!");
        return productRepo.findByBrandAndActive(brandId, pageable);
    }
}
