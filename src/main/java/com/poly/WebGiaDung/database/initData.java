package com.poly.WebGiaDung.database;

import com.poly.WebGiaDung.dto.CartDto;
import com.poly.WebGiaDung.entity.*;
import com.poly.WebGiaDung.enums.RoleName;
import com.poly.WebGiaDung.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//@Component
@RequiredArgsConstructor
public class initData implements CommandLineRunner {
    private final RoleService roleService;
    private final MyUserService userService;
    private final MyCategoryService categoryService;
    private final BrandService brandService;
    private final ProductService productService;
    private final ProductInfoService productInfoService;
    private final CartItemService cartItemService;
    public String imgTest = "//theme.hstatic.net/1000006063/1000748098/14/home_category_3_medium.png?v=13260";
    @Override
    public void run(String... args) throws Exception {
        //        init role
        RoleApp roleApp1 = new RoleApp(null, RoleName.ROLE_ADMIN);
        RoleApp roleApp2 = new RoleApp(null, RoleName.ROLE_USER);
        roleService.create(roleApp1);
        roleService.create(roleApp2);

        //        init user
        Set<RoleApp> roles1 = new HashSet<>();
        roles1.add(roleApp1);
        UserApp userApp1 = new UserApp(null,"tk", "123", "Tuan Kiet", true, roles1);

        Set<RoleApp> roles2 = new HashSet<>();
        roles2.add(roleApp2);
        UserApp userApp2 = new UserApp(null,"stellaprimo99@gmail.com", "123", "Duc Cong", true, roles2);

        userService.create(userApp1);
        userService.create(userApp2);


        MyCategory category1 = new MyCategory(  "Chăm Sóc Da Mặt Cao Cấp", "//theme.hstatic.net/1000006063/1000748098/14/home_category_3_medium.png?v=13260" );
        MyCategory category2 = new MyCategory(  "Trang Điểm Cao Cấp", "//theme.hstatic.net/1000006063/1000748098/14/home_category_3_medium.png?v=13260");
        MyCategory category3 = new MyCategory(  "Tẩy Trang","//theme.hstatic.net/1000006063/1000748098/14/home_category_3_medium.png?v=13260" );
        MyCategory category4 = new MyCategory("Sửa Rửa Mặt", "//theme.hstatic.net/1000006063/1000748098/14/home_category_3_medium.png?v=13260");
        MyCategory category5 = new MyCategory( "Tẩy Tế Bào Chết", "//theme.hstatic.net/1000006063/1000748098/14/home_category_3_medium.png?v=13260");
        MyCategory category6 = new MyCategory(  "Khẩu trang","//theme.hstatic.net/1000006063/1000748098/14/home_category_3_medium.png?v=13260" );
        MyCategory category7 = new MyCategory("Mặt Nạ Xông Hơi", "//theme.hstatic.net/1000006063/1000748098/14/home_category_3_medium.png?v=13260");
        MyCategory category8 = new MyCategory( "Chống Muỗi", "//theme.hstatic.net/1000006063/1000748098/14/home_category_3_medium.png?v=13260");
        MyCategory category9 = new MyCategory( "Bàn Chải Đánh Răng", "//theme.hstatic.net/1000006063/1000748098/14/home_category_3_medium.png?v=13260");

        categoryService.create(category1);
        categoryService.create(category2);
        categoryService.create(category3);
        categoryService.create(category4);
        categoryService.create(category5);
        categoryService.create(category6);
        categoryService.create(category7);
        categoryService.create(category8);
        categoryService.create(category9);

        //init brand
        BrandApp brandApp1 = new BrandApp(null, "Panasonic4", "//theme.hstatic.net/1000006063/1000748098/14/home_category_3_medium.png?v=13260");
        BrandApp brandApp2 = new BrandApp(null, "Panasonic1", "//theme.hstatic.net/1000006063/1000748098/14/home_category_3_medium.png?v=13260");
        BrandApp brandApp3 = new BrandApp(null, "Panasonic2", "//theme.hstatic.net/1000006063/1000748098/14/home_category_3_medium.png?v=13260");
        BrandApp brandApp4 = new BrandApp(null, "Panasonic3", "//theme.hstatic.net/1000006063/1000748098/14/home_category_3_medium.png?v=13260");
        BrandApp brandApp5 = new BrandApp(null, "Panasonic6", "//theme.hstatic.net/1000006063/1000748098/14/home_category_3_medium.png?v=13260");
        BrandApp brandApp6 = new BrandApp(null, "Panasonic14", "//theme.hstatic.net/1000006063/1000748098/14/home_category_3_medium.png?v=13260");
        BrandApp brandApp7 = new BrandApp(null, "Panasonic22", "//theme.hstatic.net/1000006063/1000748098/14/home_category_3_medium.png?v=13260");
        BrandApp brandApp8 = new BrandApp(null, "Panasonic53", "//theme.hstatic.net/1000006063/1000748098/14/home_category_3_medium.png?v=13260");
        BrandApp brandApp9 = new BrandApp(null, "Panasonic44", "//theme.hstatic.net/1000006063/1000748098/14/home_category_3_medium.png?v=13260");
        BrandApp brandApp10 = new BrandApp(null, "Panasonic12", "//theme.hstatic.net/1000006063/1000748098/14/home_category_3_medium.png?v=13260");
        BrandApp brandApp11 = new BrandApp(null, "Panasonic24", "//theme.hstatic.net/1000006063/1000748098/14/home_category_3_medium.png?v=13260");
        BrandApp brandApp12 = new BrandApp(null, "Panasonic35", "//theme.hstatic.net/1000006063/1000748098/14/home_category_3_medium.png?v=13260");

        brandService.create(brandApp1);
        brandService.create(brandApp2);
        brandService.create(brandApp3);
        brandService.create(brandApp4);
        brandService.create(brandApp5);
        brandService.create(brandApp6);
        brandService.create(brandApp7);
        brandService.create(brandApp8);
        brandService.create(brandApp9);
        brandService.create(brandApp12);
        brandService.create(brandApp11);
        brandService.create(brandApp10);

        //product info
        ProductInfo productInfo1 = new ProductInfo(null, "Chat lieu", "Nhom");
        ProductInfo productInfo2 = new ProductInfo(null, "Thong so 1 ", "Ths1");
        ProductInfo productInfo3 = new ProductInfo(null, "Thong so 2 ", "Ths2");
        productInfoService.insert(productInfo1);
        productInfoService.insert(productInfo2);
        productInfoService.insert(productInfo3);

        //product
        Product product1 = new Product("product1", BigDecimal.valueOf(999), 9d, imgTest, category1, brandApp1);
        Product product2 = new Product("product2", BigDecimal.valueOf(999), 9d, imgTest, category1, brandApp2);
        Product product3 = new Product("product3", BigDecimal.valueOf(999), 9d, imgTest, category1, brandApp2);
        Product product4 = new Product("product4", BigDecimal.valueOf(999), 9d, imgTest, category1, brandApp3);
        Product product5 = new Product("product5", BigDecimal.valueOf(999), 9d, imgTest, category1, brandApp3);
        Product product6 = new Product("product6", BigDecimal.valueOf(999), 9d, imgTest, category1, brandApp3);
        Product product7 = new Product("product7", BigDecimal.valueOf(999), 9d, imgTest, category1, brandApp3);
        Product product8 = new Product("product8", BigDecimal.valueOf(999), 9d, imgTest, category2, brandApp3);
        Product product9 = new Product("product9", BigDecimal.valueOf(999), 9d, imgTest, category2, brandApp3);
        Product product10 = new Product("product10", BigDecimal.valueOf(999), 9d, imgTest, category3, brandApp3);
        product1.setProductInfo(List.of(productInfo1,productInfo2,productInfo3));
        productService.create(product1);
        productService.create(product2);
        productService.create(product3);
        productService.create(product4);
        productService.create(product5);
        productService.create(product6);
        productService.create(product7);
        productService.create(product8);
        productService.create(product9);
        productService.create(product10);

        CartDto cartDto1 = new CartDto(1, 1 );
        CartDto cartDto2 = new CartDto(1, 2);
        CartDto cartDto3 = new CartDto(1, 3);
        cartItemService.addToCart(cartDto1, userApp1);
        cartItemService.addToCart(cartDto2, userApp1);
        cartItemService.addToCart(cartDto3, userApp1);
    }
}
