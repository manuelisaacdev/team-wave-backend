package com.teamwave.musicservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
    public static final String TOPIC_DELETE_FILE = "topic-delete-file";
    public static final String TOPIC_ALBUM_UPDATED = "topic-album-updated";
    public static final String TOPIC_ALBUM_DELETED = "topic-album-deleted";
    public static final String TOPIC_ARTIST_DELETED = "topic-artist-deleted";
    public static final String TOPIC_MUSICAL_GENRE_UPDATED = "topic-music-genre-updated";

    public static final String TOPIC_MUSIC_COVER = "topic-music-cover";
    public static final String TOPIC_MUSIC_AUDIO = "topic-music-audio";
    public static final String TOPIC_MUSIC_DELETED = "topic-music-deleted";
    public static final String TOPIC_MUSIC_UPDATED = "topic-music-updated";
    public static final String TOPIC_MUSIC_REACTIONS = "topic-music-reactions";
    public static final String TOPIC_MUSIC_ADDED_IN_ALBUM = "topic-music-added-in-album";
    public static final String TOPIC_MUSIC_REMOVED_IN_ALBUM = "topic-music-removed-in-album";

    @Bean
    NewTopic topicMusicCover() {
        return TopicBuilder
        .name(TOPIC_MUSIC_COVER)
        .build();
    }

    @Bean
    NewTopic topicMusicAudio() {
        return TopicBuilder
        .name(TOPIC_MUSIC_AUDIO)
        .build();
    }

    @Bean
    NewTopic topicMusicDeleted() {
        return TopicBuilder
        .name(TOPIC_MUSIC_DELETED)
        .build();
    }

    @Bean
    NewTopic topicMusicUpdated() {
        return TopicBuilder
        .name(TOPIC_MUSIC_UPDATED)
        .build();
    }

    @Bean
    NewTopic topicMusicReactions() {
        return TopicBuilder
        .name(TOPIC_MUSIC_REACTIONS)
        .build();
    }

    @Bean
    NewTopic topicMusicAddedInAlbum() {
        return TopicBuilder
                .name(TOPIC_MUSIC_ADDED_IN_ALBUM)
                .build();
    }

    @Bean
    NewTopic topicMusicRemovedInAlbum() {
        return TopicBuilder
                .name(TOPIC_MUSIC_REMOVED_IN_ALBUM)
                .build();
    }

}
