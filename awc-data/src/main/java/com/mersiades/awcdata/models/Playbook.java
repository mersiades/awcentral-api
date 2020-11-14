package com.mersiades.awcdata.models;

import com.mersiades.awcdata.enums.Playbooks;
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
public class Playbook  {

    @Id
    private String id;

    // enum type string
    private Playbooks playbookType;

    // long string
    private String barterInstructions;

    // long string
    private String intro;

    // long string
    private String introComment;

    private String playbookImageUrl;

    // one to one
    private PlaybookCreator creator;

    public Playbook(Playbooks playbookType, String barterInstructions, String intro, String introComment, String playbookImageUrl) {
        this.playbookType = playbookType;
        this.barterInstructions = barterInstructions;
        this.intro = intro;
        this.introComment = introComment;
        this.playbookImageUrl = playbookImageUrl;
    }
}
