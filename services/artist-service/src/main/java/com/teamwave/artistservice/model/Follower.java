package com.teamwave.artistservice.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonPropertyOrder({"id", "notify", "createdAt", "userId", "artist"})
@Table(
    name = "followers",
    indexes = {
        @Index(name = "idx_followers_user_id", columnList = "user_id"),
        @Index(name = "idx_followers_artist_id", columnList = "artist_id")
    },
    uniqueConstraints = @UniqueConstraint(name = "uk_artists_user_id_and_artist_id", columnNames = {"user_id", "artist_id"})
)
public class Follower {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ColumnDefault("true")
    @Column(nullable = false, insertable = false)
    private Boolean notify;

    @CreationTimestamp(source = SourceType.DB)
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "artist_id", referencedColumnName = "id", nullable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_followers_artists"))
    private Artist artist;

}
