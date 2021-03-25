package com.mersiades.awccontent.services;

import com.mersiades.awccontent.enums.PlaybookType;
import com.mersiades.awccontent.models.Look;

import java.util.List;

public interface LookService extends CrudService<Look, String> {
    List<Look> findAllByPlaybookType(PlaybookType playbookType);
}
