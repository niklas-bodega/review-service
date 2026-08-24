package com.lasias.review_service.services;

import com.lasias.review_service.dtos.UserDto;
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
            UserDto user = userRestClient.get()
                    .uri("/api/user")
                    .header("Authorization", "Bearer " + jwt)
                    .retrieve()
                    .body(UserDto.class);
            if(user != null && user.name() != null){
                return user.name();
            }
            else{
                return "Unknown username";
            }
        } catch (Exception e){
            log.error("Failed to fetch username from user-service: {}", e.getMessage());
            return "Unknown user";
        }
    }

}
