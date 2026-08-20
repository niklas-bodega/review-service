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
    public ShowReviewResponseDTO createNewReviewEntry(CreateReviewRequestDTO reviewToCreate, ReviewPrincipal principal){



        ReviewEntity createdReview = reviewRepository.save(mapper.createReviewRequestDTOtoEntity(reviewToCreate, principal.getUserId() , "test"));

        return mapper.entityToShowReviewResponseDTO(createdReview);

    }

}
