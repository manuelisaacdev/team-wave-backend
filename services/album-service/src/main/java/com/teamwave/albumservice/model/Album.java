package com.teamwave.albumservice.model;

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
import org.hibernate.Length;
import org.hibernate.annotations.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id", "name", "releaseDate", "albumType", "featured", "privacy",
    "createdAt", "updatedAt", "reproductions", "likes","dislikes", "loves",
    "galleries", "cover", "artistId", "description", "labels", "albumMusicalGenres", "faixas"
})
@Table(name = "albums", indexes = @Index(name = "idx_albums_artist_id", columnList = "artist_id"))
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(length = Length.LONG32, nullable = false)
    private String description;

    @Column(name = "release_date", nullable = false)
    private LocalDate releaseDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "album_type", nullable = false)
    private AlbumType albumType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Privacy privacy;

    @ColumnDefault("false")
    @Column(name = "featured", nullable = false, insertable = false)
    private Boolean featured;

    private String cover;

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

    @ColumnDefault("0")
    @Column(nullable = false, insertable = false)
    private Long reproductions;

    @Formula("(SELECT COUNT(g.album_id) FROM albums a LEFT JOIN galleries g ON (g.album_id=a.id) WHERE a.id=id GROUP BY a.id)")
    private Long galleries;

    @Column(name = "artist_id", nullable = false, updatable = false)
    private UUID artistId;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "album", orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Label> labels;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "album", orphanRemoval = true, fetch = FetchType.EAGER)
    private List<AlbumMusicalGenre> albumMusicalGenres;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "album", orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Faixa> faixas;

}
