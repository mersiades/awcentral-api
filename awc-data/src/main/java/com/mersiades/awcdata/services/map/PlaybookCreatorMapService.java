package com.mersiades.awcdata.services.map;

import com.mersiades.awcdata.enums.Playbooks;
import com.mersiades.awcdata.models.PlaybookCreator;
import com.mersiades.awcdata.services.PlaybookCreatorService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Profile({ "default", "map"})
public class PlaybookCreatorMapService extends AbstractMapService<PlaybookCreator, Long> implements PlaybookCreatorService {
    @Override
    public Set<PlaybookCreator> findAll() {
        return super.findAll();
    }

    @Override
    public PlaybookCreator findById(Long id) {
        return super.findById(id);
    }

    @Override
    public PlaybookCreator save(PlaybookCreator playbookCreator) {
        return super.save(playbookCreator);
    }

    @Override
    public void delete(PlaybookCreator playbookCreator) {
        super.delete(playbookCreator);
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public PlaybookCreator findByPlaybookType(Playbooks playbookType) {
        return this.findAll()
                .stream()
                .filter(playbookCreator -> playbookCreator.getPlaybookType().equals(playbookType))
                .findFirst()
                .orElse(null);
    }
}
