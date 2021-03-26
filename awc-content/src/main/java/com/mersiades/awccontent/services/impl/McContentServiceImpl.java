package com.mersiades.awccontent.services.impl;

import com.mersiades.awccontent.models.McContent;
import com.mersiades.awccontent.repositories.McContentRepository;
import com.mersiades.awccontent.services.McContentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class McContentServiceImpl implements McContentService {

    private final McContentRepository mcContentRepository;

    public McContentServiceImpl(McContentRepository mcContentRepository) {
        this.mcContentRepository = mcContentRepository;
    }

    @Override
    public List<McContent> findAll() {
        return mcContentRepository.findAll();
    }

    @Override
    public McContent findById(String id) {
        return mcContentRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public McContent save(McContent mcContent) {
        return mcContentRepository.save(mcContent);
    }

    @Override
    public List<McContent> saveAll(List<McContent> mcContents) {
        return mcContentRepository.saveAll(mcContents);
    }

    @Override
    public void delete(McContent mcContent) {
        mcContentRepository.delete(mcContent);
    }

    @Override
    public void deleteById(String id) {
        mcContentRepository.deleteById(id);
    }
}
