package com.poly.WebGiaDung.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.poly.WebGiaDung.entity.BrandApp;
import com.poly.WebGiaDung.entity.MyCategory;
import com.poly.WebGiaDung.entity.ProductImage;
import com.poly.WebGiaDung.entity.ProductInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private String name;

    private BigDecimal price;

    private Double percentDiscount = 0d;

    private String description;

    private String image;

    private Integer categoryId;

    private Integer brandAppId;

    private List<ProductImage> subImages;

    private List<ProductInfo> listProductInfo;
}
