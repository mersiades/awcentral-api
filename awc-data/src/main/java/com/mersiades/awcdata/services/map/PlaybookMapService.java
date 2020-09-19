package com.mersiades.awcdata.services.map;

import com.mersiades.awcdata.enums.Playbooks;
import com.mersiades.awcdata.models.Playbook;
import com.mersiades.awcdata.services.PlaybookService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Profile({ "default", "map"})
public class PlaybookMapService extends AbstractMapService<Playbook, Long> implements PlaybookService {
    @Override
    public Set<Playbook> findAll() {
        return super.findAll();
    }

    @Override
    public Playbook findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Playbook save(Playbook playbook) {
        return super.save(playbook);
    }

    @Override
    public void delete(Playbook playbook) {
        super.delete(playbook);
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public Playbook findByPlaybookType(Playbooks playbookType) {
        return this.findAll()
                .stream()
                .filter(playbook -> playbook.getPlaybookType().equals(playbookType))
                .findFirst()
                .orElse(null);
    }
}
