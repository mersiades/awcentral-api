package com.mersiades.awccontent.repositories;

import com.mersiades.awccontent.enums.PlaybookType;
import com.mersiades.awccontent.models.Name;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@Disabled
@ExtendWith({SpringExtension.class})
//@SpringBootApplication
public class NameRepositoryTest {

    @Autowired
    private NameRepository nameRepository;

    @BeforeEach
    public void setup() {
        Name dou = Name.builder().playbookType(PlaybookType.ANGEL).name("Dou").build();
        Name bon = Name.builder().playbookType(PlaybookType.ANGEL).name("Bon").build();
        Name snow = Name.builder().playbookType(PlaybookType.BATTLEBABE).name("Snow").build();
        Name crimson = Name.builder().playbookType(PlaybookType.BATTLEBABE).name("Crimson").build();
        Name smith2 = Name.builder().playbookType(PlaybookType.BRAINER).name("Smith").build();
        Name jones = Name.builder().playbookType(PlaybookType.BRAINER).name("Jones").build();

        nameRepository.deleteAll();

        nameRepository.saveAll(List.of(dou, bon, snow, crimson, smith2, jones));

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