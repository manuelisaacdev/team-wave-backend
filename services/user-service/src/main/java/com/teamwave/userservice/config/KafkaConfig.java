package com.teamwave.userservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
    public static final String TOPIC_SEND_EMAIL = "topic-send-email";
    public static final String TOPIC_DELETE_FILE = "topic-delete-file";
    public static final String TOPIC_CREATE_ARTIST = "topic-create-artist";
    public static final String TOPIC_ARTIST_CREATED = "topic-artist-created";
    public static final String TOPIC_ARTIST_DELETED = "topic-artist-deleted";

    public static final String TOPIC_USER_DELETED = "topic-user-deleted";
    public static final String TOPIC_USER_COVER_PICTURE = "topic-user-cover-picture";
    public static final String TOPIC_USER_PROFILE_PICTURE = "topic-user-profile-picture";

    @Bean
    NewTopic topicUserDeleted() {
        return TopicBuilder
        .name(TOPIC_USER_DELETED)
        .build();
    }

    @Bean
    NewTopic topicUserProfilePicture() {
        return TopicBuilder
        .name(TOPIC_USER_PROFILE_PICTURE)
        .build();
    }

    @Bean
    NewTopic topicUserCoverPicture() {
        return TopicBuilder
        .name(KafkaConfig.TOPIC_USER_COVER_PICTURE)
        .build();
    }
}
