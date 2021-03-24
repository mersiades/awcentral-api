package com.mersiades.awccontent.services;

import com.mersiades.awccontent.enums.MoveType;
import com.mersiades.awccontent.enums.PlaybookType;
import com.mersiades.awccontent.models.Move;

import java.util.List;

public interface MoveService extends CrudService<Move, String>{

    List<Move> findAllByPlaybookAndKind(PlaybookType playbookType, MoveType kind);

    Move findByName(String moveName);
}
