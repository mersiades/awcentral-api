package com.mersiades.awcdata.services.map;

import com.mersiades.awcdata.models.Character;
import com.mersiades.awcdata.services.CharacterService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Profile({ "default", "map"})
public class CharacterMapService extends AbstractMapService<Character, Long> implements CharacterService {
    @Override
    public Set<Character> findAll() {
        return super.findAll();
    }

    @Override
    public Character findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Character save(Character character) {
        return super.save(character);
    }

    @Override
    public void delete(Character character) {
        super.delete(character);
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }
}
