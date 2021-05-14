package com.mersiades.awccontent.services.impl;

import com.mersiades.awccontent.enums.MoveType;
import com.mersiades.awccontent.enums.PlaybookType;
import com.mersiades.awccontent.models.Move;
import com.mersiades.awccontent.repositories.MoveRepository;
import com.mersiades.awccontent.services.MoveService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MoveServiceImpl implements MoveService {

    private final MoveRepository moveRepository;

    public MoveServiceImpl(MoveRepository moveRepository) {
        this.moveRepository = moveRepository;
    }

    @Override
    public List<Move> findAll() {
        return moveRepository.findAll();
    }

    @Override
    public Move findById(String id) {
        return moveRepository.findById(id).orElseThrow();
    }

    @Override
    public Move save(Move move) {
        return moveRepository.save(move);
    }

    @Override
    public List<Move> saveAll(List<Move> moves) {
        return moveRepository.saveAll(moves);
    }

    @Override
    public void delete(Move move) {
        moveRepository.delete(move);
    }

    @Override
    public void deleteById(String id) {
        moveRepository.deleteById(id);
    }

    @Override
    public List<Move> findAllByPlaybookAndKind(PlaybookType playbookType, MoveType kind) {
        return moveRepository.findAllByPlaybookAndKind(playbookType, kind);
    }

    @Override
    public Move findByName(String moveName) {
        return moveRepository.findByName(moveName);
    }

    @Override
    public List<Move> findOtherPlaybookMoves(PlaybookType playbookType) {
        List<Move> allMoves = this.findAll();

        // Filter out all non-Playbook moves
        List<Move> allCharacterMoves = allMoves.stream()
                .filter(move -> move.getKind().equals(MoveType.CHARACTER)).collect(Collectors.toList());

        // Filter out moves from own playbook
        List<Move> allCharacterMovesFromOtherPlaybooks = allCharacterMoves.stream()
                .filter(move -> !move.getPlaybook().equals(playbookType)).collect(Collectors.toList());

        // Filter out moves with no move action or roll modifier
        return allCharacterMovesFromOtherPlaybooks.stream()
                .filter(move -> move.getMoveAction() != null || move.getRollModifier() != null).collect(Collectors.toList());
    }

    @Override
    public List<Move> findAllById(List<String> moveIds) {
        return (List<Move>) moveRepository.findAllById(moveIds);
    }

    @Override
    public List<Move> findAllByName(List<String> moveNames) {
        List<Move> allMoves = this.findAll();

        List<Move> moves = new ArrayList<>();

        allMoves.forEach(move -> {
            if (moveNames.contains(move.getName())) {
                moves.add(move);
            }
        });

        return moves;
    }

    @Override
    public List<Move> findDeathMoves() {
        return moveRepository.findAllByKind(MoveType.DEATH);
    }
}
