package com.lasias.review_service.controllers;

import com.lasias.review_service.dtos.ShowReviewResponseDTO;
import com.lasias.review_service.services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
public class GetControllers {
    private final ReviewService reviewService;

    //Get all users reviews ( add AuthenticationPrincipal later )
  /* @GetMapping("/my")

   public ResponseEntity<List<ShowReviewResponseDTO>> getMyReviews(){

    }
   */

    /* @GetMapping("{reviewId}")
    public ResponseEntity<ShowReviewResponseDTO> findReviewById(@PathVariable Long reviewId){
        reviewService.findReviewById(reviewId);
    }
*/
}
