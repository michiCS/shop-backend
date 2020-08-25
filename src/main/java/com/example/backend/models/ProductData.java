/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.backend.models;

/**
 *
 * @author wagne
 */
public class ProductData {
    private String name;
    private double price;
    private String description;
    private String base64;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base) {
        this.base64 = base;
    }
    
    
}
