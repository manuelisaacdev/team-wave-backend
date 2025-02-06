package com.teamwave.albumservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
    public static final String TOPIC_DELETE_FILE = "topic-delete-file";
    public static final String TOPIC_USER_DELETED = "topic-user-deleted";
    public static final String TOPIC_MUSIC_DELETED = "topic-music-deleted";
    public static final String TOPIC_MUSIC_UPDATED = "topic-music-updated";
    public static final String TOPIC_ARTIST_DELETED = "topic-artist-deleted";
    public static final String TOPIC_MUSICAL_GENRE_UPDATED = "topic-music-genre-updated";
    public static final String TOPIC_MUSIC_ADDED_IN_ALBUM = "topic-music-added-in-album";
    public static final String TOPIC_MUSIC_REMOVED_IN_ALBUM = "topic-music-removed-in-album";

    public static final String TOPIC_ALBUM_COVER = "topic-album-cover";
    public static final String TOPIC_ALBUM_UPDATED = "topic-album-updated";
    public static final String TOPIC_ALBUM_DELETED = "topic-album-deleted";
    public static final String TOPIC_ALBUM_REACTIONS = "topic-album-reactions";

    @Bean
    NewTopic topicAlbumDeleted() {
        return TopicBuilder
        .name(TOPIC_ALBUM_DELETED)
        .build();
    }

    @Bean
    NewTopic topicAlbumUpdated() {
        return TopicBuilder
                .name(TOPIC_ALBUM_UPDATED)
                .build();
    }

    @Bean
    NewTopic topicAlbumReactions() {
        return TopicBuilder
        .name(TOPIC_ALBUM_REACTIONS)
        .build();
    }

    @Bean
    NewTopic topicAlbumCover() {
        return TopicBuilder
                .name(TOPIC_ALBUM_COVER)
                .build();
    }
}
