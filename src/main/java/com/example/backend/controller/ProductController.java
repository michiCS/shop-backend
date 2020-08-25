/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.backend.controller;


import com.example.backend.entities.Product;
import com.example.backend.jwt.JwtTokenService2;
import com.example.backend.models.ProductData;
import com.example.backend.models.ProductUpdateData;
import com.example.backend.services.ProductService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author wagne
 */
@RestController
@CrossOrigin
public class ProductController {

    private JwtTokenService2 jwtService;
    private ProductService productService;
    

    public ProductController(JwtTokenService2 jwtService, ProductService productService) {
        this.jwtService = jwtService;
        this.productService = productService;
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(@RequestParam(name = "page") int pageNr, @RequestParam(name = "name", required = false) String name, @RequestParam(name = "sort", required = false) String sort) {
        return new ResponseEntity<>(productService.getProducts(pageNr, name, sort), HttpStatus.OK);
    }

    @GetMapping("/user-products")
    public ResponseEntity<List<Product>> getMyProducts(HttpServletRequest req) {
        String email = jwtService.getEmailFromRequest(req);
        List <Product> products = productService.getUserProducts(email);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/product-info")
    public ResponseEntity<Product> getProductWithId(@RequestParam(name = "id") int id) {
        return new ResponseEntity<>(productService.getProduct(id), HttpStatus.OK);
    }

    @PostMapping("/product")
    public ResponseEntity<Product> createProduct(HttpServletRequest req, @RequestBody ProductData data) {

        String email = jwtService.getEmailFromRequest(req);
        Product product = productService.createProduct(data.getName(), data.getPrice(), data.getDescription(), data.getBase64(), email);

        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @PutMapping("/product")
    public ResponseEntity<Product> updateProduct(HttpServletRequest req, @RequestBody ProductUpdateData data) {
        Product product = productService.updateProduct(data.getId(), data.getName(), data.getPrice());
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @DeleteMapping("/product")
    public ResponseEntity<Product> deleteProduct(HttpServletRequest req, @RequestParam(name = "id") int id) {
        Product product = productService.deleteProduct(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

}
