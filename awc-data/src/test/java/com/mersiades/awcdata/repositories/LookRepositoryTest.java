package com.mersiades.awcdata.repositories;

import com.mersiades.awcdata.enums.LookCategories;
import com.mersiades.awcdata.enums.Playbooks;
import com.mersiades.awcdata.models.Look;
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
        Look angel1 = new Look(Playbooks.ANGEL, LookCategories.GENDER, "man");
        Look angel2 = new Look(Playbooks.ANGEL, LookCategories.CLOTHES, "utility wear");
        Look angel3 = new Look(Playbooks.ANGEL, LookCategories.FACE, "kind face");
        Look angel4 = new Look(Playbooks.ANGEL, LookCategories.EYES, "quick eyes");
        Look angel5 = new Look(Playbooks.ANGEL, LookCategories.BODY, "compact body");
        lookRepository.deleteAll()
                .thenMany(Flux.just(angel1, angel2, angel3, angel4, angel5))
                .flatMap(lookRepository::save)
                .doOnNext(System.out::println)
                .blockLast();

    }

    @Test
    public void shouldFindAllLooksForAPlaybookType() {
        StepVerifier.create(lookRepository.findAllByPlaybookType(Playbooks.ANGEL))
                .expectSubscription()
                .expectNextCount(5)
                .verifyComplete();
    }
}