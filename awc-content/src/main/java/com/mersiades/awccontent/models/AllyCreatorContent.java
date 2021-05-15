package com.mersiades.awccontent.models;

import com.mersiades.awccontent.enums.AllyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllyCreatorContent {

    @Id
    private String id;

    private AllyType allyType;

    private String impulse;
}
