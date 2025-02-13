package com.teamwave.emailservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
    public static final String TOPIC_SEND_EMAIL = "topic-send-email";

    @Bean
    NewTopic topicSendEmail() {
        return TopicBuilder
        .name(TOPIC_SEND_EMAIL)
        .build();
    }

}
