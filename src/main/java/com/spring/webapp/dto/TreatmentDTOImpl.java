package com.spring.webapp.dto;

import com.spring.webapp.TreatmentType;
import com.spring.webapp.entity.Doctor;
import com.spring.webapp.entity.Patient;
import com.spring.webapp.entity.ProcedureMedicine;
import com.spring.webapp.entity.Treatment;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public class TreatmentDTOImpl implements EntityDTO {

    private int treatmentId;

    @NotNull(message = "Type field must to be filled")
    private TreatmentType type;

    @Min(value = 1, message = "1 - Min pattern")
    @Max(value = 5, message = "5 - Max pattern")
    @NotNull
    private int timePattern;

    @Min(value = 0, message = "0 - Min dose")
    @NotNull
    private double dose;

    /*  @NotBlank(message = "Period field must to be filled")
      private String period;*/
    private LocalDate startDate;

    private LocalDate endDate;

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

    public TreatmentDTOImpl(int id, TreatmentType type, int timePattern, double dose) {
        this.treatmentId = id;
        this.type = type;
        this.timePattern = timePattern;
     //   this.period = period;
        this.dose = dose;
    }

    public int getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(int id) {
        this.treatmentId = id;
    }

    public TreatmentType getType() {
        return type;
    }

    public void setType(TreatmentType type) {
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

  /*  public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }*/
    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
