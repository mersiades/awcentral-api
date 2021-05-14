package com.mersiades.awcweb.bootstrap;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
@Order(value = 0)
@Profile({"cypress"})
@Slf4j
@RequiredArgsConstructor
public class CypressTestDataLoader implements CommandLineRunner {

    private final MongoTemplate mongoTemplate;

    @Override
    public void run(String... args) {
        log.info("Dropping db for Cypress tests");
        mongoTemplate.getDb().drop();
        log.info("Db for Cypress tests dropped");
    }
}
