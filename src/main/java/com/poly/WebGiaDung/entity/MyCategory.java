package com.poly.WebGiaDung.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.poly.WebGiaDung.enums.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "my_categories")
public class MyCategory implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @Column(columnDefinition = "TEXT")
    private String policy;

    private String type; //Product | Service

    @Column(columnDefinition = "TEXT")
    private String image;

    @Column(unique = true)
    private String slug;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @JsonIgnore
    @ElementCollection
    @Column(name = "list_keys")
    private List<String> listKeys = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "category", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();

    public MyCategory(String name,  String type, String image) {
        this.name = name;
        this.image = image;
        this.type = type;
    }


}
