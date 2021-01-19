package com.mersiades.awccontent.models;

import com.mersiades.awccontent.enums.LookType;
import com.mersiades.awccontent.enums.PlaybookType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Look {

    @Id
    private String id;

    private PlaybookType playbookType;

    private LookType category;

    private String look;

    private PlaybookCreator playbookCreator;

    public Look(PlaybookType playbookType, LookType category, String look) {
        this.playbookType = playbookType;
        this.category = category;
        this.look = look;
    }

    public Look(String id, PlaybookType playbookType, LookType category, String look) {
        this.id = id;
        this.playbookType = playbookType;
        this.category = category;
        this.look = look;
    }
}
