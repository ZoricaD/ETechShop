package com.example.etechshop.controller;

import com.example.etechshop.entity.Review;
import com.example.etechshop.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "http://localhost:3000")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/add")
    public ResponseEntity<Review> addReview(
            @RequestParam Long userId,
            @RequestParam Long productId,
            @RequestParam int rating,
            @RequestParam String comment) {

        Review createdReview = reviewService.addReview(userId, productId, rating, comment);
        return ResponseEntity.ok(createdReview);
    }

    @PutMapping("/update/{reviewId}/{rating}")
    public ResponseEntity<Review> updateReview(
            @PathVariable Long reviewId,
            @PathVariable int rating,
            @RequestParam(required = false) String comment) {

        Review updatedReview = reviewService.updateReview(reviewId, rating, comment);
        return ResponseEntity.ok(updatedReview);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long reviewId) {
        return ResponseEntity.ok(reviewService.getReviewById(reviewId));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Review>> getReviewsByProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(reviewService.getReviewsByProduct(productId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Review>> getReviewsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(reviewService.getReviewsByUser(userId));
    }

    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }
}
