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
        Playbook angel = new Playbook(PlaybookType.ANGEL, "At the beginning ....",
                "When you’re lying ...",
                "Angels are medics. ...",
                "https://awc-images...");

        Playbook battlebabe = new Playbook(PlaybookType.BATTLEBABE, "At the beginning ...",
                "Even in a place ...",
                "Dangerous...",
                "https://awc-i...");

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