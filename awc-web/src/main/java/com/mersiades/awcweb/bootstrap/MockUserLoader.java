package com.mersiades.awcweb.bootstrap;

import com.mersiades.awcdata.repositories.GameRepository;
import com.mersiades.awcdata.repositories.GameRoleRepository;
import com.mersiades.awcdata.repositories.UserRepository;
import com.mersiades.awcweb.services.MockDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value = 2)
@Profile({"dev", "cypress"})
@RequiredArgsConstructor
@Slf4j
public class MockUserLoader implements CommandLineRunner {
    private final MockDataService mockDataService;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private final GameRoleRepository gameRoleRepository;

    @Override
    public void run(String... args) {
        mockDataService.loadMockData();

        log.info("Game count: " + gameRepository.count());
        log.info("GameRole count: " + gameRoleRepository.count());
        log.info("User count: " + userRepository.count());
    }
}

