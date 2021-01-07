package com.spring.webapp.dto;

import com.spring.webapp.entity.Doctor;
import com.spring.webapp.entity.Patient;
import com.spring.webapp.entity.ProcedureMedicine;
import com.spring.webapp.entity.Treatment;

import javax.persistence.*;
import java.util.List;

public class TreatmentDTOImpl implements EntityDTO {

    private int id;

    private String type;

    private int timePattern;

    private double dose;

    private String period;

    public TreatmentDTOImpl() {
    }

    public TreatmentDTOImpl(int id,String type, int timePattern, String period, double dose) {
        this.id = id;
        this.type = type;
        this.timePattern = timePattern;
        this.period = period;
        this.dose = dose;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTimePattern() {
        return timePattern;
    }

    public void setTimePattern(int timePattern) {
        this.timePattern = timePattern;
    }

    public double getDose() {
        return dose;
    }

    public void setDose(double dose) {
        this.dose = dose;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }
}
