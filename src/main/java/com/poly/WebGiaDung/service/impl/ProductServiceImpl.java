package com.poly.WebGiaDung.service.impl;

import com.poly.WebGiaDung.dto.ProductRequest;
import com.poly.WebGiaDung.entity.BrandApp;
import com.poly.WebGiaDung.entity.MyCategory;
import com.poly.WebGiaDung.entity.Product;
import com.poly.WebGiaDung.repo.ProductRepo;
import com.poly.WebGiaDung.service.*;
import com.poly.WebGiaDung.utils.SlugGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
    private final ProductImageService productImageService;
    private final ProductInfoService productInfoService;
    private final MyCategoryService categoryService;
    @Override
    public List<Product> getTopDiscount() {
        Page<Product> pageProducts = productRepo.getTopDiscount( PageRequest.of(0,PRODUCT_PER_TAB));
        List<Product> productList = pageProducts.getContent();
        return productList;
    }

    @Override
    public Product create(ProductRequest productRequest) {
        String slug = SlugGenerator.generateSlug(productRequest.getName());
        if(findBySlug(slug) != null){
            throw new RuntimeException("Sản phẩm đã tồn tại!");
        }

        Product product = new Product();

      /*  Optional<BrandApp> brandApp = brandService.findById(productRequest.getBrandAppId());
        if(brandApp.isEmpty()) throw new RuntimeException("Brand doesn't exist!");*/

        Optional<MyCategory> category = categoryService.findById(productRequest.getCategoryId());
        if(category.isEmpty()) throw new RuntimeException("Category doesn't exist!");

        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setPercentDiscount(productRequest.getPercentDiscount());
        product.setSlug(slug);
        product.setDescription(productRequest.getDescription());
        product.setImage(productRequest.getImage());
        product.setCategory(category.get());
        product.setBrandApp(productRequest.getBrandApp());
        product.setProductImages(productRequest.getSubImages());
        product.setProductInfo(productRequest.getListProductInfo());

        Product productSaved = productRepo.save(product);

        if(productRequest.getSubImages() != null){
            productSaved.getProductImages().forEach(productImage -> {
                productImage.setProduct(productSaved);
                productImageService.insert(productImage);
            });
        }

        if(productRequest.getListProductInfo() != null){
            productSaved.getProductInfo().forEach(productInfo -> {
                productInfo.setProduct(productSaved);
                productInfoService.insert(productInfo);
            });
        }
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

  /*  @Override
    public Page<Product> getByBrandIdAndActive(Integer brandId, Pageable pageable) {
        Optional<BrandApp> brandApp = brandService.findById(brandId);
        if (brandApp.isEmpty()) throw new IllegalArgumentException("Brand does not exist!");
        return productRepo.findByBrandAndActive(brandId, pageable);
    }*/

    @Override
    public Page<Product> findByKeyword(String keyword, Pageable pageable) {
        return productRepo.findByKeyword(keyword, pageable);
    }

    @Override
    public Page<Product> getProductsWithSortAndPagination(Pageable pageable) {
        return productRepo.findAll(pageable);
    }


    @Override // test
    public Product create(Product product) {
        String slug = SlugGenerator.generateSlug(product.getName());
        if(findBySlug(slug) != null){
            throw new RuntimeException("Product already exist!");
        }
        product.setSlug(slug);
        Product productSaved = productRepo.save(product);
       /* if(productSaved.getProductImages() != null){
            productSaved.getProductImages().forEach(productImage -> {
                productImage.setProduct(productSaved);
                productImageRepo.save(productImage);
            });
        }*/
        return productSaved;

    }

    @Override
    public void update(Product product) {
        Optional<Product> oldProduct = findById(product.getId());
        BeanUtils.copyProperties(product, oldProduct.get());


        Product productSaved = productRepo.save(oldProduct.get());


        productImageService.deleteByProduct(productSaved);
        if(product.getProductImages() != null){
            product.getProductImages().forEach(productImage -> {
                productImage.setProduct(productSaved);
                productImageService.insert(productImage);
            });
        }

        productInfoService.deleteByProduct(productSaved);
        if(product.getProductInfo() != null){
            product.getProductInfo().forEach(productInfo -> {
                if(productInfo.getKey() != null){
                    productInfo.setProduct(productSaved);
                    productInfoService.insert(productInfo);
                }
            });
        }
    }

    @Override
    public void updateStatus(Integer id, Boolean statusChanged) {
        Optional<Product> product = productRepo.findById(id);
        product.get().setIsActive(statusChanged);
        productRepo.save(product.get());
    }
    @Override
    public void deleteById(Integer id) {
            productRepo.deleteById(id);
    }

    @Override
    public List<Product> getListProductsByCategoryId(Integer id) {
        Optional<MyCategory> category = categoryService.findById(id);
        return productRepo.findByCategory(category.orElse(null));
    }

    @Override
    public List<Product> findByKeyword(String keyword) {
        return productRepo.findByKeyword(keyword);
    }
}
