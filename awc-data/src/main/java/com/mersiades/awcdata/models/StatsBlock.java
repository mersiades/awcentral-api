package com.mersiades.awcdata.models;

import com.mersiades.awcdata.enums.Stats;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class StatsBlock {
     @Id
     private String id;

     private List<CharacterStat> stats = new ArrayList<>();

     public StatsBlock() {
          this.id = UUID.randomUUID().toString();
     }

     public Optional<CharacterStat> getCharacterStatbyStat(Stats stat) {
          return this.stats.stream().filter(stat1 -> stat1.getStat().equals(stat)).findFirst();
     }
}
