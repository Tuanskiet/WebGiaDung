package com.poly.WebGiaDung.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyCategoryDto {
    private Integer id;
    private String name;

    @Column(name="image",length = 1024)
    private String image;

    @Column(unique = true)
    private String slug;

    private String policy;

    private String type;

    private List<String> listKeys;

    @Column(name = "is_active")
    private Boolean isActive = false;

}
