/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.backend.dtos;

import com.example.backend.entities.Product;

/**
 *
 * @author wagne
 */
public class ShoppingCartItemDto {

    private int id;
    private String name;
    private double price;
    private int quantity;
    private String imagePath;

    public ShoppingCartItemDto(int id, Product p, int quantity){ 
        this.id = id;
        this.name = p.getName();
        this.price = p.getPrice();
        this.imagePath = p.getImagePath();
        this.quantity = quantity;
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImagepath() {
        return imagePath;
    }

    public void setImagepath(String imagepath) {
        this.imagePath = imagepath;
    }
    
    

    @Override
    public String toString() {
        return "ShoppingCartItemDto{" + "id=" + id + ", name=" + name + ", price=" + price + ", Quantity=" + quantity + '}';
    }
    
    

}
