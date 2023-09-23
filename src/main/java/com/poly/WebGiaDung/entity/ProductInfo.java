package com.poly.WebGiaDung.entity;

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


}
