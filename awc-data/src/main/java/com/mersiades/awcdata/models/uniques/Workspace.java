package com.mersiades.awcdata.models.uniques;

import com.mersiades.awcdata.models.Project;
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
public class Workspace {

    @Id
    private String id;

    private String workspaceInstructions;

    private String projectInstructions;

    @Builder.Default
    List<String> workspaceItems = new ArrayList<>();

    @Builder.Default
    List<Project> projects = new ArrayList<>();
}
