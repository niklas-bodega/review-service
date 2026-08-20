package com.lasias.review_service.config;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewPrincipal {
        private final long userId;
        private final String role;
}
