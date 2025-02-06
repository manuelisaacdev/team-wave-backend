package com.teamwave.clipservice.model;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonPropertyOrder({"id", "userId", "createdAt", "clip"})
@Table(
    name = "favorites",
    indexes = @Index(name = "idx_favorites_user_id", columnList = "user_id"),
    uniqueConstraints = @UniqueConstraint(name = "uk_favorites_user_id_and_clip_id", columnNames = {"user_id", "clip_id"})
)
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @CreationTimestamp(source = SourceType.DB)
    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "clip_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_favorites_clips"))
    private Clip clip;
}
