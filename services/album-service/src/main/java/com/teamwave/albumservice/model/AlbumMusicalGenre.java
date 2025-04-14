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
@JsonPropertyOrder({"id", "name", "musicalGenreId", "createdAt"})
@Table(
    name = "album_musical_genres",
    indexes = @Index(name = "idx_album_musical_genres_album_id", columnList = "album_id"),
    uniqueConstraints = @UniqueConstraint(name = "uk_album_musical_genres_musical_genre_id_and_album_id", columnNames = {"musical_genre_id", "album_id"})
)
public class AlbumMusicalGenre {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(name = "musical_genre_id", nullable = false)
    private UUID musicalGenreId;

    @CreationTimestamp(source = SourceType.DB)
    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "album_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_album_musical_genres_albums"))
    private Album album;
}
