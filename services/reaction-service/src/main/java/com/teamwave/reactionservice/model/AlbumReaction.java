package com.teamwave.reactionservice.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@Table(name = "album_reactions")
@JsonPropertyOrder({"id", "createdAt", "reactionType", "userId", "albumId"})
@PrimaryKeyJoinColumn(name = "reaction_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_album_reactions"))
public class AlbumReaction extends Reaction {
    private UUID albumId;
}
