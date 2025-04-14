package com.teamwave.clipservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
    public static final String GROUP_ID = "team-wave-group-clip-service";
    public static final String TOPIC_DELETE_FILE = "topic-delete-file";
    public static final String TOPIC_MUSIC_DELETED = "topic-music-deleted";

    public static final String TOPIC_CLIP_VIDEO = "topic-clip-video";
    public static final String TOPIC_CLIP_DELETED = "topic-clip-deleted";
    public static final String TOPIC_CLIP_REACTIONS = "topic-clip-reactions";
    public static final String TOPIC_CLIP_THUMBNAIL = "topic-clip-thumbnail";

    @Bean
    NewTopic topicClipDeleted() {
        return TopicBuilder
        .name(TOPIC_CLIP_DELETED)
        .build();
    }

    @Bean
    NewTopic topicClipReactions() {
        return TopicBuilder
        .name(TOPIC_CLIP_REACTIONS)
        .build();
    }

    @Bean
    NewTopic topicClipVideo() {
        return TopicBuilder
        .name(TOPIC_CLIP_VIDEO)
        .build();
    }

    @Bean
    NewTopic topicClipThumbnail() {
        return TopicBuilder
        .name(TOPIC_CLIP_THUMBNAIL)
        .build();
    }
}
