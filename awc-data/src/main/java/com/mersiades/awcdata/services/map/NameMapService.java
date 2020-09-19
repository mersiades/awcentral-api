package com.mersiades.awcdata.services.map;

import com.mersiades.awcdata.enums.Playbooks;
import com.mersiades.awcdata.models.Name;
import com.mersiades.awcdata.services.NameService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Profile({ "default", "map"})
public class NameMapService extends AbstractMapService<Name, Long> implements NameService {


    @Override
    public Set<Name> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void delete(Name name) {
        super.delete(name);
    }

    @Override
    public Name save(Name name) {
        return super.save(name);
    }

    @Override
    public Name findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Set<Name> findAllByPlaybookType(Playbooks playbookType) {
        return this.findAll()
                .stream()
                .filter(name -> name.getPlaybookType().equals(playbookType))
                .collect(Collectors.toSet());
    }
}
