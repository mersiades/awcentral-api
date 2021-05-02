package com.mersiades.awcdata.models.uniques;

import com.mersiades.awccontent.enums.UniqueType;
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
public class BrainerGear {

    @Id
    private String id;

    @Builder.Default
    private UniqueType uniqueType = UniqueType.BRAINER_GEAR;

    @Builder.Default
    private List<String> brainerGear = new ArrayList<>();
}
