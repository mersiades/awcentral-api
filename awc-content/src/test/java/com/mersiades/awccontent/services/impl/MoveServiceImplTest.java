package com.mersiades.awccontent.services.impl;

import com.mersiades.awccontent.enums.MoveType;
import com.mersiades.awccontent.enums.PlaybookType;
import com.mersiades.awccontent.models.Move;
import com.mersiades.awccontent.repositories.MoveRepository;
import com.mersiades.awccontent.services.MoveService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

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
        when(moveRepository.findAll()).thenReturn(List.of(mockMove1, mockMove2));

        // When
        List<Move> returnedMoves = moveService.findAll();

        // Then
        assert returnedMoves != null;
        assertEquals(2, returnedMoves.size());
        verify(moveRepository, times(1)).findAll();
    }

    @Test
    void shouldFindMoveById() {
        // Given
        when(moveRepository.findById(anyString())).thenReturn(Optional.of(mockMove1));

        // When
        Move returnedMove = moveService.findById(MOCK_MOVE_ID_1);

        // Then
        assert returnedMove != null;
        assertEquals(MOCK_MOVE_ID_1, returnedMove.getId());
        verify(moveRepository, times(1)).findById(anyString());
    }

    @Test
    void shouldSaveMove() {
        // Given
        when(moveRepository.save(any(Move.class))).thenReturn(mockMove1);

        // When
        Move savedMove = moveService.save(mockMove1);

        // Then
        assert savedMove != null;
        assertEquals(MOCK_MOVE_ID_1, savedMove.getId());
        verify(moveRepository, times(1)).save(any(Move.class));
    }

    @Test
    void shouldSaveAllMoves() {
        // Given
        Move mockMove2 = Move.builder().build();
        when(moveRepository.saveAll(anyIterable())).thenReturn(List.of(mockMove1, mockMove2));

        // When
        List<Move> savedMoves = moveService.saveAll(List.of(mockMove1,mockMove2));

        // Then
        assert savedMoves != null;
        assertEquals(2, savedMoves.size());
        verify(moveRepository, times(1)).saveAll(anyIterable());
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

    @Test
    void shouldFindAllMovesByPlaybookAndKind() {
        // Given
        Move iceCold = Move.builder().name("ICE COLD").description("_**Ice cold**_: when ...").kind(MoveType.CHARACTER).playbook(PlaybookType.BATTLEBABE).build();
        Move merciless = Move.builder().name("MERCILESS").description("_**Merciless**_: when ...").kind(MoveType.CHARACTER).playbook(PlaybookType.BATTLEBABE).build();
        when(moveRepository.findAllByPlaybookAndKind(PlaybookType.BATTLEBABE, MoveType.CHARACTER)).thenReturn(List.of(iceCold, merciless));

        // When
        List<Move> returnedMoves = moveService.findAllByPlaybookAndKind(PlaybookType.BATTLEBABE, MoveType.CHARACTER);

        // Then
        assert returnedMoves != null;
        returnedMoves.forEach(move -> {
            assertEquals(move.getPlaybook(), PlaybookType.BATTLEBABE);
            assertEquals(move.getKind(), MoveType.CHARACTER);
        });
        verify(moveRepository, times(1)).findAllByPlaybookAndKind(PlaybookType.BATTLEBABE, MoveType.CHARACTER);
    }

    @Test
    void shouldFindMoveByName() {
        // Given
        when(moveRepository.findByName(anyString())).thenReturn(mockMove1);

        // When
        Move returnedMove = moveService.findByName(mockMove1.getName());

        // Then
        assert returnedMove != null;
        assertEquals(returnedMove.getName(), mockMove1.getName());
        verify(moveRepository, times(1)).findByName(mockMove1.getName());
    }
}