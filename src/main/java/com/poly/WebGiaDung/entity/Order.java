package com.poly.WebGiaDung.entity;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "[order]")
@Builder
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @Column(length = 1024)
    private String address;
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;
    private String note;

    @Column(name = "total_payment")
    private BigDecimal totalPayment;

    private String status = "Đang xử lí";

    @Column(name = "create_at")
    @CreationTimestamp
    private Timestamp createAt;

    @JsonIgnore //
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserApp userApp;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE, CascadeType.MERGE})
    private Set<OrderItem> listOrderItem = new HashSet<>();

    public Order(String name) {
        this.name = name;
    }

    public Order(String name, String address, String email, String phoneNumber, BigDecimal totalPayment, String status) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.totalPayment = totalPayment;
        this.status = status;
    }

    @Override
    public String toString() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(this);
        } catch (Exception e) {
            e.printStackTrace();
            return "{}";
        }
    }

}
