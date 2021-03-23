package com.mersiades.awccontent.services.impl;

import com.mersiades.awccontent.enums.PlaybookType;
import com.mersiades.awccontent.models.Name;
import com.mersiades.awccontent.repositories.NameRepository;
import com.mersiades.awccontent.services.NameService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NameServiceImpl implements NameService {

    private final NameRepository nameRepository;

    public NameServiceImpl(NameRepository nameRepository) {
        this.nameRepository = nameRepository;
    }

    @Override
    public List<Name> findAll() {
        return nameRepository.findAll();
    }

    @Override
    public Name findById(String id) {
        return nameRepository.findById(id).orElseThrow();
    }

    @Override
    public Name save(Name name) {
        return nameRepository.save(name);
    }

    @Override
    public List<Name> saveAll(List<Name> moves) {
        return nameRepository.saveAll(moves);
    }

    @Override
    public void delete(Name name) {
        nameRepository.delete(name);
    }

    @Override
    public void deleteById(String id) {
        nameRepository.deleteById(id);
    }

    @Override
    public List<Name> findAllByPlaybookType(PlaybookType playbookType) {
        return nameRepository.findAllByPlaybookType(playbookType);
    }
}
