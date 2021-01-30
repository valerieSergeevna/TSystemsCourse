package com.spring.utils.converter;

import com.spring.webapp.EventStatus;
import com.spring.webapp.StatusEnum;


public class EventStatusConverter  extends AbstractStatusConverter{
    @Override
    public StatusEnum convertToEntityAttribute(String dbData) {
        return EventStatus.fromTitle(dbData);
    }
}
