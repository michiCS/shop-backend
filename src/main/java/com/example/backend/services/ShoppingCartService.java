/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.backend.services;

import com.example.backend.dtos.ShoppingCartItemDto;
import com.example.backend.entities.Product;
import com.example.backend.entities.ShoppingCart;
import com.example.backend.entities.ShoppingCartItem;
import com.example.backend.repositories.ProductRepository;
import com.example.backend.repositories.ShoppingCartItemRepository;
import com.example.backend.repositories.ShoppingCartRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author wagne
 */


@Service
@Transactional
public class ShoppingCartService {

    private ShoppingCartItemRepository shoppingCartItemRepository;
    private ShoppingCartRepository shoppingCartRepository;
    private ProductRepository productRepository;

    public ShoppingCartService(ShoppingCartItemRepository shoppingCartItemRepository, ShoppingCartRepository shoppingCartRepository, ProductRepository productRepository) {
        this.shoppingCartItemRepository = shoppingCartItemRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.productRepository = productRepository;
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
}
