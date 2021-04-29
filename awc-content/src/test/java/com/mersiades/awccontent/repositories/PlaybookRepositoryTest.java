package com.mersiades.awccontent.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static com.mersiades.awccontent.content.PlaybooksContent.angel;
import static com.mersiades.awccontent.content.PlaybooksContent.battlebabe;

@Disabled
@ExtendWith({SpringExtension.class})
//@SpringBootApplication
public class PlaybookRepositoryTest {

    @Autowired
    private PlaybookRepository playbookRepository;

    @BeforeEach
    public void setup() {

        playbookRepository.deleteAll();

        playbookRepository.saveAll(List.of(angel, battlebabe));
//                .thenMany(Flux.just(angel, battlebabe))
//                .flatMap(playbookRepository::save)
//                .blockLast();
    }

//    @Test
//    public void shouldFindPlaybookByPlaybookType() {
//        StepVerifier.create(playbookRepository.findByPlaybookType(PlaybookType.BATTLEBABE))
//                .expectSubscription()
//                .expectNextMatches(playbook -> playbook.getPlaybookType().equals(PlaybookType.BATTLEBABE))
//                .verifyComplete();
//    }
}