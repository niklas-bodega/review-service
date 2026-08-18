package com.lasias.review_service.DtoMapper;

import com.lasias.review_service.Entity.ReviewEntity;
import com.lasias.review_service.dtos.CreateReviewRequestDTO;
import com.lasias.review_service.dtos.ShowReviewResponseDTO;

public class Mapper {

    public CreateReviewRequestDTO EntityToCreateReviewRequestDTO (ReviewEntity entity){
        return CreateReviewRequestDTO.builder()
                .rating(entity.getRating())
                .comment(entity.getComment())
                .roomTypeId(entity.getRoomTypeId())
                .bookingId(entity.getBookingId())
                .build();
    }

    public ReviewEntity CreateReviewRequestDTOtoEntity(CreateReviewRequestDTO dto){
       return ReviewEntity.builder()
                .rating(dto.getRating())
                .comment(dto.getComment())
                .roomTypeId(dto.getRoomTypeId())
                .bookingId(dto.getBookingId())
                .build();
    }

    public ShowReviewResponseDTO EntityToShowReviewResponseDTO(ReviewEntity entity){
        return ShowReviewResponseDTO.builder()
                .comment(entity.getComment())
                .rating(entity.getRating())
                .username(entity.getUsername())
                .createdAt(entity.getCreateDate())
                .reviewId(entity.getId())
                .build();
    }
}
