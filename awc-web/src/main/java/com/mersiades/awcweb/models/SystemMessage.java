package com.mersiades.awcweb.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SystemMessage {

    @Id
    private String id;

    private String successMessage;

    private String errorMessage;
}
