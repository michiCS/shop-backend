/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.backend.repositories;


import com.example.backend.entities.ShoppingCartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author wagne
 */
@Repository
public interface ShoppingCartItemRepository extends JpaRepository<ShoppingCartItem, Integer> {
    @Modifying
    @Transactional
    @Query("UPDATE ShoppingCartItem item SET item.quantity = ?1 WHERE item.id = ?2")
    int updateQuantity(Integer quantity, Integer id);
}
