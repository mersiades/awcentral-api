package com.mersiades.awcdata.services;

import com.mersiades.awcdata.enums.MoveKinds;
import com.mersiades.awcdata.enums.Playbooks;
import com.mersiades.awcdata.models.Move;
import reactor.core.publisher.Flux;

public interface MoveService extends ReactiveCrudService<Move, String>{

    Flux<Move> findAllByPlaybookAndKind(Playbooks playbookType, MoveKinds kind);
}
