package com.mersiades.awcdata.models;

import com.mersiades.awcdata.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameMessage {

    @Id
    private String id;

    private String gameId;

    private String gameroleId;

    private String senderName;

    private MessageType messageType;

    private String content;

    private String sentOn = Instant.now().toString();
}
