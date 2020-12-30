package com.mersiades.awcdata.repositories;

import com.mersiades.awcdata.enums.Playbooks;
import com.mersiades.awcdata.models.Playbook;
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
public class PlaybookRepositoryTest {

    @Autowired
    private PlaybookRepository playbookRepository;

    @BeforeEach
    public void setup() {
        Playbook angel = new Playbook(Playbooks.ANGEL, "At the beginning ....",
                "When you’re lying ...",
                "Angels are medics. ...",
                "https://awc-images...");

        Playbook battlebabe = new Playbook(Playbooks.BATTLEBABE, "At the beginning ...",
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
        StepVerifier.create(playbookRepository.findByPlaybookType(Playbooks.BATTLEBABE))
                .expectSubscription()
                .expectNextMatches(playbook -> playbook.getPlaybookType().equals(Playbooks.BATTLEBABE))
                .verifyComplete();
    }
}