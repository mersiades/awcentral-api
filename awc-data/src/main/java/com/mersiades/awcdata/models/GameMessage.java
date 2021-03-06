package com.mersiades.awcdata.models;

import com.mersiades.awccontent.enums.StatType;
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

    private String gameId;

    private String gameRoleId;

    private MessageType messageType;

    private String title;

    private String sentOn;

    private String content;

    private int roll1;

    private int roll2;

    private int rollModifier;

    private boolean usedPlusOneForward;

    private int rollResult;

    private StatType modifierStatName;

    private int additionalModifierValue;

    private String additionalModifierName;

    private int barterSpent;

    private int currentBarter;

    private int harmSuffered;

    private int currentHarm;

    private int stockSpent;

    private int currentStock;

}
