package com.mersiades.awcdata.models;

import com.mersiades.awcdata.enums.Threats;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Threat {

    @Id
    private String id;

    private String name;

    private Threats threatKind;

    private String impulse;

    private String description;

    private String stakes;

}
