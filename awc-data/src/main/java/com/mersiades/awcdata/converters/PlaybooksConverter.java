package com.mersiades.awcdata.converters;

import com.mersiades.awcdata.enums.Playbooks;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class PlaybooksConverter implements AttributeConverter<Playbooks, String> {
    @Override
    public String convertToDatabaseColumn(Playbooks playbook) {
        if (playbook == null) {
            return null;
        }
        return playbook.getCode();
    }

    @Override
    public Playbooks convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(Playbooks.values())
                .filter((c) -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
