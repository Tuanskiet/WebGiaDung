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
    private final ProductService productService;
    private final ProductInfoService productInfoService;
    private final CartItemService cartItemService;
    public String imgTest = "https://bizweb.dktcdn.net/thumb/large/100/488/001/products/smarttivisamsung43inch1-0fc67214-4b9e-4d9e-9a9d-c9e8e984503a.jpg?v=1686045374560";
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
        UserApp userApp1 = new UserApp(null,"admin", "123", "Luc Tuan Kiet", true, roles1);

        Set<RoleApp> roles2 = new HashSet<>();
        roles2.add(roleApp2);
        UserApp userApp2 = new UserApp(null,"stellaprimo99@gmail.com", "123", "Duc Cong", true, roles2);

        userService.create(userApp1);
        userService.create(userApp2);

        MyCategory category1 = new MyCategory(  "Thang nhôm", "product", "https://cdn.tgdd.vn/Products/Images/1942/309559/android-tivi-aqua-32-inch-le32aqt6600g-8-550x340.jpg" );
        MyCategory category2 = new MyCategory(  "Máy lọc nước","product",  "https://cdn.tgdd.vn/Products/Images/1943/220325/samsung-rt22m4032by-sv-300x300.jpg");
        MyCategory category3 = new MyCategory(  "Linh kiện máy lọc nước","product", "https://cdn.tgdd.vn/Products/Images/1944/242732/aqua-88-kg-aqw-fr88gtbk-300x300.jpg" );
        MyCategory category4 = new MyCategory("Máy lọc không khí", "product", "https://cdn.tgdd.vn/Products/Images/2202/272143/may-say-thong-hoi-electrolux-75-kg-edv754h3wb-170223-031343-600x600.jpg");
        MyCategory category5 = new MyCategory( "Dây thoát hiểm", "product", "https://cdn.tgdd.vn/Products/Images/3385/309367/may-loc-nuoc-ro-hoa-phat-hws1b-1022-10-loi-230623-025945-600x600.jpg");
        MyCategory category6 = new MyCategory(  "Rèm cửa","product", "https://cdn.tgdd.vn/Products/Images/1982/236629/bep-tu-doi-hafele-hc-i2712a-10-600x600.jpg" );
        MyCategory category7 = new MyCategory("Máy lạnh", "product", "https://cdn.tgdd.vn/Products/Images/10139/264374/samsung-vr05r5050wk-sv-100423-014048-600x600.jpg");
        MyCategory category8 = new MyCategory( "Máy hút ẩm", "product", "https://cdn.tgdd.vn/Products/Images/2162/306932/loa-keo-karaoke-dalton-ts-12g350n-100523-094658-600x600.jpg");
        MyCategory category9 = new MyCategory( "Thi công công trình", "service", "https://cdn.tgdd.vn/Products/Images/2162/306932/loa-keo-karaoke-dalton-ts-12g350n-100523-094658-600x600.jpg");
        MyCategory category10 = new MyCategory( "Sửa chữa - lắp đặt", "service", "https://cdn.tgdd.vn/Products/Images/2162/306932/loa-keo-karaoke-dalton-ts-12g350n-100523-094658-600x600.jpg");
        MyCategory category11 = new MyCategory( "Vệ sinh - bảo dưỡng", "service", "https://cdn.tgdd.vn/Products/Images/2162/306932/loa-keo-karaoke-dalton-ts-12g350n-100523-094658-600x600.jpg");

        categoryService.create(category1);
        categoryService.create(category2);
        categoryService.create(category3);
        categoryService.create(category4);
        categoryService.create(category5);
        categoryService.create(category6);
        categoryService.create(category7);
        categoryService.create(category8);
        categoryService.create(category9);
        categoryService.create(category10);
        categoryService.create(category11);

        //product
        Product product1 = new Product("Máy lạnh Daikin Inverter", BigDecimal.valueOf(999), 9d, "https://bizweb.dktcdn.net/100/488/001/products/maylanhdaikininverter25hp.jpg?v=1686045426487", category1);
        Product product2 = new Product("Máy xay đa năng Panasonic ", BigDecimal.valueOf(999), 9d, "https://bizweb.dktcdn.net/100/488/001/products/may-xay-sinh-to-multi-0-77-1.jpg?v=1686119933390", category1);
        Product product3 = new Product("Máy lạnh tủ đứng ", BigDecimal.valueOf(999), 9d, "https://bizweb.dktcdn.net/thumb/large/100/488/001/products/may-lanh-tu-dung-funiki-fc50-5-1.jpg?v=1686062079340", category1);

        productService.create(product1);
        productService.create(product2);
        productService.create(product3);

        //product info
        ProductInfo productInfo1 = new ProductInfo("Xuất xứ", "Thương hiệu: Nhật - Sản xuất tại: Việt Nam");
        ProductInfo productInfo2 = new ProductInfo("Loại Gas lạnh ", "R32");
        ProductInfo productInfo3 = new ProductInfo("Loại máy ", "Inverter - loại 1 chiều (chỉ làm lạnh)");
        ProductInfo productInfo4 = new ProductInfo("Loại máy ", "Inverter - loại 2 chiều (chỉ làm lạnh)");
        productInfo1.setProduct(product1);
        productInfo2.setProduct(product1);
        productInfo3.setProduct(product2);
        productInfo3.setProduct(product3);
        productInfoService.insert(productInfo1);
        productInfoService.insert(productInfo2);
        productInfoService.insert(productInfo3);
        productInfoService.insert(productInfo4);

        CartDto cartDto1 = new CartDto(1, 1 );
        CartDto cartDto2 = new CartDto(1, 2);
        CartDto cartDto3 = new CartDto(1, 3);
        cartItemService.addToCart(cartDto1, userApp1);
        cartItemService.addToCart(cartDto2, userApp1);
        cartItemService.addToCart(cartDto3, userApp1);
    }
}
