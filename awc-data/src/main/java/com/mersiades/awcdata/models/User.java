package com.mersiades.awcdata.models;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(name = "discord_id")
    private String discordId;

    @OneToMany(fetch= FetchType.EAGER, cascade = CascadeType.REMOVE, mappedBy = "user")
    private final Set<GameRole> gameRoles = new HashSet<>();

    public User() {
    }

    public User(Long id) {
        super(id);
    }

    public User(String discordId) {
        this.discordId = discordId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("User [");
        if (this.getId() != null) {
            sb.append("id= ").append(this.getId());
        }
        if (this.discordId != null) {
            sb.append(", discordId= ").append(this.discordId);
        }

        sb.append(", gameRoles size= ").append(this.gameRoles.size());

        sb.append("]");

        return sb.toString();
    }

}
