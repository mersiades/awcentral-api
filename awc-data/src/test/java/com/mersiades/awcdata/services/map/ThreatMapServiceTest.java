package com.mersiades.awcdata.services.map;

import com.mersiades.awcdata.models.Threat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ThreatMapServiceTest {

    ThreatMapService threatMapService;

    final Long threatId = 1L;

    @BeforeEach
    void setUp() {
        threatMapService = new ThreatMapService();
        threatMapService.save(new Threat(threatId));
    }

    @Test
    void findAll() {
        Set<Threat> threats = threatMapService.findAll();

        assertEquals(1, threats.size());
    }

    @Test
    void findById() {
        Threat threat = threatMapService.findById(threatId);

        assertEquals(threatId, threat.getId());
    }

    @Test
    void saveWithGivenId() {
        Long id = 2L;
        Threat threat2 = new Threat(id);

        Threat savedThreat = threatMapService.save(threat2);

        assertEquals(id, savedThreat.getId());
    }

    @Test
    void saveWithNoIdGiven() {

        Threat savedThreat = threatMapService.save(new Threat());

        assertNotNull(savedThreat);
        assertNotNull(savedThreat.getId());
    }

    @Test
    void delete() {
        threatMapService.delete(threatMapService.findById(threatId));

        assertEquals(0, threatMapService.findAll().size());
    }

    @Test
    void deleteById() {
        threatMapService.deleteById(threatId);

        assertEquals(0, threatMapService.findAll().size());
    }
}