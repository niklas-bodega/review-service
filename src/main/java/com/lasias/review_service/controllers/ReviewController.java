package com.lasias.review_service.controllers;

import com.lasias.review_service.config.ReviewPrincipal;
import com.lasias.review_service.dtos.CreateReviewRequestDTO;
import com.lasias.review_service.dtos.ShowReviewResponseDTO;
import com.lasias.review_service.services.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("{reviewId}")
    public ResponseEntity<ShowReviewResponseDTO> findReviewById(@PathVariable Long reviewId){
        ShowReviewResponseDTO foundReview = reviewService.findReviewById(reviewId);
        return ResponseEntity.ok(foundReview);
    }

    @PostMapping()
    public ResponseEntity<ShowReviewResponseDTO> addNewReviewToDatabase(
            @Valid @RequestBody CreateReviewRequestDTO reviewToAddToDatabase,
            @AuthenticationPrincipal ReviewPrincipal principal){
        return ResponseEntity.ok(reviewService.createNewReviewEntry(reviewToAddToDatabase, principal));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId, @AuthenticationPrincipal ReviewPrincipal principal){
        reviewService.deleteReview(reviewId, principal);

        return ResponseEntity.noContent().build();
    }

}
