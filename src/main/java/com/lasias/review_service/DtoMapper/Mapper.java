package com.lasias.review_service.DtoMapper;

import com.lasias.review_service.Entity.ReviewEntity;
import com.lasias.review_service.dtos.CreateReviewRequestDTO;
import com.lasias.review_service.dtos.ShowReviewResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class Mapper {


    public ReviewEntity createReviewRequestDTOtoEntity(CreateReviewRequestDTO dto, Long userId, String username) {
        return ReviewEntity.builder()
                .rating(dto.getRating())
                .comment(dto.getComment())
                .roomTypeId(dto.getRoomTypeId())
                .bookingNumber(dto.getBookingNumber())
                .userId(userId)
                .username(username)
                .build();
    }

    public ShowReviewResponseDTO entityToShowReviewResponseDTO(ReviewEntity entity) {
        return ShowReviewResponseDTO.builder()
                .comment(entity.getComment())
                .rating(entity.getRating())
                .username(entity.getUsername())
                .createdAt(entity.getCreateDate())
                .reviewId(entity.getId())
                .build();
    }
}
