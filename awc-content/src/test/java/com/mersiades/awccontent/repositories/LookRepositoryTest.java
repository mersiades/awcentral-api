package com.mersiades.awccontent.repositories;

import com.mersiades.awccontent.enums.LookCategories;
import com.mersiades.awccontent.enums.Playbooks;
import com.mersiades.awccontent.models.Look;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;


//@DataMongoTest
@Disabled
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