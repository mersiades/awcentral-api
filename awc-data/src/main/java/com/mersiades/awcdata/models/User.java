package com.mersiades.awcdata.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@Document
public class User {

    @Id
    private String id;

    private String discordId;

    // one to many
    private final Set<GameRole> gameRoles = new HashSet<>();

    public User() {
    }

    public User(String id, String discordId) {
        this.id = id;
        this.discordId = discordId;
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
