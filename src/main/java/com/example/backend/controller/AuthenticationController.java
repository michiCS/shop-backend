/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.backend.controller;

import com.example.backend.models.AuthenticationRequest;
import com.example.backend.AuthenticationService;
import com.example.backend.models.RegisterCredentials;
import com.example.backend.services.DbService;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author wagne
 */
@RestController
@RequestMapping
@CrossOrigin
public class AuthenticationController {

    private AuthenticationService authenticationService;
    private DbService dbInteractionService;

    public AuthenticationController(AuthenticationService authenticationService, DbService dbInteractionService) {
        this.authenticationService = authenticationService;
        this.dbInteractionService = dbInteractionService;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationRequest request) {
        return new ResponseEntity<>(authenticationService.generateJWTToken(request.getEmail(), request.getPassword()), HttpStatus.OK);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity handleEntityNotFoundException(EntityNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterCredentials credentials) {
        if (dbInteractionService.isUserExisting(credentials.getEmail())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            dbInteractionService.createUser(credentials.getUsername(), credentials.getEmail(), credentials.getPassword());
            return new ResponseEntity<>(HttpStatus.CREATED);
        }

    }
}
