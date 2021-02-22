package com.spring.webapp.dto;

import com.spring.webapp.TreatmentType;

public class BinTreatmentDTOImpl extends TreatmentDTOImpl {
    public BinTreatmentDTOImpl(int id, TreatmentType type, int timePattern, double dose) {
        super(id, type, timePattern, dose);
    }
}
