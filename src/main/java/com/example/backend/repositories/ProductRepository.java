/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.backend.repositories;

import com.example.backend.entities.Product;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author wagne
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Page<Product> findByDeleted(Pageable pageable, boolean deleted);
    Page<Product> findByNameContaining(String productName, Pageable pageable);
    List<Product> findByUserIdOrderByNameAsc(int id);
}
