package com.mersiades.awcdata.services.map;

import com.mersiades.awcdata.enums.Playbooks;
import com.mersiades.awcdata.models.Look;
import com.mersiades.awcdata.services.LookService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Profile({ "default", "map"})
public class LookMapService extends AbstractMapService<Look, Long> implements LookService {


    @Override
    public Set<Look> findAll() {
        return super.findAll();
    }

    @Override
    public Look findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Look save(Look look) {
        return super.save(look);
    }

    @Override
    public void delete(Look look) {
        super.delete(look);
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public Set<Look> findAllByPlaybookType(Playbooks playbookType) {
        return this.findAll()
                .stream()
                .filter(look -> look.getPlaybookType().equals(playbookType))
                .collect(Collectors.toSet());
    }
}
