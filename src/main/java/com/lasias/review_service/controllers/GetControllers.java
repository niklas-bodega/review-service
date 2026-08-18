package com.lasias.review_service.controllers;

import com.lasias.review_service.dtos.ShowReviewResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
public class GetControllers {

    //Get all users reviews ( add AuthenticationPrincipal later )
  /* @GetMapping("/my")

   public ResponseEntity<List<ShowReviewResponseDTO>> getMyReviews(){

    }
   */

}
