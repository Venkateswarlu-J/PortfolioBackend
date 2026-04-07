package com.jvportfolio.service;

import com.jvportfolio.model.Feedback;
import com.jvportfolio.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final JavaMailSender mailSender;

    @Value("${portfolio.owner.email}")
    private String ownerEmail;

    public Feedback saveFeedback(String email, String message, String ipAddress) {
        // Save to DB
        Feedback feedback = new Feedback();
        feedback.setEmail(email);
        feedback.setMessage(message);
        feedback.setIpAddress(ipAddress);
        Feedback saved = feedbackRepository.save(feedback);

        // Send email notification
        sendEmailNotification(email, message);

        return saved;
    }

    private void sendEmailNotification(String senderEmail, String message) {
        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(ownerEmail);
            mail.setFrom(ownerEmail);
            mail.setSubject("📬 New Portfolio Feedback from " + senderEmail);
            mail.setText(
                "You received a new message on JVPortfolio!\n\n" +
                "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
                "From   : " + senderEmail + "\n" +
                "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n\n" +
                message + "\n\n" +
                "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
                "Sent via JVPortfolio Contact Form"
            );
            mailSender.send(mail);
            log.info("Email notification sent for feedback from {}", senderEmail);
        } catch (Exception e) {
            log.error("Failed to send email notification: {}", e.getMessage());
            // Don't rethrow — feedback is already saved, email is best-effort
        }
    }

    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAllByOrderByCreatedAtDesc();
    }
}
