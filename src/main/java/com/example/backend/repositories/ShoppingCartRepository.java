/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.backend.repositories;

import com.example.backend.entities.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author wagne
 */
@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer> {
    
}

