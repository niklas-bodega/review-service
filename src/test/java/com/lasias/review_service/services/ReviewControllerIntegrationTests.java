package com.lasias.review_service.services;

import com.lasias.review_service.Entity.ReviewEntity;
import com.lasias.review_service.repositories.ReviewRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.mysql.MySQLContainer;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Transactional
public class ReviewControllerIntegrationTests {



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
    private MockMvc mockMvc;

    @Autowired
    private ReviewRepository reviewRepository;


    @Test
    void shouldGetReviewsForShowcase() throws Exception{
        reviewRepository.deleteAll();

        reviewRepository.save(ReviewEntity.builder()
                .roomTypeId(1L)
                .rating(5)
                .comment("Fantastic stay")
                .bookingNumber("booking-1")
                .userId(1L)
                .username("Ivan")
                .roomTypeName("Suite")
                .build());

        reviewRepository.save(ReviewEntity.builder()
                .roomTypeId(2L)
                .rating(2)
                .comment("Ok stay")
                .bookingNumber("booking-2")
                .userId(2L)
                .username("Pelle")
                .roomTypeName("Single")
                .build());

        mockMvc.perform(get("/api/review/showcase"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[?(@.username == 'Pelle')]").exists())
                .andExpect(jsonPath("$[?(@.roomTypeName == 'Suite')]").exists());
    }

}
