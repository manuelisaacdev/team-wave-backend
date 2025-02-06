package com.teamwave.clipservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Length;
import org.hibernate.annotations.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id", "title", "releaseDate", "duration", "clipType",
    "video", "thumbnail", "createdAt", "updatedAt", "likes", "available",
    "dislikes", "loves", "favorites", "reproductions", "musicId", "description"
})
@Table(
    name = "clips",
    indexes = @Index(name = "idx_clips_music_id", columnList = "music_id"),
    uniqueConstraints = @UniqueConstraint(name = "uk_clips_music_id", columnNames = "music_id")
)
public class Clip {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(name = "release_date", nullable = false)
    private LocalDate releaseDate;

    @Column(length = Length.LONG32)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "clip_type", nullable = false)
    private ClipType clipType;

    @ColumnDefault("false")
    @Column(nullable = false, insertable = false)
    private Boolean available;

    @ColumnDefault("0")
    @Column(nullable = false, insertable = false)
    private Long duration;

    @Column(insertable = false)
    private String video;

    @Column(insertable = false)
    private String thumbnail;

    @CreationTimestamp(source = SourceType.DB)
    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp(source = SourceType.DB)
    @Column(name = "updated_at", nullable = false, insertable = false)
    private LocalDateTime updatedAt;

    @ColumnDefault("0")
    @Column(nullable = false, insertable = false)
    private Long likes;

    @ColumnDefault("0")
    @Column(nullable = false, insertable = false)
    private Long dislikes;

    @ColumnDefault("0")
    @Column(nullable = false, insertable = false)
    private Long loves;

    @Formula("(SELECT COUNT(f.clip_id) FROM clips c LEFT JOIN favorites f ON (f.clip_id=c.id) WHERE c.id=id GROUP BY c.id)")
    private Long favorites;

    @ColumnDefault("0")
    @Column(nullable = false, insertable = false)
    private Long reproductions;

    @Column(name = "music_id", nullable = false)
    private UUID musicId;

}
