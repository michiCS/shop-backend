/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.backend.dtos;

import com.example.backend.entities.Product;
import java.io.IOException;

/**
 *
 * @author wagne
 */
public class ProductDto {
    private Product product;
    //private String encodedImage;
    
    public ProductDto(Product product){
        this.setProduct(product);
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
        this.setEncodedImage(product.getImagePath());
    }

    public String getEncodedImage() {
        //return encodedImage;
        return "";
    }

    public void setEncodedImage(String encodedImage) {
        //this.encodedImage = new ImageEncoder().encodeImage(encodedImage);
    }
    
    
}
