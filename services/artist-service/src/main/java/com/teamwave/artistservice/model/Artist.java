package com.teamwave.artistservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Length;
import org.hibernate.annotations.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id", "name", "verified", "debutYear", "exercising",
    "follow", "subscribe", "popularity", "createdAt", "updatedAt",
    "totalSongs", "totalAlbums", "totalPlaylists","totalEvents",
    "totalSubscribers", "totalFollowers", "totalCollaborations", "userId", "biography"
})
@Table(
    name = "artists",
    uniqueConstraints = @UniqueConstraint(name = "uk_artists_user_id", columnNames = "user_id")
)
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @ColumnDefault("false")
    @Column(nullable = false, insertable = false)
    private Boolean verified;

    @Column(nullable = false)
    private Integer debutYear;

    @ColumnDefault("true")
    @Column(nullable = false, insertable = false)
    private Boolean exercising;

    @ColumnDefault("true")
    @Column(nullable = false, insertable = false)
    private Boolean follow;

    @ColumnDefault("true")
    @Column(nullable = false, insertable = false)
    private Boolean subscribe;

    @ColumnDefault("'LOWEST'")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, insertable = false)
    private  Popularity popularity;

    @Column(length = Length.LONG32)
    private String biography;

    @CreationTimestamp(source = SourceType.DB)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp(source = SourceType.DB)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(name = "updated_at", nullable = false, insertable = false, updatable = false)
    private LocalDateTime updatedAt;

    @ColumnDefault("0")
    @Column(name = "total_songs", nullable = false, insertable = false)
    private Long totalSongs;

    @ColumnDefault("0")
    @Column(name = "total_albums", nullable = false, insertable = false)
    private Long totalAlbums;

    @ColumnDefault("0")
    @Column(name = "total_playlists", nullable = false, insertable = false)
    private Long totalPlaylists;

    @ColumnDefault("0")
    @Column(name = "total_events", nullable = false, insertable = false)
    private Long totalEvents;

    @ColumnDefault("0")
    @Column(name = "total_collaborations", nullable = false, insertable = false)
    private Long totalCollaborations;

    @Formula("(SELECT COUNT(f.artist_id) FROM artists a LEFT JOIN followers f ON (f.artist_id=a.id) WHERE a.id=id GROUP BY a.id)")
    private Long totalFollowers;

    @Formula("(SELECT COUNT(s.artist_id) FROM artists a LEFT JOIN subscribers s ON (s.artist_id=a.id) WHERE a.id=id GROUP BY a.id)")
    private Long totalSubscribers;

    @Column(name = "user_id", nullable = false, updatable = false)
    private UUID userId;

}
