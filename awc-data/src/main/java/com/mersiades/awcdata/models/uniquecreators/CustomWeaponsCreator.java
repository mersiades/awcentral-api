package com.mersiades.awcdata.models.uniquecreators;

import com.mersiades.awcdata.models.ItemCharacteristic;
import com.mersiades.awcdata.models.TaggedItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomWeaponsCreator {

    @Id
    private String id;

    // ------------------------------------------- CUSTOM_WEAPONS ------------------------------------------------- //
    private String firearmsTitle;

    private String firearmsBaseInstructions;

    @Builder.Default
    private List<TaggedItem> firearmsBaseOptions = new ArrayList<>();

    private String firearmsOptionsInstructions;

    @Builder.Default
    private List<ItemCharacteristic> firearmsOptionsOptions = new ArrayList<>();

    private String handTitle;

    private String handBaseInstructions;

    @Builder.Default
    private List<TaggedItem> handBaseOptions = new ArrayList<>();

    private String handOptionsInstructions;

    @Builder.Default
    private List<ItemCharacteristic> handOptionsOptions = new ArrayList<>();
}
