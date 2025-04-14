package com.teamwave.artistservice.model;

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
@JsonPropertyOrder({"id", "artistId"})
@Table(
    name = "participants",
    indexes = @Index(name = "idx_participants_music_id", columnList = "music_id"),
    uniqueConstraints = @UniqueConstraint(name = "uk_participants_music_id_and_artist_id", columnNames = {"music_id", "artist_id"})
)
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "music_id")
    private UUID musicId;

    @CreationTimestamp(source = SourceType.DB)
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "artist_id", referencedColumnName = "id", nullable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_participants_artists"))
    private Artist artist;
}
