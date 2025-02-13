package com.teamwave.playlistservice.model;

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
@JsonPropertyOrder({"id", "duration", "createdAt", "musicId", "cover"})
@Table(
    name = "musics_playlist",
    indexes = @Index(name = "idx_musics_playlist_playlist_id", columnList = "playlist_id"),
    uniqueConstraints = @UniqueConstraint(name = "uk_musics_playlist_music_id_and_playlist_id", columnNames = {"music_id", "playlist_id"})
)
public class MusicPlaylist {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "music_id", nullable = false)
    private UUID musicId;

    private String cover;

    @Column(nullable = false)
    private Long duration;

    @CreationTimestamp(source = SourceType.DB)
    @Column(nullable = false, insertable = false)
    private LocalDateTime createdAt;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "playlist_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_musics_playlist_playlists"))
    private Playlist playlist;
}
