package com.mersiades.awcdata.models.uniques;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomWeapons {

    @Id
    private String id;

    @Builder.Default
    private List<String> weapons = new ArrayList<>();
}
