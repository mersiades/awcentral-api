package com.mersiades.awcdata.services.jpa;

import com.mersiades.awcdata.enums.Playbooks;
import com.mersiades.awcdata.models.Name;
import com.mersiades.awcdata.repositories.NameRepository;
import com.mersiades.awcdata.services.NameService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Profile("jpa")
public class NameJpaService implements NameService {

    private final NameRepository nameRepository;

    public NameJpaService(NameRepository nameRepository) {
        this.nameRepository = nameRepository;
    }

    @Override
    public Set<Name> findAll() {
        Set<Name> names = new HashSet<>();
        nameRepository.findAll().forEach(names::add);
        return names;
    }

    @Override
    public Name findById(Long id) {
        Optional<Name> optionalName = nameRepository.findById(id);
        return optionalName.orElse(null);
    }

    @Override
    public Name save(Name name) {
        return nameRepository.save(name);
    }

    @Override
    public void delete(Name name) {
        nameRepository.delete(name);
    }

    @Override
    public void deleteById(Long id) {
        nameRepository.deleteById(id);
    }

    @Override
    public Set<Name> findAllByPlaybookType(Playbooks playbookType) {
        return nameRepository.findAllByPlaybookType(playbookType);
    }








}
