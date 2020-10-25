package com.mersiades.awcdata.services;

import com.mersiades.awcdata.models.Threat;
import org.springframework.stereotype.Service;

@Service
public interface ThreatService extends CrudService<Threat, String> {
}
