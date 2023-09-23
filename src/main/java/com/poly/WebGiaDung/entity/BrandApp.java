package com.poly.WebGiaDung.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="brands_app")
public class BrandApp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String name;

    @Column(length = 1024)
    private String image;

    @JsonIgnore
    @OneToMany(mappedBy = "brandApp", cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();

    public BrandApp(Integer id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }
}
