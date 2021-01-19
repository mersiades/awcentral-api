package com.mersiades.awcdata.models;

import com.mersiades.awccontent.enums.RoleType;
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
@NoArgsConstructor
@AllArgsConstructor
public class GameRole {

    @Id
    private String id;

    private RoleType role;

    @DBRef
    private Game game;

    @DBRef
    private User user;

    @Builder.Default
    private List<Npc> npcs = new ArrayList<>();

    @Builder.Default
    private List<Threat> threats = new ArrayList<>();

    @Builder.Default
    private List<Character> characters = new ArrayList<>();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("GameRole [");
        if (this.getId() != null) {
            sb.append("id= ").append(this.getId());
        }

        if (this.role != null) {
            sb.append(", role= ").append(this.role);
        }

        if (this.getGame() != null) {
            sb.append(", Game= ").append(this.getGame().getId());
        }

        if (this.getUser() == null) {
            sb.append(", User= null");
        } else if (this.getUser().getId() != null) {
            sb.append(", User= ").append(this.getUser().getId());
        }

        sb.append("]");

        return sb.toString();
    }
}
