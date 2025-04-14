package com.teamwave.reactionservice.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {
    public static final String TOPIC_USER_DELETED = "topic-user-deleted";
    public static final String TOPIC_CLIP_DELETED = "topic-clip-deleted";
    public static final String TOPIC_MUSIC_DELETED = "topic-music-deleted";
    public static final String TOPIC_ALBUM_DELETED = "topic-album-deleted";
    public static final String TOPIC_PLAYLIST_DELETED = "topic-playlist-deleted";

    public static final String TOPIC_CLIP_REACTIONS = "topic-clip-reactions";
    public static final String TOPIC_MUSIC_REACTIONS = "topic-music-reactions";
    public static final String TOPIC_ALBUM_REACTIONS = "topic-album-reactions";
    public static final String TOPIC_PLAYLIST_REACTIONS = "topic-playlist-reactions";

}
