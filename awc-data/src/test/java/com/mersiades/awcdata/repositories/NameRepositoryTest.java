package com.mersiades.awcdata.repositories;

import com.mersiades.awcdata.enums.Playbooks;
import com.mersiades.awcdata.models.Name;
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
public class NameRepositoryTest {

    @Autowired
    private NameRepository nameRepository;

    @BeforeEach
    public void setup() {
        Name dou = new Name(Playbooks.ANGEL, "Dou");
        Name bon = new Name(Playbooks.ANGEL, "Bon");
        Name snow = Name.builder().playbookType(Playbooks.BATTLEBABE).name("Snow").build();
        Name crimson = Name.builder().playbookType(Playbooks.BATTLEBABE).name("Crimson").build();
        Name smith2 = Name.builder().playbookType(Playbooks.BRAINER).name("Smith").build();
        Name jones = Name.builder().playbookType(Playbooks.BRAINER).name("Jones").build();

        nameRepository.deleteAll()
                .thenMany(Flux.just(dou, bon, snow, crimson, smith2, jones))
                .flatMap(nameRepository::save)
                .doOnNext(System.out::println)
                .blockLast();
    }

    @Test
    public void shouldFindAllNameForAPlaybook() {
        StepVerifier.create(nameRepository.findAllByPlaybookType(Playbooks.BATTLEBABE))
                .expectSubscription()
                .expectNextCount(2)
                .verifyComplete();
    }
}