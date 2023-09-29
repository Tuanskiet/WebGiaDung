package com.poly.WebGiaDung.service.impl;

import com.poly.WebGiaDung.dto.CartDto;
import com.poly.WebGiaDung.dto.OrderDto;
import com.poly.WebGiaDung.entity.Order;
import com.poly.WebGiaDung.entity.OrderItem;
import com.poly.WebGiaDung.entity.Product;
import com.poly.WebGiaDung.entity.UserApp;
import com.poly.WebGiaDung.repo.OrderItemRepo;
import com.poly.WebGiaDung.repo.OrderRepo;
import com.poly.WebGiaDung.service.CartItemService;
import com.poly.WebGiaDung.service.OrderService;
import com.poly.WebGiaDung.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepo orderRepo;
    private final ProductService productService;
    private final CartItemService cartItemService;
    private final OrderItemRepo orderItemRepo;

    @Transactional
    @Override
    public void create(OrderDto orderDtoList, UserApp currentUser) {
        Order newOrder = new Order();
        if(orderDtoList.getIdOrder() != null && orderDtoList.getIdOrder() != -1){
            newOrder = orderRepo.findById(orderDtoList.getIdOrder()).get();
        }
        BeanUtils.copyProperties(orderDtoList, newOrder);
        /*BigDecimal totalPrice = orderDtoList.getCartDtoList().stream()
                        .map(CartDto::getTotalPrice)
                        .reduce(BigDecimal.ZERO,BigDecimal::add);*/
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (CartDto cartDto : orderDtoList.getCartDtoList()) {
            Product product = productService.findById(cartDto.getProductId()).get();
            BigDecimal totalItem = product.getPriceDiscount().multiply(BigDecimal.valueOf(cartDto.getQuantity()));
            totalPrice = totalPrice.add(totalItem);
        }

        newOrder.setStatus(orderDtoList.getStatus());
        newOrder.setUserApp(currentUser);
        newOrder.setTotalPayment(totalPrice);

        orderRepo.save(newOrder);

        orderItemRepo.deleteAllByOrder(newOrder);

        Set<OrderItem> listOrderItem = new HashSet<>();

        for (CartDto item : orderDtoList.getCartDtoList() ) {
            Product product = productService.findById(item.getProductId()).get();
            OrderItem orderItem = new OrderItem(item.getQuantity(),
                    product.getName(), product.getPrice());
            orderItem.setImageProduct(product.getImage());
            orderItem.setProductSlug(product.getSlug());
            listOrderItem.add(orderItem);
            orderItem.setOrder(newOrder);
            orderItemRepo.save(orderItem);
        }
        updateAfterOrder(orderDtoList.getCartDtoList(), currentUser);
    }
//
    @Override
    public Page<Order> findByKeyword(String keyword, Pageable pageable) {
        return orderRepo.findByKeyword(keyword, pageable);
    }

    @Override
    public Page<Order> getWithSortAndPagination(Pageable pageable) {
        return orderRepo.findAllWithSortByDateCreate(pageable);
    }

    @Override
    public Order insert(Order order) {
        return orderRepo.save(order);
    }

    @Override
    public void deleteById(Integer id) {
        orderRepo.deleteById(id);
    }

    @Override
    public Optional<Order> findById(Integer id) {
        Optional<Order> order = orderRepo.findById(id);
        if(order.isEmpty()){
            throw new RuntimeException("Order doesn't exist!");
        }
        return order;
    }

    @Override
    public List<Order> findByUser(UserApp currentUser) {
        return orderRepo.findAllByUserApp(currentUser);
    }

    @Override
    public List<Order> findByUserAndStatus(UserApp currentUser, String status) {
        return orderRepo.findByUserAndStatus(currentUser, status);
    }

    private void updateAfterOrder(List<CartDto> cartDtoList, UserApp currentUser) {
        cartDtoList.stream().forEach(item -> {
            // update quantity product after order
            Product product = productService.findById(item.getProductId()).get();
            // remove cart item  after order
            cartItemService.deleteCartItem(item.getProductId(), currentUser);
        });
    }
}
