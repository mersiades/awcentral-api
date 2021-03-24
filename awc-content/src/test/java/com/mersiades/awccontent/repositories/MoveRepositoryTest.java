package com.mersiades.awccontent.repositories;

import com.mersiades.awccontent.enums.MoveType;
import com.mersiades.awccontent.enums.PlaybookType;
import com.mersiades.awccontent.models.Move;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@Disabled
@ExtendWith({SpringExtension.class})
//@SpringBootApplication
public class MoveRepositoryTest {

    @Autowired
    private MoveRepository moveRepository;

    @BeforeEach
    public void setup() {
        Move iceCold = Move.builder().name("ICE COLD").description("_**Ice cold**_: when ...").kind(MoveType.CHARACTER).playbook(PlaybookType.BATTLEBABE).build();
        Move merciless = Move.builder().name("MERCILESS").description("_**Merciless**_: when ...").kind(MoveType.CHARACTER).playbook(PlaybookType.BATTLEBABE).build();
        Move profCompassion = Move.builder().name("PROFESSIONAL COMPASSION").description("_**Professional compassion**_: you can ...").kind(MoveType.CHARACTER).playbook(PlaybookType.ANGEL).build();
        Move battlefieldGrace = Move.builder().name("BATTLEFIELD GRACE").description("_**Battlefield grace**_: while you ...").kind(MoveType.CHARACTER).playbook(PlaybookType.ANGEL).build();

        moveRepository.deleteAll();
        moveRepository.saveAll(List.of(iceCold,merciless, profCompassion, battlefieldGrace));
//                .thenMany(Flux.just(iceCold,merciless, profCompassion, battlefieldGrace))
//                .flatMap(moveRepository::save)
//                .doOnNext(System.out::println)
//                .blockLast();
    }

//    @Test
//    public void shouldFindAllMovesByPlaybookAndMoveKind() {
//        StepVerifier.create(moveRepository.findAllByPlaybookAndKind(PlaybookType.ANGEL, MoveType.CHARACTER))
//                .expectSubscription()
//                .expectNextCount(2)
//                .verifyComplete();
//    }
//
//    @Test
//    public void shouldFindMoveByMoveName() {
//        StepVerifier.create(moveRepository.findByName("MERCILESS"))
//                .expectSubscription()
//                .expectNextMatches(move -> move.getDescription().equals("_**Merciless**_: when ..."))
//                .verifyComplete();
//    }

}