package com.teamwave.artistservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@JsonPropertyOrder({"id", "url", "createdAt", "socialMediaType"})
@Table(
    name = "social_media",
    indexes = @Index(name = "idx_social_media_artist_id", columnList = "artist_id"),
    uniqueConstraints = @UniqueConstraint(name = "uk_social_media_url_and_artist_id", columnNames = {"url", "artist_id"})
)
public class SocialMedia {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String url;

    @Enumerated(EnumType.STRING)
    @Column(name = "social_media_Type", nullable = false)
    private SocialMediaType socialMediaType;

    @CreationTimestamp(source = SourceType.DB)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "artist_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_social_media_artists"))
    private Artist artist;
}
