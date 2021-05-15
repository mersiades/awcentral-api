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
public class Game {

    @Id
    private String id;

    private String name;

    private User mc;

    private String commsApp;

    private String commsUrl;

    private Boolean hasFinishedPreGame;

    private Boolean showFirstSession;

    private ThreatMap threatMap;

    @Builder.Default
    private List<GameMessage> gameMessages = new ArrayList<>();

    @Builder.Default
    private List<String> invitees = new ArrayList<>();

    @DBRef
    @Builder.Default
    private List<User> players = new ArrayList<>();

    @DBRef
    @Builder.Default
    private List<GameRole> gameRoles = new ArrayList<>();


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Game [");
        if (this.getId() != null) {
            sb.append("id= ").append(this.getId());
        }
        if (this.name != null) {
            sb.append(", name= ").append(this.name);
        }

        if (this.gameRoles != null) {
            sb.append(", gameRoles size= ").append(this.gameRoles.size());
        }

        sb.append("]");

        return sb.toString();
    }

}
