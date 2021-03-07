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

}
