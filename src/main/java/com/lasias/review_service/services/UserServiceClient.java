package com.lasias.review_service.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserServiceClient {

    private final RestClient userRestClient;

    public String getUsername(String jwt){
        try {
            System.out.println(userRestClient.get()
                    .uri("/api/user")
                    .header("Authorization", "Bearer " + jwt)
                    .retrieve()
                    .body(String.class));

            return "getusername done";
        } catch (Exception e){
            log.error("Failed to fetch username from user-service: {}", e.getMessage());
            return "Unknown user";
        }
    }

}
