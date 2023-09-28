package com.poly.WebGiaDung.service;

import com.poly.WebGiaDung.dto.OrderDto;
import com.poly.WebGiaDung.entity.Order;
import com.poly.WebGiaDung.entity.UserApp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.jaxb.SpringDataJaxb;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    void create(OrderDto orderDtoList, UserApp currentUser);

    Page<Order> findByKeyword(String keyword, Pageable pageable);

    Page<Order> getWithSortAndPagination(Pageable pageable);

    Order insert(Order order);

    void deleteById(Integer id);

    Optional<Order> findById(Integer id);
    List<Order> findByUser(UserApp currentUser);

    List<Order> findByUserAndStatus(UserApp currentUser, String status);
}
