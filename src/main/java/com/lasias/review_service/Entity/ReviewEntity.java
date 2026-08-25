package com.lasias.review_service.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@Data
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String comment;

    //Rating 1-5
    @Column(nullable = false)
    private int rating;

    @Column(nullable = false)
    private Long bookingId;

    @Column(nullable = false)
    private Long roomTypeId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String username;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createDate;

}
