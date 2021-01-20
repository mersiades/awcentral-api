package com.mersiades.awcdata.services.impl;

import com.mersiades.awcdata.models.GameMessage;
import com.mersiades.awcdata.repositories.GameMessageRepository;
import com.mersiades.awcdata.services.GameMessageService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class GameMessageServiceImpl implements GameMessageService {
    private final GameMessageRepository gameMessageRepository;

    public GameMessageServiceImpl(GameMessageRepository gameMessageRepository) {
        this.gameMessageRepository = gameMessageRepository;
    }

    @Override
    public Flux<GameMessage> findAll() {
        return gameMessageRepository.findAll();
    }

    @Override
    public Mono<GameMessage> findById(String id) {
        return gameMessageRepository.findById(id);
    }

    @Override
    public Mono<GameMessage> save(GameMessage gameMessage) {
        return gameMessageRepository.save(gameMessage);
    }

    @Override
    public Flux<GameMessage> saveAll(Flux<GameMessage> gameMessages) {
        return gameMessageRepository.saveAll(gameMessages);
    }

    @Override
    public void delete(GameMessage gameMessage) {
        gameMessageRepository.delete(gameMessage);
    }

    @Override
    public void deleteById(String id) {
        gameMessageRepository.deleteById(id);
    }
}
