package com.mersiades.awcdata.converters;

import com.mersiades.awcdata.enums.Stats;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class StatsConverter implements AttributeConverter<Stats, String> {

    @Override
    public String convertToDatabaseColumn(Stats stat) {
        if (stat == null) {
            return null;
        }
        return stat.getCode();
    }

    @Override
    public Stats convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(Stats.values())
                .filter((c) -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
