package com.mersiades.awcdata.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "games")
public class Game extends BaseEntity {

    /*
     * textChannelId is provided by Discord when the Game's channels are created.
     * textChannelId acts as the Game's id
     */
    @Column(name = "text_channel_id")
    private String textChannelId;

    /* voiceChannelId is provided by Discord when the Game's channels are created. */
    @Column(name = "voice_channel_id")
    private String voiceChannelId;

    @Column(name = "name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "game")
    private final Set<GameRole> gameRoles = new HashSet<>();

    public Game() {
    }

    public Game(String textChannelId, String voiceChannelId, String name) {
        this.textChannelId = textChannelId;
        this.voiceChannelId = voiceChannelId;
        this.name = name;
    }

    public Game(Long id, String textChannelId, String voiceChannelId, String name) {
        super(id);
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
