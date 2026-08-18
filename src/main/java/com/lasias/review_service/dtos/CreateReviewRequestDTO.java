package com.lasias.review_service.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateReviewRequestDTO {

    @Size(max = 1000)
    private final String comment;

    @Min(1)
    @Max(5)
    private final int rating;

    @NotNull
    private final Long bookingId;
    @NotNull
    private final Long roomTypeId;

    public CreateReviewRequestDTO(String comment, int rating, Long bookingId, Long roomTypeId) {
        this.comment = comment;
        this.rating = rating;
        this.bookingId = bookingId;
        this.roomTypeId = roomTypeId;
    }
}
