package com.poly.WebGiaDung.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "[order_item]")
@Builder
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer quantity;

    private String name; // name product
    private BigDecimal price;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;
    private String productSlug;
    private String imageProduct;

    public OrderItem(Integer quantity, String name, BigDecimal price) {
        this.quantity = quantity;
        this.name = name;
        this.price = price;
    }
}
