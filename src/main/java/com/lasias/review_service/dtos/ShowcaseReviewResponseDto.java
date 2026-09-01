package com.lasias.review_service.dtos;


import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
@Builder
@Getter
public class ShowcaseReviewResponseDto {

    private final Long reviewId;
    private final String comment;
    private final int rating;
    private final String username;
    private final LocalDateTime createdAt;
    private final String roomTypeName;

    public ShowcaseReviewResponseDto(Long reviewId, String comment, int rating, String username, LocalDateTime createdAt, String roomTypeName) {
        this.reviewId = reviewId;
        this.comment = comment;
        this.rating = rating;
        this.username = username;
        this.createdAt = createdAt;
        this.roomTypeName = roomTypeName;
    }
}
