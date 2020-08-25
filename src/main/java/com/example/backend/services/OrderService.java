/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.backend.services;

import com.example.backend.entities.Order;
import com.example.backend.entities.OrderItem;
import com.example.backend.entities.ShoppingCartItem;
import com.example.backend.entities.User;
import com.example.backend.repositories.OrderItemRepository;
import com.example.backend.repositories.OrderRepository;
import com.example.backend.repositories.ShoppingCartItemRepository;
import com.example.backend.repositories.UserRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author wagne
 */
@Service
@Transactional
public class OrderService {

    private OrderRepository orderRepository;
    private OrderItemRepository orderItemRepository;
    private ShoppingCartItemRepository shoppingCartItemRepository;
    private UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, ShoppingCartItemRepository shoppingCartItemRepository, OrderItemRepository orderItemRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.shoppingCartItemRepository = shoppingCartItemRepository;
        this.userRepository = userRepository;
    }

    public List<Order> getUserOrders(String email) {
        return userRepository.findByEmail(email).getOrders();
    }

    public Order createOrder(String email) {
        User user = userRepository.findByEmail(email);
        Order order = new Order();
        order.setDate(LocalDateTime.now());
        order.setUser(user);
        orderRepository.save(order);

        List<OrderItem> orderItems = new ArrayList<OrderItem>();
        for (ShoppingCartItem item : userRepository.findByEmail(email).getShoppingCart().getShoppingCartItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(item.getProduct());
            orderItem.setQuantity(item.getQuantity());
            orderItems.add(orderItem);
            orderItemRepository.save(orderItem);
            shoppingCartItemRepository.delete(item);
        }
        order.setOrderItems(orderItems);
        orderRepository.save(order);
        return order;
    }
}
