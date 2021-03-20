package com.mersiades.awccontent.models;

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
public class McContent {

    @Id
    private String id;

    @Builder.Default
    private List<ContentItem> core = new ArrayList<>();

    @Builder.Default
    private List<ContentItem> firstSession = new ArrayList<>();

    @Builder.Default
    private List<ContentItem> harm = new ArrayList<>();

    @Builder.Default
    private List<ContentItem> selected = new ArrayList<>();
}
