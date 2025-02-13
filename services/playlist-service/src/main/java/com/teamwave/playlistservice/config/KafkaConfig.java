package com.teamwave.playlistservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
    public static final String TOPIC_DELETE_FILE = "topic-delete-file";
    public static final String TOPIC_USER_DELETED = "topic-user-deleted";
    public static final String TOPIC_MUSIC_UPDATED = "topic-music-updated";
    public static final String TOPIC_MUSIC_DELETED = "topic-music-deleted";

    public static final String TOPIC_PLAYLIST_COVER = "topic-playlist-cover";
    public static final String TOPIC_PLAYLIST_DELETED = "topic-playlist-deleted";
    public static final String TOPIC_PLAYLIST_REACTIONS = "topic-playlist-reactions";

    @Bean
    NewTopic topicPlaylistDeleted() {
        return TopicBuilder.name(TOPIC_PLAYLIST_DELETED)
        .build();
    }

    @Bean
    NewTopic topicPlaylistReactions() {
        return TopicBuilder.name(TOPIC_PLAYLIST_REACTIONS)
        .build();
    }

    @Bean
    NewTopic topicPlaylistCover() {
        return TopicBuilder.name(TOPIC_PLAYLIST_COVER)
        .build();
    }

}
