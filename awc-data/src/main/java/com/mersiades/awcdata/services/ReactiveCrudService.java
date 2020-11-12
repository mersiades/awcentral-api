package com.mersiades.awcdata.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReactiveCrudService<T, ID> {
    Flux<T> findAll();

    Mono<T> findById(ID id);

    Mono<T> save(T object);

    Flux<T> saveAll(Flux<T> moves);

    void delete(T object);

    void deleteById(ID id);
}
