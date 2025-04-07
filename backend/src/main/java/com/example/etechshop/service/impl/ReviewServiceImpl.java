package com.example.etechshop.service.impl;

import com.example.etechshop.entity.Product;
import com.example.etechshop.entity.Review;
import com.example.etechshop.entity.TechUser;
import com.example.etechshop.repository.ProductRepository;
import com.example.etechshop.repository.ReviewRepository;
import com.example.etechshop.repository.TechUserRepository;
import com.example.etechshop.service.ReviewService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final TechUserRepository techUserRepository;
    private final ProductRepository productRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, TechUserRepository techUserRepository, ProductRepository productRepository) {
        this.reviewRepository = reviewRepository;
        this.techUserRepository = techUserRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public Review addReview(Long userId, Long productId, int rating, String comment) {
        TechUser user = techUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Review review = Review.builder()
                .techUser(user)
                .product(product)
                .rating(rating)
                .comment(comment)
                .build();

        return reviewRepository.save(review);
    }

    @Override
    @Transactional
    public Review updateReview(Long reviewId, int rating, String comment) {
        Review existingReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        existingReview.setRating(rating);
        existingReview.setComment(comment);

        return reviewRepository.save(existingReview);
    }

    @Override
    public Review getReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));
    }

    @Override
    public List<Review> getReviewsByProduct(Long productId) {
        return reviewRepository.findAll()
                .stream()
                .filter(review -> review.getProduct().getId().equals(productId))
                .toList();
    }

    @Override
    public List<Review> getReviewsByUser(Long userId) {
        return reviewRepository.findAll()
                .stream()
                .filter(review -> review.getTechUser().getId().equals(userId))
                .toList();
    }

    @Override
    @Transactional
    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }
}
