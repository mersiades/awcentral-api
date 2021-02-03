package com.mersiades.awccontent.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GangOption {

    @Id
    private String id;

    private String description;

    private String modifier;

    // For example, +rich, -savage, +Vulnerable: disease
    private String tag;
}
