/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.backend.repositories;

import com.example.backend.entities.User;
import java.util.stream.Collectors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author wagne
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
}

