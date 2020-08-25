/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.backend.services;

import com.example.backend.entities.Product;
import com.example.backend.entities.User;
import com.example.backend.repositories.ProductRepository;
import com.example.backend.repositories.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author wagne
 */
@Service
@Transactional
public class ProductService {

    private ProductRepository productRepository;
    private UserRepository userRepository;
    private ImageKitService imageKitService;

    public ProductService(ProductRepository productRepository, UserRepository userRepository, ImageKitService imageKitService) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.imageKitService = imageKitService;
    }

    public List<Product> getProducts(int pageNr, String name, String sortParam) {
        Pageable pageable; 
        if(sortParam == null) sortParam = "name";
        if(sortParam.split(";").length > 1) {
            String s = sortParam.split(";")[0];
            pageable = (Pageable) PageRequest.of(pageNr, 15, Sort.by(s).descending());
        } else {
            pageable = (Pageable) PageRequest.of(pageNr, 15, Sort.by(sortParam));
        }
        Page<Product> page;
        if (name == null || name.length() == 0) {
            page = productRepository.findByDeleted(pageable, false);
        } else {
            page = productRepository.findByNameContaining(name, pageable);
        }
        return page.getContent();
    }

    public List<Product> getUserProducts(String email) {
        int userId = userRepository.findByEmail(email).getId();
        return productRepository.findByUserIdOrderByNameAsc(userId);
    }

    public Product getProduct(int productId) {
        return productRepository.findById(productId).orElse(null);
    }

    public Product createProduct(String name, double price, String description, String base64, String email) {
        User user = userRepository.findByEmail(email);
        String imagePath = imageKitService.uploadImage(base64, user.getName(), email);

        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setDescription(description);
        product.setCreated(LocalDateTime.now());
        product.setImagePath(imagePath);
        product.setUser(user);
        productRepository.save(product);
        return product;
    }

    public Product updateProduct(int productId, String productName, double price) {
        Product product = productRepository.findById(productId).orElse(null);
        product.setName(productName);
        product.setPrice(price);
        productRepository.save(product);
        return product;
    }

    public Product deleteProduct(int productId) {
        Product product = productRepository.findById(productId).orElse(null);
        product.setDeleted(true);
        productRepository.save(product);
        return product;
    }

}
