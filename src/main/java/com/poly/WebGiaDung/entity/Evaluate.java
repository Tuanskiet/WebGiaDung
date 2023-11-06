package com.poly.WebGiaDung.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "[evaluate]")
@Builder
public class Evaluate implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer numStar;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String nameUser;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    public Evaluate(Integer numStar, String content, Product product) {
        this.numStar = numStar;
        this.content = content;
        this.product = product;
    }

    //    @Override
//    public String toString() {
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            return objectMapper.writeValueAsString(this);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "{}";
//        }
//    }

}
