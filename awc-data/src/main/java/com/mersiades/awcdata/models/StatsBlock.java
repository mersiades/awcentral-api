package com.mersiades.awcdata.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;
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
}
