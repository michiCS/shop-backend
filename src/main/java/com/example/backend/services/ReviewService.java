/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.backend.services;

import com.example.backend.entities.Product;
import com.example.backend.entities.Review;
import com.example.backend.entities.User;
import com.example.backend.models.ReviewDto;
import com.example.backend.repositories.ProductRepository;
import com.example.backend.repositories.ReviewRepository;
import com.example.backend.repositories.UserRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author wagne
 */
@Service
@Transactional
public class ReviewService {

    private ReviewRepository reviewRepository;
    private ProductRepository productRepository;
    private UserRepository userRepository;

    public ReviewService(ReviewRepository reviewRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public List<ReviewDto> getProductReviews(int productId) {
        return productRepository
                .findById(productId)
                .orElse(null)
                .getReviews()
                .stream()
                .map(review -> new ReviewDto(review.getId(), review.getRating(), review.getText(), review.getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)), review.getUser().getName()))
                .collect(Collectors.toList());
    }

    public boolean canReview(int productId, String email) {
        Product product = productRepository.findById(productId).orElse(null);
        User user = userRepository.findByEmail(email);
        boolean orderedBefore = user.getOrders()
                .stream()
                .anyMatch(order -> order.getOrderItems().stream().anyMatch(item -> item.getProduct().getId() == product.getId()));
        boolean reviewedBefore = user.getReviews()
                .stream()
                .anyMatch(review -> review.getProduct().getId() == product.getId());
        return (orderedBefore && !reviewedBefore);
    }

    public Review createReview(int productId, int rating, String text, String email) {
        Product product = productRepository.findById(productId).orElse(null);
        User user = userRepository.findByEmail(email);
        Review review = new Review();
        review.setProduct(product);
        review.setRating(rating);
        review.setText(text);
        review.setUser(user);
        review.setDate(LocalDateTime.now());
        reviewRepository.save(review);
        product.getReviews().add(review);
        productRepository.save(product);
        user.getReviews().add(review);
        userRepository.save(user);
        return review;
    }

    public int deleteReview(int reviewId) {
        Review review = reviewRepository.findById(reviewId).orElse(null);
        reviewRepository.delete(review);
        return reviewId;
    }
}
