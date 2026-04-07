package com.jvportfolio.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "feedbacks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String message;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "ip_address")
    private String ipAddress;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
