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
@Table(name = "playlist_reactions")
@JsonPropertyOrder({"id", "createdAt", "reactionType", "userId", "playlistId"})
@PrimaryKeyJoinColumn(name = "reaction_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_playlist_reactions"))
public class PlaylistReaction extends Reaction {
    private UUID playlistId;
}
