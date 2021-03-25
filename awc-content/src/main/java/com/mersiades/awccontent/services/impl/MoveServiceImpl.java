package com.mersiades.awccontent.services.impl;

import com.mersiades.awccontent.enums.MoveType;
import com.mersiades.awccontent.enums.PlaybookType;
import com.mersiades.awccontent.models.Move;
import com.mersiades.awccontent.repositories.MoveRepository;
import com.mersiades.awccontent.services.MoveService;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
