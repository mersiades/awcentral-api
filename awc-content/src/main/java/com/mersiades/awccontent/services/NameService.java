package com.mersiades.awccontent.services;

import com.mersiades.awccontent.enums.PlaybookType;
import com.mersiades.awccontent.models.Name;

import java.util.List;

public interface NameService extends CrudService<Name, String>{

    List<Name> findAllByPlaybookType(PlaybookType playbookType);
}
