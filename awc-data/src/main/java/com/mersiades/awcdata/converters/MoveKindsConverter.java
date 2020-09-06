package com.mersiades.awcdata.converters;

import com.mersiades.awcdata.enums.MoveKinds;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class MoveKindsConverter implements AttributeConverter<MoveKinds, String> {

    @Override
    public String convertToDatabaseColumn(MoveKinds moveKinds) {
        if (moveKinds == null) {
            return null;
        }
        return moveKinds.getCode();
    }

    @Override
    public MoveKinds convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(MoveKinds.values())
                .filter((c) -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
