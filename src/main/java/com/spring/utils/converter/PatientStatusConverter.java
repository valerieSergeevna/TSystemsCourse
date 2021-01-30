package com.spring.utils.converter;

import com.spring.webapp.PatientStatus;
import com.spring.webapp.StatusEnum;

public class PatientStatusConverter extends AbstractStatusConverter{
    @Override
    public StatusEnum convertToEntityAttribute(String dbData) {
      return PatientStatus.fromTitle(dbData);
    }
}
