package com.poly.WebGiaDung.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="product_info")
public class ProductInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "[key]")
    private String key;

    @Column(name = "[value]")
    private String value;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public ProductInfo(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
