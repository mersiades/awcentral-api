package com.mersiades.awcdata.models;

import com.mersiades.awcdata.enums.UniqueType;
import com.mersiades.awcdata.models.uniques.AngelKit;
import com.mersiades.awcdata.models.uniques.BrainerGear;
import com.mersiades.awcdata.models.uniques.CustomWeapons;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaybookUnique {

    @Id
    private String id;

    private UniqueType type;

    private AngelKit angelKit;

    private CustomWeapons customWeapons;

    private BrainerGear brainerGear;
}
