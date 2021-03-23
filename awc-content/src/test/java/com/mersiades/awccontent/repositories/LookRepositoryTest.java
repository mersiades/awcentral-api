package com.mersiades.awccontent.repositories;

import com.mersiades.awccontent.enums.LookType;
import com.mersiades.awccontent.enums.PlaybookType;
import com.mersiades.awccontent.models.Look;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@Disabled
@ExtendWith(SpringExtension.class)
@DataMongoTest
public class LookRepositoryTest {

    @Autowired
    private LookRepository lookRepository;

    @BeforeEach
    public void setup() {
        Look angel1 = Look.builder()
                .playbookType(PlaybookType.ANGEL)
                .category(LookType.GENDER)
                .look("man").build();

        Look angel2 = Look.builder()
                .playbookType(PlaybookType.ANGEL)
                .category(LookType.CLOTHES)
                .look("utility wear").build();

        Look angel3 = Look.builder()
                .playbookType(PlaybookType.ANGEL)
                .category(LookType.FACE)
                .look("kind face").build();

        Look angel4 = Look.builder()
                .playbookType(PlaybookType.ANGEL)
                .category(LookType.EYES)
                .look("quick eyes").build();

        Look angel5 = Look.builder()
                .playbookType(PlaybookType.ANGEL)
                .category(LookType.BODY)
                .look("compact body").build();

        lookRepository.deleteAll();

        lookRepository.saveAll(List.of(angel1, angel2, angel3, angel4, angel5));
//                .thenMany(Flux.just(angel1, angel2, angel3, angel4, angel5))
//                .flatMap(lookRepository::save)
//                .doOnNext(System.out::println)
//                .blockLast();

    }

//    @Test
//    public void shouldFindAllLooksForAPlaybookType() {
//        List<Look> returnedLooks = lookRepository.findAllByPlaybookType(PlaybookType.ANGEL);
//        System.out.println("returnedLooks = " + returnedLooks);
//
////        StepVerifier.create(lookRepository.findAllByPlaybookType(PlaybookType.ANGEL))
////                .expectSubscription()
////                .expectNextCount(5)
////                .verifyComplete();
//    }
}