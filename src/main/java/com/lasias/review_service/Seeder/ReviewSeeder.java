package com.lasias.review_service.Seeder;

import com.lasias.review_service.Entity.ReviewEntity;
import com.lasias.review_service.repositories.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ReviewSeeder implements CommandLineRunner {

    private final ReviewRepository reviewRepository;

    @Override
    public void run(String... args) throws Exception {
        if (reviewRepository.count() == 0) {
            reviewRepository.saveAll(reviewsToAdd);
            log.info("Added {} to Rooms table", reviewsToAdd.size());
        } else {
            log.info("Rooms already exists in database");
        }
    }

    List<ReviewEntity> reviewsToAdd = List.of(
            ReviewEntity.builder()
                    .comment("Second stay in the Lux Suite, still exceeded expectations.")
                    .rating(5)
                    .bookingId(106L)
                    .roomTypeId(1L)
                    .userId(3L)
                    .username("Jane Smith")
                    .build(),

            ReviewEntity.builder()
                    .comment("Decent standard room, nothing fancy but clean and quiet.")
                    .rating(4)
                    .bookingId(107L)
                    .roomTypeId(2L)
                    .userId(3L)
                    .username("Jane Smith")
                    .build(),

            ReviewEntity.builder()
                    .comment("Loved the double room's natural light. Would book again.")
                    .rating(5)
                    .bookingId(108L)
                    .roomTypeId(3L)
                    .userId(2L)
                    .username("John Doe")
                    .build()
    );

}