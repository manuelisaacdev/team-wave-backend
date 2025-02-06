package com.teamwave.albumservice.model;

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
@JsonPropertyOrder({"id", "userId", "createdAt", "album"})
@Table(
    name = "galleries",
    indexes = @Index(name = "idx_galleries_user_id", columnList = "user_id"),
    uniqueConstraints = @UniqueConstraint(name = "uk_galleries_user_id_and_album_id", columnNames = {"user_id", "album_id"})
)
public class Gallery {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    @CreationTimestamp(source = SourceType.DB)
    private LocalDateTime createdAt;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "album_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_galleries_albums"))
    private Album album;
}
