package com.mersiades.awcdata.repositories;

import com.mersiades.awcdata.enums.Playbooks;
import com.mersiades.awcdata.models.PlaybookCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

//@DataMongoTest
@ExtendWith({SpringExtension.class})
@SpringBootApplication
class PlaybookCreatorRepositoryTest {

    @Autowired
    private PlaybookCreatorRepository playbookCreatorRepository;

    @BeforeEach
    public void setup() {
        PlaybookCreator angelCreator = PlaybookCreator.builder()
                .playbookType(Playbooks.ANGEL)
                .improvementInstructions("Whenever you roll ...")
                .movesInstructions("You get all ...")
                .hxInstructions("Everyone introduces ...")
                .defaultMoveCount(1)
                .moveChoiceCount(2)
                .build();

        PlaybookCreator battlebabePlaybookCreator = PlaybookCreator.builder()
                .playbookType(Playbooks.BATTLEBABE)
                .improvementInstructions("Whenever you roll ...")
                .movesInstructions("You get all...")
                .hxInstructions("Everyone introduces ...")
                .defaultMoveCount(1)
                .moveChoiceCount(2)
                .build();

        playbookCreatorRepository.deleteAll()
                .thenMany(Flux.just(angelCreator, battlebabePlaybookCreator))
                .flatMap(playbookCreatorRepository::save)
                .doOnNext(System.out::println)
                .blockLast();
    }

    @Test
    public void shouldFindPlaybookCreatorForAPlaybook() {
        StepVerifier.create(playbookCreatorRepository.findByPlaybookType(Playbooks.ANGEL))
                .expectSubscription()
                .expectNextMatches(playbookCreator -> playbookCreator.getPlaybookType().equals(Playbooks.ANGEL))
                .verifyComplete();
    }
}