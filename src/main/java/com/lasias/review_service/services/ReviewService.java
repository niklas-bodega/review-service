package com.lasias.review_service.services;

import com.lasias.review_service.DtoMapper.Mapper;
import com.lasias.review_service.Entity.ReviewEntity;
import com.lasias.review_service.config.ReviewPrincipal;
import com.lasias.review_service.dtos.CreateReviewRequestDTO;
import com.lasias.review_service.dtos.ShowReviewResponseDTO;
import com.lasias.review_service.repositories.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ReviewService {

    private final Mapper mapper;
    private final ReviewRepository reviewRepository;
    private final UserServiceClient userServiceClient;

    public ShowReviewResponseDTO createNewReviewEntry(CreateReviewRequestDTO reviewToCreate, ReviewPrincipal principal){
        String username = userServiceClient.getUsername(principal.getJwt());
        ReviewEntity createdReview = reviewRepository.save(mapper.createReviewRequestDTOtoEntity(reviewToCreate, principal.getUserId() , username));
        return mapper.entityToShowReviewResponseDTO(createdReview);

    }

    public String deleteReview(Long reviewId, ReviewPrincipal principal){

        ReviewEntity reviewToDelete = reviewRepository.findById(reviewId).orElse(null);
        if(reviewToDelete != null && reviewToDelete.getUserId().equals(principal.getUserId())){
            reviewRepository.deleteById(reviewId);
            return "Review deleted succesfully";
        }
        else{
            return "Couldn't delete review";
        }
    }

}
