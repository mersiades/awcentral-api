package com.mersiades.awcdata.models.uniques;

import com.mersiades.awccontent.enums.GangSize;
import com.mersiades.awccontent.models.GangOption;
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
public class Gang {

    @Id
    private String id;

    private GangSize size;

    private int harm;

    private int armor;

    @Builder.Default
    private List<GangOption> strengths = new ArrayList<>();

    @Builder.Default
    private List<GangOption> weaknesses = new ArrayList<>();

    private List<String> tags = new ArrayList<>();

}
