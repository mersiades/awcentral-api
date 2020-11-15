package com.mersiades.awcdata.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document
public class Game {

    @Id
    private String id;

    /*
     * textChannelId is provided by Discord when the Game's channels are created.
     * textChannelId acts as the Game's id
     */
    private String textChannelId;

    /* voiceChannelId is provided by Discord when the Game's channels are created. */
    private String voiceChannelId;

    private String name;

    @DBRef
    private List<GameRole> gameRoles = new ArrayList<>();

    public Game() {
    }

    public Game(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Game(String textChannelId, String voiceChannelId, String name) {
        this.textChannelId = textChannelId;
        this.voiceChannelId = voiceChannelId;
        this.name = name;
    }

    public Game(String id, String textChannelId, String voiceChannelId, String name) {
        this.id = id;
        this.textChannelId = textChannelId;
        this.voiceChannelId = voiceChannelId;
        this.name = name;
    }

    public Game(String textChannelId, String voiceChannelId, String name, GameRole mcGameRole) {
        this.textChannelId = textChannelId;
        this.voiceChannelId = voiceChannelId;
        this.name = name;
        this.gameRoles.add(mcGameRole);
    }

    public Game(String id, String textChannelId, String voiceChannelId, String name, GameRole mcGameRole) {
        this.id = id;
        this.textChannelId = textChannelId;
        this.voiceChannelId = voiceChannelId;
        this.name = name;
        this.gameRoles.add(mcGameRole);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Game [");
        if (this.getId() != null) {
            sb.append("id= ").append(this.getId());
        }
        if (this.name != null) {
            sb.append(", name= ").append(this.name);
        }
        if (this.textChannelId != null) {
            sb.append(", textChannelId= ").append(this.textChannelId);
        }

        if (this.voiceChannelId != null) {
            sb.append(", voiceChannelId= ").append(this.voiceChannelId);
        }

        sb.append(", gameRoles size= ").append(this.gameRoles.size());

        sb.append("]");

        return sb.toString();
    }

}
