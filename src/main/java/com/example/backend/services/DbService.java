/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.backend.services;

import com.example.backend.dtos.ShoppingCartItemDto;
import com.example.backend.entities.Order;
import com.example.backend.entities.OrderItem;
import com.example.backend.entities.Product;
import com.example.backend.entities.Review;
import com.example.backend.entities.ShoppingCart;
import com.example.backend.entities.ShoppingCartItem;
import com.example.backend.entities.User;
import com.example.backend.jwt.JwtTokenService2;
import com.example.backend.models.ReviewDto;
import com.example.backend.repositories.OrderItemRepository;
import com.example.backend.repositories.OrderRepository;
import com.example.backend.repositories.ProductRepository;
import com.example.backend.repositories.ReviewRepository;
import com.example.backend.repositories.ShoppingCartItemRepository;
import com.example.backend.repositories.ShoppingCartRepository;
import com.example.backend.repositories.UserRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

/**
 *
 * @author wagne
 */
@Component
public class DbService {

    @Autowired
    private ShoppingCartItemRepository shoppingCartItemRepository;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    public DbService() {

    }

    public boolean isUserExisting(String email) {
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .isPresent();
    }

    public User getUser(String email) {
        return userRepository.findAll()
                .stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst()
                .get();
    }

    public User createUser(String name, String email, String password) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        userRepository.save(user);

        ShoppingCart sc = new ShoppingCart();
        sc.setUser(user);
        shoppingCartRepository.save(sc);

        user.setShoppingCart(sc);
        userRepository.save(user);

        return user;
    }

    public ShoppingCart getUserShoppingCart(String email) {
        return shoppingCartRepository.findAll()
                .stream()
                .filter(sc -> sc.getUser().getEmail().equals(email))
                .findFirst()
                .get();
    }

    public List<ShoppingCartItemDto> getUserShoppingCartItems(String email) {
        return shoppingCartRepository.findAll()
                .stream()
                .filter(cart -> cart.getUser().getEmail().equals(email))
                .flatMap(cart -> cart.getShoppingCartItems().stream())
                .map(item -> new ShoppingCartItemDto(item.getId(), item.getProduct(), item.getQuantity()))
                .collect(Collectors.toList());
    }

    public ShoppingCartItem addToUserShoppingCart(ShoppingCart cart, int productId, int quantity) {
        Product prod = productRepository.findById(productId).orElse(null);

        Optional<ShoppingCartItem> i = cart.getShoppingCartItems()
                .stream()
                .filter(item -> item.getProduct().getId() == productId)
                .findFirst();

        ShoppingCartItem item;
        if (!i.isPresent()) {
            item = new ShoppingCartItem();
            item.setProduct(prod);
            item.setShoppingCart(cart);
            item.setQuantity(quantity);
            cart.getShoppingCartItems().add(item);
            shoppingCartItemRepository.save(item);
            shoppingCartRepository.save(cart);
        } else {
            item = i.get();
            item.setQuantity(item.getQuantity() + quantity);
            shoppingCartItemRepository.save(item);
        }
        return item;
    }

    public int deleteItemFromCart(int itemId) {
        ShoppingCartItem item = shoppingCartItemRepository.findById(itemId).orElse(null);
        shoppingCartItemRepository.delete(item);
        return itemId;
    }

    public ShoppingCartItem updateQuantity(int itemId, int quantity) {
        ShoppingCartItem item = shoppingCartItemRepository.findById(itemId).orElse(null);
        item.setQuantity(quantity);
        shoppingCartItemRepository.save(item);
        return item;
    }

    

    

    public List<Order> getUserOrders(String email) {
        return getUser(email).getOrders();
    }

    public Order createOrder(String email) {
        User user = getUser(email);
        Order order = new Order();
        order.setDate(LocalDateTime.now());
        order.setUser(user);
        orderRepository.save(order);

        List<OrderItem> orderItems = new ArrayList<OrderItem>();
        for (ShoppingCartItem item : getUserShoppingCart(email).getShoppingCartItems()) {
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
