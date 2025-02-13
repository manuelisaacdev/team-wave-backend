package com.teamwave.fileservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
    // User - Profile picture & Cover picture
    public static final String TOPIC_USER_COVER_PICTURE = "topic-user-cover-picture";
    public static final String TOPIC_USER_PROFILE_PICTURE = "topic-user-profile-picture";

    // Album & Playlist - Cover
    public static final String TOPIC_ALBUM_COVER = "topic-album-cover";
    public static final String TOPIC_PLAYLIST_COVER = "topic-playlist-cover";

    // Music - Audio & Cover
    public static final String TOPIC_MUSIC_COVER = "topic-music-cover";
    public static final String TOPIC_MUSIC_AUDIO = "topic-music-audio";

    // Clip - Video & Thumbnail
    public static final String TOPIC_CLIP_VIDEO = "topic-clip-video";
    public static final String TOPIC_CLIP_THUMBNAIL = "topic-clip-thumbnail";

    // To Delete file
    public static final String TOPIC_DELETE_FILE = "topic-delete-file";

    @Bean
    NewTopic topicDeleteFiles() {
        return TopicBuilder
        .name(TOPIC_DELETE_FILE)
        .partitions(1)
        .build();
    }
}
