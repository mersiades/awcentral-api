package com.mersiades.awcdata.models;

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
public class TaggedItem {

    @Id
    private String id;

    private String description;

    @Builder.Default
    private List<String> tags = new ArrayList<>();
}
