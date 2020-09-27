package com.mersiades.awcdata.services.map;

import com.mersiades.awcdata.models.Stat;
import com.mersiades.awcdata.services.StatService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Profile({ "default", "map"})
public class StatMapService extends AbstractMapService<Stat, Long> implements StatService {
    @Override
    public Set<Stat> findAll() {
        return super.findAll();
    }

    @Override
    public Stat findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Stat save(Stat stat) {
        return super.save(stat);
    }

    @Override
    public void delete(Stat stat) {
        super.delete(stat);
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }




}
