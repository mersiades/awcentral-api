package com.mersiades.awccontent.services.impl;

import com.mersiades.awccontent.enums.MoveKinds;
import com.mersiades.awccontent.enums.Playbooks;
import com.mersiades.awccontent.models.Move;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.mersiades.awccontent.repositories.MoveRepository;
import com.mersiades.awccontent.services.MoveService;

@Service
public class MoveServiceImpl implements MoveService {

    private final MoveRepository moveRepository;

    public MoveServiceImpl(MoveRepository moveRepository) {
        this.moveRepository = moveRepository;
    }

    @Override
    public Flux<Move> findAll() {
        return moveRepository.findAll();
    }

    @Override
    public Mono<Move> findById(String id) {
        return moveRepository.findById(id);
    }

    @Override
    public Mono<Move> save(Move move) {
        return moveRepository.save(move);
    }

    @Override
    public Flux<Move> saveAll(Flux<Move> moves) {
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
    public Flux<Move> findAllByPlaybookAndKind(Playbooks playbookType, MoveKinds kind) {
        return moveRepository.findAllByPlaybookAndKind(playbookType, kind);
    }
}
