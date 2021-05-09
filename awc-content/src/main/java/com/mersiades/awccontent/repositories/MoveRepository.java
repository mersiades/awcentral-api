package com.mersiades.awccontent.repositories;

import com.mersiades.awccontent.enums.MoveType;
import com.mersiades.awccontent.enums.PlaybookType;
import com.mersiades.awccontent.models.Move;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MoveRepository extends MongoRepository<Move, String> {

    List<Move> findAllByPlaybookAndKind(PlaybookType playbookType, MoveType kind);

    Move findByKind(MoveType kind);

    List<Move> findAllByKind(MoveType kind);

    Move findByName(String moveName);

    List<Move> findAllByName(List<String> moveNames);
}
