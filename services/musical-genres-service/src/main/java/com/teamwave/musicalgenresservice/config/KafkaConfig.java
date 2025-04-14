package com.teamwave.musicalgenresservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
    public static final String TOPIC_USER_DELETED = "topic-user-deleted";
    public static final String TOPIC_ARTIST_DELETED = "topic-artist-deleted";

    public static final String TOPIC_MUSICAL_GENRE_UPDATED = "topic-music-genre-updated";

    @Bean
    NewTopic topicMusicalGenreUpdated() {
        return TopicBuilder
        .name(TOPIC_MUSICAL_GENRE_UPDATED)
        .build();
    }
}
