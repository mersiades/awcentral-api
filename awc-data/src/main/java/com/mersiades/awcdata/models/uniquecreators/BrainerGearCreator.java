package com.mersiades.awcdata.models.uniquecreators;

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
public class BrainerGearCreator {

    @Id
    private String id;

    @Builder.Default
    private List<String> gear = new ArrayList<>();
}
