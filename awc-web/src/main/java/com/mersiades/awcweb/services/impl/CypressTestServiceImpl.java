package com.mersiades.awcweb.services.impl;

import com.mersiades.awcdata.repositories.*;
import com.mersiades.awcweb.models.SystemMessage;
import com.mersiades.awcweb.services.CypressTestService;
import com.mersiades.awcweb.services.MockDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CypressTestServiceImpl implements CypressTestService {

    private final MockDataService mockDataService;
    private final UserRepository userRepository;
    private final NpcRepository npcRepository;
    private final CharacterRepository characterRepository;
    private final GameRepository gameRepository;
    private final GameRoleRepository gameRoleRepository;
    private final ThreatRepository threatRepository;
    private final AllyRepository allyRepository;
    private final Environment environment;

    @Profile("cypress")
    @Override
    public SystemMessage resetDB() {
        String[] activeProfiles = this.environment.getActiveProfiles();

        if (activeProfiles.length == 1 && activeProfiles[0].equals("cypress")) {
            try {
                // Delete existing mock data
                allyRepository.deleteAll();
                characterRepository.deleteAll();
                gameRepository.deleteAll();
                gameRoleRepository.deleteAll();
                npcRepository.deleteAll();
                threatRepository.deleteAll();
                userRepository.deleteAll();

                // Reseed with mock data
                mockDataService.loadMockData();
                mockDataService.loadMockCharacters();
                mockDataService.loadHx();
                mockDataService.loadThreats();
                mockDataService.loadNpcs();

                return SystemMessage.builder().id(new ObjectId().toString()).successMessage("Database reset").build();
            } catch (Exception exception) {
                return SystemMessage.builder().id(new ObjectId().toString()).errorMessage("Database reset failed: " + exception.getMessage()).build();
            }
        } else  {
            return SystemMessage.builder().id(new ObjectId().toString()).errorMessage("Database reset failed: not Cypress server").build();
        }
    }
}
