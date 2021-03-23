package com.mersiades.awccontent.services.impl;

import com.mersiades.awccontent.enums.PlaybookType;
import com.mersiades.awccontent.models.Look;
import com.mersiades.awccontent.repositories.LookRepository;
import com.mersiades.awccontent.services.LookService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LookServiceImpl implements LookService {

    private final LookRepository lookRepository;

    public LookServiceImpl(LookRepository lookRepository) {
        this.lookRepository = lookRepository;
    }

    @Override
    public List<Look> findAll() {
        return lookRepository.findAll();
    }

    @Override
    public Look findById(String id) {
        return lookRepository.findById(id).orElseThrow();
    }

    @Override
    public Look save(Look look) {
        return lookRepository.save(look);
    }

    @Override
    public List<Look> saveAll(List<Look> looks) {
        return lookRepository.saveAll(looks);
    }

    @Override
    public void delete(Look look) {
        this.lookRepository.delete(look);
    }

    @Override
    public void deleteById(String id) {
        this.lookRepository.deleteById(id);
    }

    @Override
    public List<Look> findAllByPlaybookType(PlaybookType playbookType) {
        return lookRepository.findAllByPlaybookType(playbookType);
    }
}
