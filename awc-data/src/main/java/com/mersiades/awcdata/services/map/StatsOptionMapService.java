package com.mersiades.awcdata.services.map;

import com.mersiades.awcdata.models.StatsOption;
import com.mersiades.awcdata.services.StatsOptionService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Profile({ "default", "map"})
public class StatsOptionMapService extends AbstractMapService<StatsOption, Long> implements StatsOptionService {
    @Override
    public Set<StatsOption> findAll() {
        return super.findAll();
    }

    @Override
    public StatsOption findById(Long id) {
        return super.findById(id);
    }

    @Override
    public StatsOption save(StatsOption statsOption) {
        return super.save(statsOption);
    }

    @Override
    public void delete(StatsOption statsOption) {
        super.delete(statsOption);
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }
}
