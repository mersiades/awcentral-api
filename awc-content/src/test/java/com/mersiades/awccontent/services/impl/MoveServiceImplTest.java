package com.mersiades.awccontent.services.impl;

import com.mersiades.awccontent.enums.MoveType;
import com.mersiades.awccontent.enums.PlaybookType;
import com.mersiades.awccontent.models.Move;
import com.mersiades.awccontent.models.MoveAction;
import com.mersiades.awccontent.models.RollModifier;
import com.mersiades.awccontent.repositories.MoveRepository;
import com.mersiades.awccontent.services.MoveService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static com.mersiades.awccontent.constants.MoveNames.angelSpecialName;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MoveServiceImplTest {

    public static final String MOCK_MOVE_ID_1 = "mock-move-id-1";

    @Mock
    MoveRepository moveRepository;

    MoveService moveService;

    Move mockBasicMove1;

    Move mockIceColdMove; // Battlebabe
    Move mockMercilessMove; // Battlebabe
    Move mockAngelSpecialMove; // Angel, is default move
    Move mockHealingTouchMove; // Angel, has move action
    Move mockBrainReceptivity; // Brainer, has roll modifier
    Move mockBrainAttunement; // Brainer, has no move action or roll modifier


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        moveService = new MoveServiceImpl(moveRepository);

        mockBasicMove1 = Move.builder()
                .id(MOCK_MOVE_ID_1)
                .name("Mock move name")
                .description("Description of a mock move")
                .stat(null)
                .kind(MoveType.BASIC)
                .playbook(null).build();

        mockIceColdMove = Move.builder().name("ICE COLD").description("_**Ice cold**_: when ...")
                .kind(MoveType.CHARACTER).playbook(PlaybookType.BATTLEBABE).build();
        mockMercilessMove = Move.builder().name("MERCILESS").description("_**Merciless**_: when ...")
                .kind(MoveType.CHARACTER).playbook(PlaybookType.BATTLEBABE).build();
        mockAngelSpecialMove = Move.builder().name(angelSpecialName).description("If you and another character have sex, ...")
                .kind(MoveType.DEFAULT_CHARACTER).playbook(PlaybookType.ANGEL).moveAction(MoveAction.builder().build()).build();
        mockHealingTouchMove = Move.builder().name("HEALING TOUCH").description("_**Healing touch**_: when you put your hands...")
                .kind(MoveType.CHARACTER).playbook(PlaybookType.ANGEL).moveAction(MoveAction.builder().build()).build();
        mockBrainReceptivity = Move.builder().name("CASUAL BRAIN RECEPTIVITY").description("_**Casual brain receptivity**_: when you read someone...")
                .kind(MoveType.CHARACTER).playbook(PlaybookType.BRAINER).rollModifier(RollModifier.builder().build()).build();
        mockBrainAttunement = Move.builder().name("PRETERNATURAL BRAIN ATTUNEMENT").description("_**Preternatural at-will brain attunement**_: you get...")
                .kind(MoveType.CHARACTER).playbook(PlaybookType.BRAINER).build();
    }

    @Test
    void shouldFindAllMoves() {
        // Given
        Move mockMove2 = Move.builder().build();
        when(moveRepository.findAll()).thenReturn(List.of(mockBasicMove1, mockMove2));

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
        when(moveRepository.findById(anyString())).thenReturn(Optional.of(mockBasicMove1));

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
        when(moveRepository.save(any(Move.class))).thenReturn(mockBasicMove1);

        // When
        Move savedMove = moveService.save(mockBasicMove1);

        // Then
        assert savedMove != null;
        assertEquals(MOCK_MOVE_ID_1, savedMove.getId());
        verify(moveRepository, times(1)).save(any(Move.class));
    }

    @Test
    void shouldSaveAllMoves() {
        // Given
        Move mockMove2 = Move.builder().build();
        when(moveRepository.saveAll(anyIterable())).thenReturn(List.of(mockBasicMove1, mockMove2));

        // When
        List<Move> savedMoves = moveService.saveAll(List.of(mockBasicMove1,mockMove2));

        // Then
        assert savedMoves != null;
        assertEquals(2, savedMoves.size());
        verify(moveRepository, times(1)).saveAll(anyIterable());
    }

    @Test
    void shouldDeleteMove() {
        // When
        moveService.delete(mockBasicMove1);

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

        when(moveRepository.findAllByPlaybookAndKind(PlaybookType.BATTLEBABE, MoveType.CHARACTER)).thenReturn(List.of(mockIceColdMove, mockMercilessMove));

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
        when(moveRepository.findByName(anyString())).thenReturn(mockBasicMove1);

        // When
        Move returnedMove = moveService.findByName(mockBasicMove1.getName());

        // Then
        assert returnedMove != null;
        assertEquals(returnedMove.getName(), mockBasicMove1.getName());
        verify(moveRepository, times(1)).findByName(mockBasicMove1.getName());
    }

    @Test
    void shouldReturnAllAppropriateOtherPlaybookMoves() {
        // Given
        when(moveService.findAll()).thenReturn(List.of(mockIceColdMove, mockMercilessMove, mockAngelSpecialMove,
                mockHealingTouchMove, mockBrainReceptivity, mockBrainAttunement));

        // When
        List<Move> returnedMoves = moveService.findOtherPlaybookMoves(PlaybookType.BATTLEBABE);

        // Then
        assert returnedMoves != null;
        assertEquals(2, returnedMoves.size());
        assertFalse(returnedMoves.stream().anyMatch(move -> move.getPlaybook().equals(PlaybookType.BATTLEBABE)));
        assertFalse(returnedMoves.stream().anyMatch(move -> move.getKind().equals(MoveType.DEFAULT_CHARACTER)));
        assertFalse(returnedMoves.stream().anyMatch(move -> move.getMoveAction() == null && move.getRollModifier() == null));
    }
}