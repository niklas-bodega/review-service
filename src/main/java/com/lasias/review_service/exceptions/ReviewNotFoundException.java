package com.lasias.review_service.exceptions;


public class ReviewNotFoundException extends RuntimeException {
    public ReviewNotFoundException(Long reviewId) {
        super("Review not found: " + reviewId);
    }
}
