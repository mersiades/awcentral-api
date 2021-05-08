package com.mersiades.awccontent.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static com.mersiades.awccontent.content.StatOptionsContent.*;

@Disabled
@ExtendWith({SpringExtension.class})
//@SpringBootApplication
public class StatsOptionRepositoryTest {

    @Autowired
    StatsOptionRepository statsOptionRepository;

    @BeforeEach
    public void setup() {
        statsOptionRepository.deleteAll();

        statsOptionRepository.saveAll(List.of(statsOptionBattlebabe1, statsOptionBattlebabe2, statsOptionBrainer1, statsOptionBrainer2));
//                .thenMany(Flux.just(battlebabe1, battlebabe2, brainer1, brainer2))
//                .flatMap(statsOptionRepository::save)
//                .blockLast();
    }

//    @Test
//    public void shouldFindAllStatsOptionsForPlaybookType() {
//        StepVerifier.create(statsOptionRepository.findAllByPlaybookType(PlaybookType.BRAINER))
//                .expectSubscription()
//                .expectNextMatches(statsOption -> statsOption.getPlaybookType().equals(PlaybookType.BRAINER))
//                .expectNextMatches(statsOption -> statsOption.getPlaybookType().equals(PlaybookType.BRAINER))
//                .verifyComplete();
//    }
}