package com.spring.webapp.dto;

import com.spring.webapp.entity.Doctor;
import com.spring.webapp.entity.Patient;
import com.spring.webapp.entity.ProcedureMedicine;
import com.spring.webapp.entity.Treatment;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;


public class TreatmentDTOImpl implements EntityDTO {

    private int treatmentId;

    @NotBlank(message = "Type field must to be filled")
    private String type;

    @NotBlank(message = "This field must to be filled")
    private int timePattern;

    @NotBlank(message = "Dose field must to be filled")
    private double dose;

    @NotBlank(message = "Period field must to be filled")
    private String period;

    @NotBlank(message = "This field must to be filled")
    private String typeName;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public TreatmentDTOImpl() {
    }

    public TreatmentDTOImpl(int id,String type, int timePattern, String period, double dose) {
        this.treatmentId = id;
        this.type = type;
        this.timePattern = timePattern;
        this.period = period;
        this.dose = dose;
    }

    public int getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(int id) {
        this.treatmentId = id;
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
