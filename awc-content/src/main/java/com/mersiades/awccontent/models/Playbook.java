package com.mersiades.awccontent.models;

import com.mersiades.awccontent.enums.PlaybookType;
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
    private PlaybookType playbookType;

    // long string
    private String barterInstructions;

    // long string
    private String intro;

    // long string
    private String introComment;

    private String playbookImageUrl;

    // one to one
    private PlaybookCreator creator;

    public Playbook(PlaybookType playbookType, String barterInstructions, String intro, String introComment, String playbookImageUrl) {
        this.playbookType = playbookType;
        this.barterInstructions = barterInstructions;
        this.intro = intro;
        this.introComment = introComment;
        this.playbookImageUrl = playbookImageUrl;
    }
}
