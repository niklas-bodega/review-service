package com.lasias.review_service.dtos;

import jakarta.validation.constraints.*;
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
    private final String bookingNumber;
    @NotNull
    private final Long roomTypeId;

    @NotNull
    private final String roomTypeName;


    public CreateReviewRequestDTO(String comment, int rating, String bookingNumber, Long roomTypeId, String roomTypeName) {
        this.comment = comment;
        this.rating = rating;
        this.bookingNumber = bookingNumber;
        this.roomTypeId = roomTypeId;
        this.roomTypeName = roomTypeName;
    }
}
