package com.poly.WebGiaDung.repo;

import com.poly.WebGiaDung.entity.Order;
import com.poly.WebGiaDung.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepo extends JpaRepository<OrderItem, Integer> {
    void deleteAllByOrder(Order newOrder);
}
