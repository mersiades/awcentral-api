package com.mersiades.awccontent.services.impl;


import com.mersiades.awccontent.models.StatModifier;
import com.mersiades.awccontent.repositories.StatModifierRepository;
import com.mersiades.awccontent.services.StatModifierService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatModifierServiceImpl implements StatModifierService {

    private final StatModifierRepository statModifierRepository;

    public StatModifierServiceImpl(StatModifierRepository statModifierRepository) {
        this.statModifierRepository = statModifierRepository;
    }

    @Override
    public List<StatModifier> findAll() {
        return statModifierRepository.findAll();
    }

    @Override
    public StatModifier findById(String id) {
        return statModifierRepository.findById(id).orElseThrow();
    }

    @Override
    public StatModifier save(StatModifier statModifier) {
        return statModifierRepository.save(statModifier);
    }

    @Override
    public List<StatModifier> saveAll(List<StatModifier> statModifierList) {
        return statModifierRepository.saveAll(statModifierList);
    }

    @Override
    public void delete(StatModifier statModifier) {
        statModifierRepository.delete(statModifier);
    }

    @Override
    public void deleteById(String id) {
        statModifierRepository.deleteById(id);
    }
}
