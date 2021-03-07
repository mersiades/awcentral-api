package com.mersiades.awccontent.repositories;

import com.mersiades.awccontent.enums.LookType;
import com.mersiades.awccontent.enums.PlaybookType;
import com.mersiades.awccontent.models.Look;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;


//@DataMongoTest
@ExtendWith(SpringExtension.class)
@SpringBootApplication
public class LookRepositoryTest {

    @Autowired
    private LookRepository lookRepository;

    @BeforeEach
    public void setup() {
        Look angel1 = new Look(PlaybookType.ANGEL, LookType.GENDER, "man");
        Look angel2 = new Look(PlaybookType.ANGEL, LookType.CLOTHES, "utility wear");
        Look angel3 = new Look(PlaybookType.ANGEL, LookType.FACE, "kind face");
        Look angel4 = new Look(PlaybookType.ANGEL, LookType.EYES, "quick eyes");
        Look angel5 = new Look(PlaybookType.ANGEL, LookType.BODY, "compact body");
        lookRepository.deleteAll()
                .thenMany(Flux.just(angel1, angel2, angel3, angel4, angel5))
                .flatMap(lookRepository::save)
                .doOnNext(System.out::println)
                .blockLast();

    }

    @Test
    public void shouldFindAllLooksForAPlaybookType() {
        StepVerifier.create(lookRepository.findAllByPlaybookType(PlaybookType.ANGEL))
                .expectSubscription()
                .expectNextCount(5)
                .verifyComplete();
    }
}