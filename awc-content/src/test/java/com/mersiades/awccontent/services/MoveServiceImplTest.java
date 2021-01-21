package com.mersiades.awccontent.services;

import com.mersiades.awccontent.enums.MoveType;
import com.mersiades.awccontent.models.Move;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.mersiades.awccontent.repositories.MoveRepository;
import com.mersiades.awccontent.services.impl.MoveServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MoveServiceImplTest {

    public static final String MOCK_MOVE_ID_1 = "mock-move-id-1";

    @Mock
    MoveRepository moveRepository;

    MoveService moveService;

    Move mockMove1;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        moveService = new MoveServiceImpl(moveRepository);

        mockMove1 = Move.builder()
                .id(MOCK_MOVE_ID_1)
                .name("Mock move name")
                .description("Description of a mock move")
                .stat(null)
                .kind(MoveType.BASIC)
                .playbook(null).build();
    }

    @Test
    void shouldFindAllMoves() {
        // Given
        Move mockMove2 = Move.builder().build();
        when(moveRepository.findAll()).thenReturn(Flux.just(mockMove1, mockMove2));

        // When
        List<Move> returnedMoves = moveService.findAll().collectList().block();

        // Then
        assert returnedMoves != null;
        assertEquals(2, returnedMoves.size());
        verify(moveRepository, times(1)).findAll();
    }

    @Test
    void shouldFindMoveById() {
        // Given
        when(moveRepository.findById(anyString())).thenReturn(Mono.just(mockMove1));

        // When
        Move returnedMove = moveService.findById(MOCK_MOVE_ID_1).block();

        // Then
        assert returnedMove != null;
        assertEquals(MOCK_MOVE_ID_1, returnedMove.getId());
        verify(moveRepository, times(1)).findById(anyString());
    }

    @Test
    void shouldSaveMove() {
        // Given
        when(moveRepository.save(any(Move.class))).thenReturn(Mono.just(mockMove1));

        // When
        Move savedMove = moveService.save(mockMove1).block();

        // Then
        assert savedMove != null;
        assertEquals(MOCK_MOVE_ID_1, savedMove.getId());
        verify(moveRepository, times(1)).save(any(Move.class));
    }

    @Test
    void shouldSaveAllMoves() {
        // Given
        Move mockMove2 = Move.builder().build();
        when(moveRepository.saveAll(any(Publisher.class))).thenReturn(Flux.just(mockMove1, mockMove2));

        // When
        List<Move> savedMoves = moveService.saveAll(Flux.just(mockMove1,mockMove2)).collectList().block();

        // Then
        assert savedMoves != null;
        assertEquals(2, savedMoves.size());
        verify(moveRepository, times(1)).saveAll(any(Publisher.class));
    }

    @Test
    void shouldDeleteMove() {
        // When
        moveService.delete(mockMove1);

        // Then
        verify(moveRepository, times(1)).delete(any(Move.class));
    }

    @Test
    void shouldDeleteMoveById() {
        // When
        moveService.deleteById(MOCK_MOVE_ID_1);

        // Then
        verify(moveRepository, times(1)).deleteById(anyString());
    }
}