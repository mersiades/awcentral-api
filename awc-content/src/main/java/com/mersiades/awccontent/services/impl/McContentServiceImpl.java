package com.mersiades.awccontent.services.impl;

import com.mersiades.awccontent.models.McContent;
import com.mersiades.awccontent.repositories.McContentRepository;
import com.mersiades.awccontent.services.McContentService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class McContentServiceImpl implements McContentService {

    private final McContentRepository mcContentRepository;

    public McContentServiceImpl(McContentRepository mcContentRepository) {
        this.mcContentRepository = mcContentRepository;
    }

    @Override
    public Flux<McContent> findAll() {
        return mcContentRepository.findAll();
    }

    @Override
    public Mono<McContent> findById(String id) {
        return mcContentRepository.findById(id);
    }

    @Override
    public Mono<McContent> save(McContent content) {
        return mcContentRepository.save(content);
    }

    @Override
    public Flux<McContent> saveAll(Flux<McContent> contents) {
        return mcContentRepository.saveAll(contents);
    }

    @Override
    public void delete(McContent content) {
        mcContentRepository.delete(content);
    }

    @Override
    public void deleteById(String id) {
        mcContentRepository.deleteById(id);
    }
}
