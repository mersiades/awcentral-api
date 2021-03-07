package com.mersiades.awccontent.repositories;

import com.mersiades.awccontent.enums.PlaybookType;
import com.mersiades.awccontent.models.Playbook;
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
public class PlaybookRepositoryTest {

    @Autowired
    private PlaybookRepository playbookRepository;

    @BeforeEach
    public void setup() {
        Playbook angel = Playbook.builder()
                .playbookType(PlaybookType.ANGEL)
                .barterInstructions("At the beginning ....")
                .intro("When you’re lying ...")
                .introComment("Angels are medics. ...")
                .playbookImageUrl("https://awc-images...").build();

        Playbook battlebabe = Playbook.builder()
                .playbookType(PlaybookType.BATTLEBABE)
                .barterInstructions("At the beginning ....")
                .intro("Even in a place ...")
                .introComment("Dangerous...")
                .playbookImageUrl("https://awc-images...").build();

        playbookRepository.deleteAll()
                .thenMany(Flux.just(angel, battlebabe))
                .flatMap(playbookRepository::save)
                .blockLast();
    }

    @Test
    public void shouldFindPlaybookByPlaybookType() {
        StepVerifier.create(playbookRepository.findByPlaybookType(PlaybookType.BATTLEBABE))
                .expectSubscription()
                .expectNextMatches(playbook -> playbook.getPlaybookType().equals(PlaybookType.BATTLEBABE))
                .verifyComplete();
    }
}