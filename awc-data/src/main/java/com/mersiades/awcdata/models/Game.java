package com.mersiades.awcdata.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "games")
public class Game extends BaseEntity {

    /*
     * textChannelId is provided by Discord when the Game's channels are created.
     * textChannelId acts as the Game's id
     */
    @Column(name = "text_channel_id")
    private Long textChannelId;

    /* voiceChannelId is provided by Discord when the Game's channels are created. */
    @Column(name = "voice_channel_id")
    private Long voiceChannelId;

    @Column(name = "name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "game")
    private final Set<GameRole> gameRoles = new HashSet<>();

    public Game() {
    }

    public Game(Long textChannelId, Long voiceChannelId, String name) {
        this.textChannelId = textChannelId;
        this.voiceChannelId = voiceChannelId;
        this.name = name;
    }

    public Long getTextChannelId() {
        return textChannelId;
    }

    public Long getVoiceChannelId() {
        return voiceChannelId;
    }

    public String getName() {
        return name;
    }

    public Set<GameRole> getGameRoles() {
        return gameRoles;
    }
}
