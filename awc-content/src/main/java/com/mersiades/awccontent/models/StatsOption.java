package com.mersiades.awccontent.models;

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
public class StatsOption {

    @Id
    private String id;

    private PlaybookType playbookType;

    private int COOL;

    private int HARD;

    private int HOT;

    private int SHARP;

    private int WEIRD;

    private PlaybookCreator playbookCreator;
}
