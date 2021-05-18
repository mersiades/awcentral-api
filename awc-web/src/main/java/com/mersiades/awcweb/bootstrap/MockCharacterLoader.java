package com.mersiades.awcweb.bootstrap;

import com.mersiades.awcdata.repositories.CharacterRepository;
import com.mersiades.awcweb.services.MockDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Component
@Order(value = 3)
@Profile({"dev", "cypress"})
@RequiredArgsConstructor
@Slf4j
public class MockCharacterLoader implements CommandLineRunner {

    private final MockDataService mockDataService;
    private final CharacterRepository characterRepository;

    @Override
    public void run(String... args) {
        long characterCount = characterRepository.count();
        if (characterCount == 0) {
            mockDataService.loadMockCharacters();
            mockDataService.loadHx();
            mockDataService.loadThreats(); // characterCount is serving as a proxy for threatCount here
            mockDataService.loadNpcs(); // characterCount is serving as a proxy for npcCount here
        }

        log.info("Character count: " + characterRepository.count());
    }
}
