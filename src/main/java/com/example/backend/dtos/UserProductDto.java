/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.backend.dtos;

/**
 *
 * @author wagne
 */
public class UserProductDto {
    private int id;
    private String name;
    private double price;
    private String imagePath;
    private boolean deleted;

    public UserProductDto(int id, String name, double price, String imagePath, boolean deleted) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imagePath = imagePath;
        this.deleted = deleted;
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
