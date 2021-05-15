package com.mersiades.awcdata.models;

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
public class Ally {

    @Id
    private String id;

    private String name;

    private AllyType allyType;

    private String impulse;

    private String description;
}
