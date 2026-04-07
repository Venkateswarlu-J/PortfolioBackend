package com.jvportfolio.controller;

import com.jvportfolio.model.Feedback;
import com.jvportfolio.service.FeedbackService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/feedback")
@RequiredArgsConstructor
@Slf4j
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping
    public ResponseEntity<?> submitFeedback(
            @Valid @RequestBody FeedbackRequest request,
            HttpServletRequest httpRequest) {
        System.out.println("REcieved your message🔥 FEEDBACK API HIT");
        log.info("Received feedback from: {}", request.getEmail());
        String ip = getClientIp(httpRequest);
        Feedback saved = feedbackService.saveFeedback(request.getEmail(), request.getMessage(), ip);

        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Feedback submitted successfully",
            "id", saved.getId()
        ));
    }

    @GetMapping
    public ResponseEntity<List<Feedback>> getAllFeedbacks() {
        return ResponseEntity.ok(feedbackService.getAllFeedbacks());
    }

    private String getClientIp(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }

    @Data
    public static class FeedbackRequest {
        @NotBlank(message = "Email is required")
        @Email(message = "Please provide a valid email address")
        private String email;

        @NotBlank(message = "Message is required")
        @Size(min = 10, max = 2000, message = "Message must be between 10 and 2000 characters")
        private String message;
    }
}
