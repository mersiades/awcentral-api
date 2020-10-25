package com.mersiades.awcdata.services.jpa;

import com.mersiades.awcdata.models.Move;
import com.mersiades.awcdata.repositories.MoveRepository;
import com.mersiades.awcdata.services.MoveService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Profile("jpa")
public class MoveJpaService implements MoveService {

    private final MoveRepository moveRepository;

    public MoveJpaService(MoveRepository moveRepository) {
        this.moveRepository = moveRepository;
    }

    @Override
    public Set<Move> findAll() {
        Set<Move> moves = new HashSet<>();
        moveRepository.findAll().forEach(moves::add);
        return moves;
    }

    @Override
    public Move findById(String id) {
        Optional<Move> optionalMove = moveRepository.findById(id);
        return optionalMove.orElse(null);
    }

    @Override
    public Move save(Move move) {
        return moveRepository.save(move);
    }

    @Override
    public void delete(Move move) {
        moveRepository.delete(move);
    }

    @Override
    public void deleteById(String id) {
        moveRepository.deleteById(id);
    }
}
