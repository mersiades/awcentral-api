package com.mersiades.awcdata.services.jpa;

import com.mersiades.awcdata.enums.Roles;
import com.mersiades.awcdata.enums.Threats;
import com.mersiades.awcdata.models.Game;
import com.mersiades.awcdata.models.GameRole;
import com.mersiades.awcdata.models.Threat;
import com.mersiades.awcdata.models.User;
import com.mersiades.awcdata.repositories.ThreatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ThreatJpaServiceTest {

    @Mock
    ThreatRepository threatRepository;

    @InjectMocks
    ThreatJpaService service;

    Threat mockThreat;

    @BeforeEach
    void setUp() {
        mockThreat = new Threat(1L);
    }

    @Test
    void findAll() {
        GameRole mockGameRole = new GameRole(4L, Roles.MC, new Game(5L, 123L, 234L, "mock game"),new User(6L));
        Set<Threat> returnThreats = new HashSet<>();
        returnThreats.add(new Threat(2L));
        returnThreats.add(new Threat(mockGameRole, "Gritsnot", Threats.AFFLICTION, "to infect"));

        when(threatRepository.findAll()).thenReturn(returnThreats);

        Set<Threat> threats = service.findAll();

        assertNotNull(threats);
        assertEquals(2, threats.size());
    }

    @Test
    void findById() {
        when(threatRepository.findById(any())).thenReturn(Optional.of(mockThreat));

        Threat threat = service.findById(1L);

        assertNotNull(threat);

        assertEquals(threat.getId(), mockThreat.getId());
    }

    @Test
    void save() {
        when(threatRepository.save(any())).thenReturn(mockThreat);

        Threat threat = service.save(mockThreat);

        assertNotNull(threat);
        assertEquals(threat.getId(), mockThreat.getId());
        verify(threatRepository).save(any());
    }

    @Test
    void delete() {
        service.delete(mockThreat);

        verify(threatRepository).delete(any());
    }

    @Test
    void deleteById() {
        service.deleteById(1L);

        verify(threatRepository).deleteById(any());
    }
}