package com.mersiades.awcdata.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Hold {

    @Id
    private String id;

    private String moveName;

    private String moveDescription;

    private int rollResult;
}
