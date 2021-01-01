package com.mersiades.awcdata.models;

import lombok.*;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Npc {

    @Id
    private String id;

    private String name;

    private String description;

}
