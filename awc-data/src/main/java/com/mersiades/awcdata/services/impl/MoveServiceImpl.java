package com.mersiades.awcdata.services.impl;

import com.mersiades.awcdata.models.Move;
import com.mersiades.awcdata.repositories.MoveReactiveRepository;
import com.mersiades.awcdata.services.MoveService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Profile("jpa")
public class MoveServiceImpl implements MoveService {

    private final MoveReactiveRepository moveRepository;

    public MoveServiceImpl(MoveReactiveRepository moveRepository) {
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
}
