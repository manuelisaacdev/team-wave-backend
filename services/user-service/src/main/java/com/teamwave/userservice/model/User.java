package com.teamwave.userservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id", "name", "gender", "dateOfBirth", "enabled",
    "hasLoggedIn", "verified", "deleted", "locked", "artistId",
    "email", "countryId", "profilePicture", "coverPicture", "roles"
})
@Table(
    name = "users",
    indexes = @Index(name = "idx_users_email", columnList = "email"),
    uniqueConstraints = @UniqueConstraint(name = "uk_users_email", columnNames = "email")
)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;


    @ColumnDefault("false")
    @Column(nullable = false, insertable = false)
    private Boolean verified;

    @ColumnDefault("false")
    @Column(nullable = false, insertable = false)
    private Boolean locked;

    @ColumnDefault("false")
    @Column(nullable = false, insertable = false)
    private Boolean enabled;

    @ColumnDefault("false")
    @Column(name = "has_logged_in", nullable = false, insertable = false)
    private Boolean hasLoggedIn;

    @Column(name = "country_id", nullable = false)
    private UUID countryId;

    @Column(name = "profile_picture", insertable = false)
    private String profilePicture;

    @Column(name = "cover_picture", insertable = false)
    private String coverPicture;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(name = "artist_id", insertable = false)
    private UUID artistId;

    @CollectionTable(
        name = "roles",
        indexes = @Index(name = "idx_roles_user_id", columnList = "user_id"),
        uniqueConstraints = @UniqueConstraint(name = "uk_roles_role_and_user_id", columnNames = {"role", "user_id"}),
        joinColumns = @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_roles_users"))
    )
    @ColumnDefault("'USER'")
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;

}
