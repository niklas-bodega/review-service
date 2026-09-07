package com.lasias.review_service.services;

import com.lasias.review_service.Entity.ReviewEntity;
import com.lasias.review_service.config.ReviewPrincipal;
import com.lasias.review_service.dtos.CreateReviewRequestDTO;
import com.lasias.review_service.dtos.ShowReviewResponseDTO;
import com.lasias.review_service.repositories.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.mysql.MySQLContainer;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@Testcontainers
class ReviewServiceTest {

    @Container
    static MySQLContainer mysql = new MySQLContainer("mysql:8.0")
            .withDatabaseName("review_db_test")
            .withUsername("tester")
            .withPassword("tester");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewRepository reviewRepository;

    @MockitoBean
    private UserServiceClient userServiceClient;

    @BeforeEach
    void setUp() {
        reviewRepository.deleteAll();
    }

    @Test
    void createNewReviewEntry_savesReviewToRealTestDatabase(){
        when(userServiceClient.getUsername("fake-jwt")).thenReturn("Ivan");
        CreateReviewRequestDTO dto = CreateReviewRequestDTO.builder()
                .comment("Good stay.")
                .rating(5)
                .bookingNumber("testtest123")
                .roomTypeId(1L)
                .roomTypeName("Ivans Penthouse")
                .build();

        ReviewPrincipal principal = ReviewPrincipal.builder()
                .userId(1L)
                .jwt("fake-jwt")
                .build();

        reviewService.createNewReviewEntry(dto, principal);

        assertEquals(1, reviewRepository.count());

        ReviewEntity addedReview = reviewRepository.findAll().getFirst();
        assertNotNull(addedReview);
        assertEquals(addedReview.getBookingNumber(), dto.getBookingNumber());
    }

    @Test
    void findReviewByIdTest_returnsReviewEntity(){
        ReviewEntity savedReview = reviewRepository.save(ReviewEntity.builder()
                .roomTypeId(1L)
                .rating(5)
                .comment("Wonderful stay")
                .bookingNumber("booking-abc")
                .userId(1L)
                .username("Ivan")
                .roomTypeName("Penthouse")
                .build());

        ShowReviewResponseDTO reviewToFind = reviewService.findReviewById(savedReview.getId());

        assertNotNull(reviewToFind);
        assertEquals(savedReview.getRoomTypeName(), reviewToFind.getRoomTypeName());
    }

}