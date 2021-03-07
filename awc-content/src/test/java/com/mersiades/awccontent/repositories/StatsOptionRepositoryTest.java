package com.mersiades.awccontent.repositories;

import com.mersiades.awccontent.enums.PlaybookType;
import com.mersiades.awccontent.models.StatsOption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@ExtendWith({SpringExtension.class})
@SpringBootApplication
public class StatsOptionRepositoryTest {

    @Autowired
    StatsOptionRepository statsOptionRepository;

    @BeforeEach
    public void setup() {
        StatsOption battlebabe1 = StatsOption.builder().playbookType(PlaybookType.BATTLEBABE).COOL(3).HARD(-1).HOT(1).SHARP(1).WEIRD(0).build();
        StatsOption battlebabe2 = StatsOption.builder().playbookType(PlaybookType.BATTLEBABE).COOL(3).HARD(-1).HOT(2).SHARP(0).WEIRD(-1).build();
        StatsOption brainer1 = StatsOption.builder().playbookType(PlaybookType.BRAINER).COOL(1).HARD(1).HOT(-2).SHARP(1).WEIRD(2).build();
        StatsOption brainer2 = StatsOption.builder().playbookType(PlaybookType.BRAINER).COOL(0).HARD(0).HOT(1).SHARP(0).WEIRD(2).build();

        statsOptionRepository.deleteAll()
                .thenMany(Flux.just(battlebabe1, battlebabe2, brainer1, brainer2))
                .flatMap(statsOptionRepository::save)
                .blockLast();
    }

    @Test
    public void shouldFindAllStatsOptionsForPlaybookType() {
        StepVerifier.create(statsOptionRepository.findAllByPlaybookType(PlaybookType.BRAINER))
                .expectSubscription()
                .expectNextMatches(statsOption -> statsOption.getPlaybookType().equals(PlaybookType.BRAINER))
                .expectNextMatches(statsOption -> statsOption.getPlaybookType().equals(PlaybookType.BRAINER))
                .verifyComplete();
    }
}