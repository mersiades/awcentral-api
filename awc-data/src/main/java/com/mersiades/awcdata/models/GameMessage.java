package com.mersiades.awcdata.models;

import com.mersiades.awcdata.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameMessage {

    @Id
    private String id;

    private MessageType messageType;

    private String senderName;

    private String content;
}
