package com.mersiades.awcdata.repositories;

import com.mersiades.awcdata.models.Threat;
import org.springframework.data.repository.CrudRepository;

public interface ThreatRepository extends CrudRepository<Threat, String> {
}
