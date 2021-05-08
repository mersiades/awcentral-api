package com.mersiades.awccontent.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static com.mersiades.awccontent.content.NamesContent.*;

@Disabled
@ExtendWith({SpringExtension.class})
//@SpringBootApplication
public class NameRepositoryTest {

    @Autowired
    private NameRepository nameRepository;

    @BeforeEach
    public void setup() {
        nameRepository.deleteAll();

        nameRepository.saveAll(List.of(nameAngel1, nameAngel2, nameBattlebabe1, nameBattlebabe2, nameBrainer1, nameBrainer2));

//                .thenMany(Flux.just(dou, bon, snow, crimson, smith2, jones))
//                .flatMap(nameRepository::save)
//                .doOnNext(System.out::println)
//                .blockLast();
    }

//    @Test
//    public void shouldFindAllNameForAPlaybook() {
//        StepVerifier.create(nameRepository.findAllByPlaybookType(PlaybookType.BATTLEBABE))
//                .expectSubscription()
//                .expectNextCount(2)
//                .verifyComplete();
//    }
}