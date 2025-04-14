package com.teamwave.playlistservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "playlists", indexes = @Index(name = "idx_playlists_user_id", columnList = "user_id"))
@JsonPropertyOrder({
    "id", "name", "privacy","likes", "dislikes", "loves", "galleries",
    "reproductions", "createdAt", "updatedAt", "userId", "cover", "description", "musicsPlaylist"
})
public class Playlist extends RepresentationModel<Playlist> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    private String cover;

    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Privacy privacy;

    @ColumnDefault("0")
    @Column(nullable = false, insertable = false)
    private Long likes;

    @ColumnDefault("0")
    @Column(nullable = false, insertable = false)
    private Long dislikes;

    @ColumnDefault("0")
    @Column(nullable = false, insertable = false)
    private Long loves;

    @Formula("(SELECT COUNT(g.playlist_id) FROM playlists p LEFT JOIN galleries g ON (g.playlist_id=p.id) WHERE p.id=id GROUP BY p.id)")
    private Long galleries;

    @ColumnDefault("0")
    @Column(nullable = false, insertable = false)
    private Long reproductions;

    @CreationTimestamp(source = SourceType.DB)
    @Column(nullable = false, insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp(source = SourceType.DB)
    @Column(nullable = false, insertable = false)
    private LocalDateTime updatedAt;

    @Column(name = "user_id", nullable = false, updatable = false)
    private UUID userId;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "playlist", orphanRemoval = true, fetch = FetchType.EAGER)
    private List<MusicPlaylist> musicsPlaylist;
}
