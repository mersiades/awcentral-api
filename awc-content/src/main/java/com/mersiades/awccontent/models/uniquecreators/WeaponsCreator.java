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
public class WeaponsCreator {

    @Id
    private String id;

    private final int bfoGunOptionCount = 1;
    private final int seriousGunOptionCount = 2;
    private final int backupWeaponsOptionCount = 1;

    @Builder.Default
    private List<String> bigFuckOffGuns = new ArrayList<>();

    @Builder.Default
    private List<String> seriousGuns = new ArrayList<>();

    @Builder.Default
    private List<String> backupWeapons = new ArrayList<>();

}
