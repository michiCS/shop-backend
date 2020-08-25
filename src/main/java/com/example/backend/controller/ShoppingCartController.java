/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.backend.controller;

import com.example.backend.dtos.ShoppingCartItemDto;
import com.example.backend.entities.Product;
import com.example.backend.entities.ShoppingCart;
import com.example.backend.entities.ShoppingCartItem;
import com.example.backend.jwt.JwtTokenService2;
import com.example.backend.repositories.ProductRepository;
import com.example.backend.repositories.ShoppingCartItemRepository;
import com.example.backend.repositories.ShoppingCartRepository;
import com.example.backend.repositories.UserRepository;
import com.example.backend.services.DbService;
import com.example.backend.services.ShoppingCartService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author wagne
 */
@RestController
@CrossOrigin
public class ShoppingCartController {

    private JwtTokenService2 jwtService;
    private ShoppingCartService shoppingCartService;

    public ShoppingCartController(JwtTokenService2 jwtService, ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
        this.jwtService = jwtService;
    }

    @GetMapping("/shoppingcart")
    public ResponseEntity<List<ShoppingCartItemDto>> shoppingcartItems(HttpServletRequest req) {
        String email = jwtService.getEmailFromRequest(req);
        return new ResponseEntity<>(shoppingCartService.getUserShoppingCartItems(email), HttpStatus.OK);
    }

    @PostMapping("/add-to-cart")
    public ResponseEntity<ShoppingCartItem> addToCart(HttpServletRequest req, @RequestParam(name = "id") int id, @RequestParam(name = "quantity") int quantity) {
        String username = jwtService.getEmailFromRequest(req);
        ShoppingCart cart = shoppingCartService.getUserShoppingCart(username);
        ShoppingCartItem item = shoppingCartService.addToUserShoppingCart(cart, id, quantity);
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete-from-cart")
    public ResponseEntity<Integer> deleteFromCart(@RequestParam(name = "id") int id) {
        int deletedId = shoppingCartService.deleteItemFromCart(id);
        return new ResponseEntity<>(deletedId, HttpStatus.OK);
    }

    @PutMapping("/update-item-quantity")
    public Object updateCartItemQuantity(@RequestParam(name = "id") int id, @RequestParam(name = "quantity") int quantity) {
        ShoppingCartItem item = shoppingCartService.updateQuantity(id, quantity);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }
}
