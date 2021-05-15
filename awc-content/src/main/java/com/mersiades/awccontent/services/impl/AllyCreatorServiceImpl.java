package com.mersiades.awccontent.services.impl;

import com.mersiades.awccontent.models.AllyCreator;
import com.mersiades.awccontent.repositories.AllyCreatorRepository;
import com.mersiades.awccontent.services.AllyCreatorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AllyCreatorServiceImpl implements AllyCreatorService {

    private final AllyCreatorRepository allyCreatorRepository;

    public AllyCreatorServiceImpl(AllyCreatorRepository allyCreatorRepository) {
        this.allyCreatorRepository = allyCreatorRepository;
    }

    @Override
    public List<AllyCreator> findAll() {
        return allyCreatorRepository.findAll();
    }

    @Override
    public AllyCreator findById(String id) {
        return allyCreatorRepository.findById(id).orElseThrow();
    }

    @Override
    public AllyCreator save(AllyCreator allyCreator) {
        return allyCreatorRepository.save(allyCreator);
    }

    @Override
    public List<AllyCreator> saveAll(List<AllyCreator> allyCreators) {
        return allyCreatorRepository.saveAll(allyCreators);
    }

    @Override
    public void delete(AllyCreator allyCreator) {
        allyCreatorRepository.delete(allyCreator);
    }

    @Override
    public void deleteById(String id) {
        allyCreatorRepository.deleteById(id);
    }
}
