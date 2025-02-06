package com.teamwave.musicalgenresservice.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.SourceType;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonPropertyOrder({"id", "userId", "createdAt", "musicalGenre"})
@Table(
    name = "users_musical_genres",
    indexes = @Index(name = "idx_users_musical_genres_user_id", columnList = "user_id"),
    uniqueConstraints = @UniqueConstraint(name = "uk_users_musical_genres_user_id_and_musical_genre_id", columnNames = {"user_id", "musical_genre_id"})
)
public class UserMusicalGenre {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @CreationTimestamp(source = SourceType.DB)
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "musical_genre_id", referencedColumnName = "id", nullable = false, updatable = false)
    private MusicalGenre musicalGenre;

}
