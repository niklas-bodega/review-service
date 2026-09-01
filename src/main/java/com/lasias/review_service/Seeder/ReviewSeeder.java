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
            log.info("Added {} to Reviews table", reviewsToAdd.size());
        } else {
            log.info("Reviews already exists in database");
        }
    }

    List<ReviewEntity> reviewsToAdd = List.of(
            ReviewEntity.builder()
                    .comment("Second stay in the Lux Suite, still exceeded expectations.")
                    .rating(5)
                    .bookingNumber("d5937943-d4ef-46d7-a7df-5b86a26f4e77")
                    .roomTypeId(1L)
                    .userId(3L)
                    .username("Jane Smith")
                    .build(),

            ReviewEntity.builder()
                    .comment("Decent standard room, nothing fancy but clean and quiet.")
                    .rating(4)
                    .bookingNumber("a62b0b51-b94e-4296-8068-0437370bfde4")
                    .roomTypeId(2L)
                    .userId(3L)
                    .username("Jane Smith")
                    .build(),

            ReviewEntity.builder()
                    .comment("Loved the double room's natural light. Would book again.")
                    .rating(5)
                    .bookingNumber("9f982396-d8e8-4a20-844d-740140ef7f58")
                    .roomTypeId(3L)
                    .userId(2L)
                    .username("John Doe")
                    .build()
    );

}