package com.teamwave.emailservice.service;

import com.teamwave.common.dto.kafka.EmailDTO;
import com.teamwave.emailservice.config.KafkaConfig;
import com.teamwave.emailservice.exception.EmailServiceException;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailService {
    private final String from;
    private final JavaMailSender mailSender;

    public EmailService(@Value("${spring.mail.username}") String from, JavaMailSender mailSender) {
        this.from = from;
        this.mailSender = mailSender;
    }

    @KafkaListener(topics = KafkaConfig.TOPIC_SEND_EMAIL)
    public void handleSendEmail(EmailDTO emailDTO) {
        log.info("Sending email to: {}", emailDTO.to());
        try {
            MimeMessage message = mailSender.createMimeMessage();
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, emailDTO.to());
            message.setSubject(emailDTO.subject());
            message.setContent(emailDTO.body(), "text/html; charset=utf-8");
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new EmailServiceException("Failed to send email to: " + emailDTO.to(), e);
        }
    }
}
