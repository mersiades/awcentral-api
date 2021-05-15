package com.mersiades.awcdata.services.impl;

import com.mersiades.awcdata.models.Ally;
import com.mersiades.awcdata.repositories.AllyRepository;
import com.mersiades.awcdata.services.AllyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AllyServiceImpl implements AllyService {

    private final AllyRepository allyRepository;

    public AllyServiceImpl(AllyRepository allyRepository) {
        this.allyRepository = allyRepository;
    }

    @Override
    public List<Ally> findAll() {
        return allyRepository.findAll();
    }

    @Override
    public Ally findById(String id) {
        return allyRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Ally save(Ally ally) {
        return allyRepository.save(ally);
    }

    @Override
    public List<Ally> saveAll(List<Ally> allies) {
        return allyRepository.saveAll(allies);
    }

    @Override
    public void delete(Ally ally) {
        allyRepository.delete(ally);
    }

    @Override
    public void deleteById(String id) {
        allyRepository.deleteById(id);
    }
}
