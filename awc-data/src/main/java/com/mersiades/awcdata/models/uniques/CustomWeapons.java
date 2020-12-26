package com.mersiades.awcdata.models.uniques;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomWeapons {

    @Id
    private String id;

    // TODO: Add in the rest
}
