package com.lasias.review_service.services;

import com.lasias.review_service.DtoMapper.Mapper;
import com.lasias.review_service.Entity.ReviewEntity;
import com.lasias.review_service.config.ReviewPrincipal;
import com.lasias.review_service.dtos.CreateReviewRequestDTO;
import com.lasias.review_service.dtos.ShowReviewResponseDTO;
import com.lasias.review_service.exceptions.AccessToReviewDeniedException;
import com.lasias.review_service.exceptions.ReviewNotFoundException;
import com.lasias.review_service.repositories.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


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

    public Map<Long, Double> getAllReviewsRatingByRoomType(){
        return reviewRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(
                        ReviewEntity::getRoomTypeId,
                        Collectors.averagingDouble(ReviewEntity::getRating)
                ));
    }

    public void deleteReview(Long reviewId, ReviewPrincipal principal){

        ReviewEntity reviewToDelete = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException(reviewId));
        if(!reviewToDelete.getUserId().equals(principal.getUserId())){
            throw new AccessToReviewDeniedException();
        }
        reviewRepository.deleteById(reviewId);
    }

    public ShowReviewResponseDTO findReviewById (Long reviewId){
        ReviewEntity foundReviewEntity = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException(reviewId));
        return mapper.entityToShowReviewResponseDTO(foundReviewEntity);

    }

}
