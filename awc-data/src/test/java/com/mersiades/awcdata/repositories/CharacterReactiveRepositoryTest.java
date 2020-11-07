package com.mersiades.awcdata.repositories;

import com.mersiades.awcdata.models.Character;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataMongoTest
@EnableMongoRepositories(basePackages = {"com.mersiades.awcdata.repositories.CharacterReactiveRepository"})
// I think this is not working because the awc-data module has no application context
//@ContextConfiguration(classes = {})
class CharacterReactiveRepositoryTest {

    @Autowired
    CharacterReactiveRepository characterReactiveRepository;

    @BeforeEach
    void setUp() {
        // NPE
        System.out.println("characterReactiveRepository" + characterReactiveRepository);
//        characterReactiveRepository.deleteAll().block();
    }

    @Test
    @Disabled
    public void testCharacterSave() throws Exception {
        Character character = new Character();

        characterReactiveRepository.save(character).block();

        Long count = characterReactiveRepository.count().block();

        assertEquals(Long.valueOf(1L), count);
    }
}