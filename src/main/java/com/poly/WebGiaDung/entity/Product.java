package com.poly.WebGiaDung.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="[products]")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(name="price", precision = 12, scale = 3)
    private BigDecimal price;

    @Column(name="percent_discount")
    private Double percentDiscount = 0d;

    @Column(name="date_release")
    @CreationTimestamp
    private Timestamp dateRelease;

    private String slug;

//    @Column(name="short_description", columnDefinition = "TEXT")
//    private String shortDescription;

    @Column(name="full_description", columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String image;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private MyCategory category;

  /*  @JsonIgnore
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    private BrandApp brandApp;*/
    private String brandApp;

    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductImage> productImages = new ArrayList<>();


    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Evaluate> evaluates = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductInfo> productInfo = new ArrayList<>();


    public Product(String name, BigDecimal price, Double percentDiscount, String image, MyCategory category, BrandApp brandApp) {
        this.name = name;
        this.price = price;
        this.percentDiscount = percentDiscount;
        this.image = image;
        this.category = category;
    }

    @Transient
    public BigDecimal getPriceDiscount(){
        BigDecimal priceDiscount = price.multiply(BigDecimal.valueOf((100 - percentDiscount)/100));
        BigDecimal result = priceDiscount.setScale(0, RoundingMode.CEILING);
        return result;
    }

    @Transient
    public Integer getAvgStar(){
        if(evaluates.size() == 0 ){
            return 5; // 5 star
        }
        OptionalDouble avgStar = evaluates.stream().mapToInt(Evaluate::getNumStar).average();
        return  (int) Math.ceil(avgStar.getAsDouble());
    }


}
