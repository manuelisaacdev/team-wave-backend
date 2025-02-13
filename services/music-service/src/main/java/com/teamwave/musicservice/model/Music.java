package com.teamwave.musicservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Length;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "musics", indexes = @Index(name = "idx_musics_artist_id", columnList = "artist_id"))
@JsonPropertyOrder({
    "id", "title", "duration", "available", "releaseType", "releaseDate", "clipId",
    "audio", "cover", "createdAt", "updatedAt", "likes", "dislikes", "loves", "albumId", "albumName",
    "playlists", "reproductions", "musicalGenreId", "musicalGenreName", "artistId", "lyrics","description"
})
public class Music {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(length = Length.LONG32)
    private String description;

    @Column(length = Length.LONG32)
    private String lyrics;

    @Column(name = "release_date", nullable = false)
    private LocalDate releaseDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "release_type", nullable = false)
    private ReleaseType releaseType;

    @CreationTimestamp(source = SourceType.DB)
    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp(source = SourceType.DB)
    @Column(name = "updated_at", nullable = false, insertable = false)
    private LocalDateTime updatedAt;

    @ColumnDefault("false")
    @Column(nullable = false, insertable = false)
    private Boolean available;

    @Column(insertable = false)
    private String cover;

    @Column(insertable = false)
    private String audio;

    @ColumnDefault("0")
    @Column(nullable = false, insertable = false)
    private Long duration;

    @ColumnDefault("0")
    @Column(nullable = false, insertable = false)
    private Long likes;

    @ColumnDefault("0")
    @Column(nullable = false, insertable = false)
    private Long dislikes;

    @ColumnDefault("0")
    @Column(nullable = false, insertable = false)
    private Long loves;

    @ColumnDefault("0")
    @Column(nullable = false, insertable = false)
    private Long playlists;

    @ColumnDefault("0")
    @Column(nullable = false, insertable = false)
    private Long reproductions;

    @Column(name = "musical_genre_id", nullable = false)
    private UUID musicalGenreId;

    @Column(name = "musical_genre_name", nullable = false)
    private String musicalGenreName;

    @Column(name = "album_id", insertable = false)
    private UUID albumId;

    @Column(name = "album_name", insertable = false)
    private String albumName;

    @Column(name = "artist_id", nullable = false, updatable = false)
    private UUID artistId;

}
