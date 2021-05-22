package com.mersiades.awcweb.services;

import com.mersiades.awcweb.models.SystemMessage;
import org.springframework.context.annotation.Profile;

public interface CypressTestService {

    @Profile("cypress")
    SystemMessage resetDB();
}
