package com.poly.WebGiaDung.controller.admin;

import com.poly.WebGiaDung.dto.CartDto;
import com.poly.WebGiaDung.dto.OrderDto;
import com.poly.WebGiaDung.dto.OrderItemResponse;
import com.poly.WebGiaDung.dto.OrderResponse;
import com.poly.WebGiaDung.entity.*;
import com.poly.WebGiaDung.security.MyUserDetails;
import com.poly.WebGiaDung.service.CartItemService;
import com.poly.WebGiaDung.service.MyCategoryService;
import com.poly.WebGiaDung.service.OrderService;
import com.poly.WebGiaDung.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ManagerOrderController {
    private final OrderService orderService;
    private final MyCategoryService categoryService;
    private final CartItemService cartItemService;
    private static final int PRODUCT_PER_PAGE = 10;

    @GetMapping("/admin/manager-order")
    public String  viewManagerOrderPage(
                             @RequestParam(name="page", defaultValue = "1", required = false)  int page,
                             @RequestParam(name="sortBy",defaultValue = "id", required = false) String sortBy,
                             @RequestParam(name="orderBy", defaultValue = "asc",  required = false) String orderBy,
                             @RequestParam(name="keyword",  required = false) String keyword,
                             Model model){
        Pageable pageable = PageRequest.of(page - 1, PRODUCT_PER_PAGE)
                .withSort(Sort.by(Sort.Direction.fromString(orderBy), sortBy));
        Page<Order> listOrders = null;
        if(keyword != null){
            listOrders = orderService.findByKeyword(keyword, pageable);
            model.addAttribute("keyword", keyword);
        }else{
            listOrders = orderService.getWithSortAndPagination(pageable);
        }
        model.addAttribute("listOrders", listOrders.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", listOrders.getTotalPages());
        model.addAttribute("totalElements", listOrders.getTotalElements());

        return "admin/manager_order";
    }
    @GetMapping("/admin/manager-order/add")
    public String  viewAddOrderPage(Model model){
        model.addAttribute("newOrder", new Order());
        model.addAttribute("categories", categoryService.getAllCategory());
        return "admin/add_order";
    }

    @PostMapping("/admin/manager-order/add")
    @ResponseBody
    public String doCreateNewOrder(
            @RequestBody OrderDto orderDtoList,
            @AuthenticationPrincipal MyUserDetails userDetails,
            HttpSession session
    ){
        orderService.create(orderDtoList, userDetails.getUserApp());
        resetListCart(session, userDetails.getUserApp());
        return "OK";
    }

    @PostMapping("/admin/manager-order/update")
    public String doUpdateOrder(@ModelAttribute(name = "newOrder") Order order,
                                     Model model){
        try{
            orderService.insert(order);
            model.addAttribute("success", MessageUtils.Product.ADD_SUCCESS.getVal());
        }catch(Exception ex){
            model.addAttribute("error", MessageUtils.Product.ADD_FAILED.getVal());
            return "/admin/add_order";
        }
        return "redirect:/admin/manager-order";
    }

    @DeleteMapping("/admin/manager-order/delete")
    @ResponseBody
    public ResponseEntity<?> doDeleteOrder(@RequestParam(name = "id") Integer id){
        try{
            orderService.deleteById(id);
        }catch(Exception e){
//            e.printStackTrace();
            return ResponseEntity.status(500).body(MessageUtils.Product.ERROR_FOREIGN_KEY.getVal());
        }
        return ResponseEntity.status(204).body("DELETED");
    }

    @GetMapping("/admin/manager-order/edit")
    public String editOrder(@RequestParam(name = "id") String id,
                              Model model){
        Integer idIn = Integer.parseInt(id);
        Order order = orderService.findById(idIn).get();

        OrderResponse orderResponse = new OrderResponse();
        List<OrderItemResponse> orderItemResponseList = new ArrayList<>();
        BeanUtils.copyProperties(order, orderResponse);
        for (OrderItem orderItem : order.getListOrderItem()) {
            Product product = orderItem.getProduct();
            orderItemResponseList.add(new OrderItemResponse(
                    product.getId(),
                    orderItem.getQuantity(),
                    product.getName(),
                    orderItem.getOldPrice()
            ));
        }
        orderResponse.setOrderItems(orderItemResponseList);
        model.addAttribute("mode", "edit");
        model.addAttribute("editOrder", orderResponse);
        model.addAttribute("categories", categoryService.getAllCategory());
        return "admin/add_order.html";
    }


    private void resetListCart(HttpSession session, UserApp userApp){
        List<CartItem> cartDtoList = cartItemService.getCartsByUser(userApp);
        session.setAttribute("listCart", cartDtoList);
    }

}
