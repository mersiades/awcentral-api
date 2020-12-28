package com.mersiades.awcdata.models;

import com.mersiades.awcdata.enums.Stats;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatsBlock {
     @Id
     private String id;

     @Builder.Default
     private List<CharacterStat> stats = new ArrayList<>();

     public Optional<CharacterStat> getCharacterStatByStat(Stats stat) {
          return this.stats.stream().filter(stat1 -> stat1.getStat().equals(stat)).findFirst();
     }
}
