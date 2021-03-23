package com.mersiades.awcdata.models.uniques;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.mersiades.awccontent.models.Move;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AngelKit {

    @Id
    private String id;

    @Builder.Default
    private String description = "Your angel kit has all kinds of crap in it: scissors, rags, tape, needles, clamps, gloves, chill coils, wipes, alcohol, injectable tourniquets & bloodslower, instant blood packets (coffee reddener), tubes of meatmesh, bonepins & site injectors, biostabs, chemostabs, narcostabs (chillstabs) in quantity, and a roll of heart jumpshock patches for when it comes to that. It’s big enough to fill the trunk of a car.\n" +
            "\n" +
            "When you use it, spend its stock; you can spend 0–3 of its stock per use.\n" +
            "\n" +
            "You can resupply it for 1-barter per 2-stock, if your circumstances let you barter for medical supplies.";

    private int stock;

    @Builder.Default
    private List<Move> angelKitMoves = new ArrayList<>();

    @Builder.Default
    private boolean hasSupplier = false;

    @Builder.Default
    private String supplierText = "_**You have a supplier.**_\n" +
            "At the beginning of every session, gain 1-stock, to a maximum of 6-stock.";
}
