package com.mersiades.awccontent.models.uniquecreators;

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
public class WorkspaceCreator {

    @Id
    private String id;

    private int itemsCount;

    private String workspaceInstructions;

    private String projectInstructions;

    @Builder.Default
    List<String> workspaceItems = new ArrayList<>();
}
