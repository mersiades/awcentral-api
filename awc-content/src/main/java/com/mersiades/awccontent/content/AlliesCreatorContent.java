package com.mersiades.awccontent.content;

import com.mersiades.awccontent.enums.AllyType;
import com.mersiades.awccontent.models.AllyCreator;
import com.mersiades.awccontent.models.AllyCreatorContent;
import org.bson.types.ObjectId;

import java.util.List;

public class AlliesCreatorContent {

    public static final AllyCreatorContent friend = AllyCreatorContent.builder()
            .id(new ObjectId().toString())
            .allyType(AllyType.FRIEND)
            .impulse("to back you up")
            .build();

    public static final AllyCreatorContent lover = AllyCreatorContent.builder()
            .id(new ObjectId().toString())
            .allyType(AllyType.LOVER)
            .impulse("to give you shelter & comfort")
            .build();

    public static final AllyCreatorContent rightHand = AllyCreatorContent.builder()
            .id(new ObjectId().toString())
            .allyType(AllyType.RIGHT_HAND)
            .impulse("to follow through on your intentions")
            .build();

    public static final AllyCreatorContent representative = AllyCreatorContent.builder()
            .id(new ObjectId().toString())
            .allyType(AllyType.REPRESENTATIVE)
            .impulse("to pursue your interests in you absence")
            .build();

    public static final AllyCreatorContent guardian = AllyCreatorContent.builder()
            .id(new ObjectId().toString())
            .allyType(AllyType.GUARDIAN)
            .impulse("to intercept danger")
            .build();

    public static final AllyCreatorContent confidante = AllyCreatorContent.builder()
            .id(new ObjectId().toString())
            .allyType(AllyType.CONFIDANTE)
            .impulse("to give you advice, perspective, or absolution")
            .build();

    public static final AllyCreator allyCreator = AllyCreator.builder()
            .id(new ObjectId().toString())
            .allies(List.of(friend, lover, rightHand, representative, guardian, confidante))
            .build();
}
