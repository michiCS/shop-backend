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
public class OrderItemDto {
    private int quantity;
    private int productId;
    private String productName;
    private double price;
    private String imagePath;
    private boolean deleted;

    public OrderItemDto(int quantity, int productId, String productName, double price, String imagePath, boolean deleted) {
        this.quantity = quantity;
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.imagePath = imagePath;
        this.deleted = deleted;
    }
    

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
    
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    
    
    
    
}
