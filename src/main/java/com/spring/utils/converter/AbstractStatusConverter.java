package com.spring.utils.converter;

import com.spring.webapp.StatusEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public abstract class AbstractStatusConverter  implements AttributeConverter<StatusEnum, String> {
    @Override
    public String convertToDatabaseColumn(StatusEnum status) {
        return status.getTitle();
    }
    @Override
    public abstract StatusEnum convertToEntityAttribute(String dbData);
}
