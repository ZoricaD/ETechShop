package com.example.etechshop.service;

import com.example.etechshop.entity.Review;
import java.util.List;

public interface ReviewService {
    Review addReview(Long userId, Long productId, int rating, String comment);
    Review updateReview(Long reviewId, int rating, String comment);
    Review getReviewById(Long reviewId);
    List<Review> getReviewsByProduct(Long productId);
    List<Review> getReviewsByUser(Long userId);
    void deleteReview(Long reviewId);
}
