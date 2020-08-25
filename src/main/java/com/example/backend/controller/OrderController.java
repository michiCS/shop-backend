/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.backend.controller;

import com.example.backend.entities.Order;
import com.example.backend.entities.OrderItem;
import com.example.backend.entities.Product;
import com.example.backend.entities.ShoppingCart;
import com.example.backend.entities.ShoppingCartItem;
import com.example.backend.entities.User;
import com.example.backend.jwt.JwtTokenService2;
import com.example.backend.models.OrderDto;
import com.example.backend.models.OrderItemDto;
import com.example.backend.repositories.OrderItemRepository;
import com.example.backend.repositories.OrderRepository;
import com.example.backend.repositories.ShoppingCartItemRepository;
import com.example.backend.repositories.ShoppingCartRepository;
import com.example.backend.repositories.UserRepository;
import com.example.backend.services.DbService;
import com.example.backend.services.OrderService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author wagne
 */
@RestController
@CrossOrigin
public class OrderController {


    private JwtTokenService2 jwtService;
    
    private OrderService orderService;

    public OrderController(JwtTokenService2 jwtService, OrderService orderService) {
        this.jwtService = jwtService;
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderDto>> getOrdersForUser(HttpServletRequest req) {
        Map<String, Object> result = new HashMap<String, Object>();
        String email = jwtService.getEmailFromRequest(req);
        List<Order> orders = orderService.getUserOrders(email);
        Comparator<Order> orderDateComp = Comparator.comparing(Order::getDate, (d1, d2) -> {
            return d2.compareTo(d1);
        });
        orders.sort(orderDateComp);
        List<OrderDto> orderDtos = new ArrayList<OrderDto>();
        for(Order order : orders) {
            List<OrderItemDto> orderItemDtos = new ArrayList<OrderItemDto>();
            for(OrderItem orderItem : order.getOrderItems()) {
                Product prod = orderItem.getProduct();
                OrderItemDto orderItemDto = new OrderItemDto(orderItem.getQuantity(), prod.getId(), prod.getName(), prod.getPrice(), prod.getImagePath(), prod.isDeleted());
                orderItemDtos.add(orderItemDto);
            }
            OrderDto orderDto = new OrderDto(order.getId(), order.getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)), orderItemDtos);
            orderDtos.add(orderDto);
        } 
       
        return new ResponseEntity(orderDtos, HttpStatus.OK);
    }

    @PostMapping("/orders")
    public ResponseEntity<Order> makeOrder(HttpServletRequest req) {
        String email = jwtService.getEmailFromRequest(req);
        Order order = orderService.createOrder(email);
        
        return new ResponseEntity(order, HttpStatus.CREATED);
    }
}
