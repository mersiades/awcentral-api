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

    @ManyToMany(mappedBy = "games")
    private Set<User> users;

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

}
