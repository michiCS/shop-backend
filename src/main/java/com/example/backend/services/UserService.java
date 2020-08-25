/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.backend.services;

import com.example.backend.entities.ShoppingCart;
import com.example.backend.entities.User;
import com.example.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author wagne
 */
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public int getUserId(String email) {
        return 0;
    }
}
