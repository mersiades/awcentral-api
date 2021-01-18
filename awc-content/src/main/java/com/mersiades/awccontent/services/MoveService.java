package com.mersiades.awccontent.services;

import com.mersiades.awccontent.enums.MoveKinds;
import com.mersiades.awccontent.enums.Playbooks;
import com.mersiades.awccontent.models.Move;
import reactor.core.publisher.Flux;

public interface MoveService extends ReactiveCrudService<Move, String>{

    Flux<Move> findAllByPlaybookAndKind(Playbooks playbookType, MoveKinds kind);
}
