package com.mersiades.awcdata.services.jpa;

import com.mersiades.awcdata.models.Stat;
import com.mersiades.awcdata.repositories.StatRepository;
import com.mersiades.awcdata.services.StatService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Profile("jpa")
public class StatJpaService implements StatService {

    private final StatRepository statRepository;

    public StatJpaService(StatRepository statRepository) {
        this.statRepository = statRepository;
    }

    @Override
    public Set<Stat> findAll() {
        Set<Stat> stats = new HashSet<>();
        statRepository.findAll().forEach(stats::add);
        return stats;
    }

    @Override
    public Stat findById(Long id) {
        Optional<Stat> optionalStat = statRepository.findById(id);
        return optionalStat.orElse(null);
    }

    @Override
    public Stat save(Stat stat) {
        return statRepository.save(stat);
    }

    @Override
    public void delete(Stat stat) {
        statRepository.delete(stat);
    }

    @Override
    public void deleteById(Long id) {
        statRepository.deleteById(id);
    }
}
