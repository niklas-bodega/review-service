package com.lasias.review_service.services;

import com.lasias.review_service.Entity.ReviewEntity;
import com.lasias.review_service.repositories.ReviewRepository;
import lombok.Builder;
import org.springframework.stereotype.Service;


@Service
public class ReviewService {

    private ReviewRepository reviewRepository;

    public void createNewReviewEntry(String comment, int rating, long bookingId, long roomTypeId){


        //UserID and username is missing add with JWT filter setup.
        ReviewEntity reviewToAddToDatabase = ReviewEntity.builder()
                .bookingId(bookingId)
                .comment(comment)
                .rating(rating)
                .roomTypeId(roomTypeId)
                .build();

        reviewRepository.save(reviewToAddToDatabase);

    }

}
