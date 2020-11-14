package com.mersiades.awcdata.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private String id;

    private String discordId;

    // one to many
    @DBRef
    private List<GameRole> gameRoles = new ArrayList<>();

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

        if (this.gameRoles != null) {
            sb.append(", gameRoles size= ").append(this.gameRoles.size());
        }

        sb.append("]");

        return sb.toString();
    }

}
