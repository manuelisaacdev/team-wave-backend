package com.teamwave.albumservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.SourceType;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonPropertyOrder({"id", "musicId", "title", "duration", "trackNumber", "createdAt"})
@Table(
    name = "faixas",
    indexes = @Index(name = "idx_faixas_album_id", columnList = "album_id"),
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_faixas_music_id", columnNames = "music_id"),
        @UniqueConstraint(name = "uk_faixas_music_id_and_track_number", columnNames = {"music_id", "track_number"})
    }
)
public class Faixa {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "music_id", nullable = false)
    private UUID musicId;

    @Column(name = "title", nullable = false)
    private UUID title;

    @Column(nullable = false)
    private Long duration;

    @Column(name = "track_number", nullable = false)
    private Integer trackNumber;

    @CreationTimestamp(source = SourceType.DB)
    @Column(nullable = false, insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "album_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_faixas_albums"))
    private Album album;

}
