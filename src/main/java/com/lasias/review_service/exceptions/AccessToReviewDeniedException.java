package com.lasias.review_service.exceptions;

public class AccessToReviewDeniedException extends RuntimeException {
        public AccessToReviewDeniedException() {
            super("You don't have permission to modify this review");
        }
}
