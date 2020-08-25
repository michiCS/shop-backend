/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.backend.controller;

import com.example.backend.entities.Product;
import com.example.backend.entities.Review;
import com.example.backend.entities.User;
import com.example.backend.jwt.JwtTokenService2;
import com.example.backend.models.ReviewData;
import com.example.backend.models.ReviewDto;
import com.example.backend.services.DbService;
import com.example.backend.services.ReviewService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author wagne
 */
@RestController
@CrossOrigin
public class ReviewController {

    private JwtTokenService2 jwtService;
    private ReviewService reviewService;

    public ReviewController(JwtTokenService2 jwtService, ReviewService reviewService) {
        this.jwtService = jwtService;
        this.reviewService = reviewService;
    }
    
    @GetMapping("/reviews")
    public ResponseEntity<List<ReviewDto>> getReviewsForProduct(@RequestParam(name = "id") int id) {
        return new ResponseEntity<>(reviewService.getProductReviews(id), HttpStatus.OK);
    }

    @GetMapping("/review-info")
    public ResponseEntity<Boolean> getReviewInfo(HttpServletRequest req, @RequestParam(name = "id") int id) {
        String email = jwtService.getEmailFromRequest(req);
        boolean allowed = reviewService.canReview(id, email);
        return new ResponseEntity<>(allowed, HttpStatus.OK);

    }

    @PostMapping("/reviews")
    public ResponseEntity<Review> makeReview(HttpServletRequest req, @RequestBody ReviewData reviewData) {
        String email = jwtService.getEmailFromRequest(req);
        Review review = reviewService.createReview(reviewData.getProductId(), reviewData.getRating(), reviewData.getText(), email);
        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }

    @DeleteMapping("/reviews")
    public ResponseEntity<Integer> deleteReview(@RequestParam(name = "id") int id) {
        int deletedId = reviewService.deleteReview(id);
        return new ResponseEntity<>(deletedId, HttpStatus.OK);
    }
}
