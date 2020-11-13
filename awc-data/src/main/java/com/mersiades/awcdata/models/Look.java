package com.mersiades.awcdata.models;

import com.mersiades.awcdata.enums.LookCategories;
import com.mersiades.awcdata.enums.Playbooks;
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

    private Playbooks playbookType;

    private LookCategories category;

    private String look;

    private PlaybookCreator playbookCreator;

    public Look(Playbooks playbookType, LookCategories category, String look) {
        this.playbookType = playbookType;
        this.category = category;
        this.look = look;
    }

    public Look(String id, Playbooks playbookType, LookCategories category, String look) {
        this.id = id;
        this.playbookType = playbookType;
        this.category = category;
        this.look = look;
    }
}
