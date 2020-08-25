/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.backend;

import com.example.backend.entities.User;
import com.example.backend.jwt.JwtTokenService;
import com.example.backend.jwt.JWTTokenResponse;
import com.example.backend.repositories.UserRepository;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author wagne
 */
@Service
public class AuthenticationService {

    @Autowired
    private JwtTokenService jwtTokenService;
    
    @Autowired
    private UserRepository userRepository;
    //private PasswordEncoder passwordEncoder;

    public Object generateJWTToken(String email, String password) {
        User user = userRepository.findAll()
                .stream()
                .filter(u -> u.getEmail().equals(email) && u.getPassword().equals(password))
                .collect(Collectors.toList())
                .get(0);
         System.out.println("!!!!!!!!!!!!!!!!!!!!!!!ASDFASDFDAFADFAFASDFADFFADFDFDAFD");
        if(user != null) {
            return new JWTTokenResponse(jwtTokenService.generateToken(email), user.getName());
        } else {
            return new EntityNotFoundException("Account not found");
        }

    }
}
