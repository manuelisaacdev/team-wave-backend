package com.teamwave.artistservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
    public static final String TOPIC_USER_DELETED = "topic-user-deleted";

    public static final String TOPIC_CREATE_ARTIST = "topic-create-artist";
    public static final String TOPIC_ARTIST_DELETED = "topic-artist-deleted";
    public static final String TOPIC_ARTIST_CREATED = "topic-artist-created";

    @Bean
    NewTopic topicArtistDeleted() {
        return TopicBuilder
        .name(TOPIC_ARTIST_DELETED)
        .build();
    }

    @Bean
    NewTopic topicCreateArtist() {
        return TopicBuilder
        .name(TOPIC_CREATE_ARTIST)
        .build();
    }

    @Bean
    NewTopic topicArtistCreated() {
        return TopicBuilder
        .name(TOPIC_ARTIST_CREATED)
        .build();
    }

}
