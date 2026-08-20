package com.lasias.review_service.controllers;


import com.lasias.review_service.config.ReviewPrincipal;
import com.lasias.review_service.dtos.CreateReviewRequestDTO;
import com.lasias.review_service.dtos.ShowReviewResponseDTO;
import com.lasias.review_service.services.ReviewService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")

public class PostControllers {

    private ReviewService reviewService;

    @PostMapping()
    public ResponseEntity<ShowReviewResponseDTO> addNewReviewToDatabase(@Valid @RequestBody CreateReviewRequestDTO reviewToAddToDatabase, @AuthenticationPrincipal ReviewPrincipal principal){

        return ResponseEntity.ok(reviewService.createNewReviewEntry(reviewToAddToDatabase, principal));

    }

}
