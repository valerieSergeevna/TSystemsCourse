package com.spring.webapp.entity;

import com.spring.webapp.TreatmentType;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "treatments")
public class Treatment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int treatmentId;

    @Column(name = "type")
    @NotNull(message = "Type field must to be filled")
    @Enumerated(EnumType.STRING)
    private TreatmentType type;

   /* @Column(name = "patient_id")
    private String patientID;

    @Column(name = "type_id")
    private String typeID;*/

    @Column(name = "time_pattern")
    @Min(value = 1, message = "1 - Min pattern")
    @Max(value = 5, message = "5 - Max pattern")
    @NotNull
    private int timePattern;

    @Column(name = "dose")
    @Min(value = 0, message = "0 - Min dose")
    @NotNull
    private double dose;


   /* @Column(name = "period")
    private String period;*/

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.DETACH,
            CascadeType.MERGE,CascadeType.REFRESH})
    @JoinColumn(name = "patient_id")
    private Patient patient;

    public ProcedureMedicine getProcedureMedicine() {
        return procedureMedicine;
    }

    public void setProcedureMedicine(ProcedureMedicine procedureMedicine) {
        this.procedureMedicine = procedureMedicine;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "type_id")
    private ProcedureMedicine procedureMedicine;

    public Treatment() {
    }

    public Treatment(TreatmentType type, int timePattern,double dose, LocalDateTime startDate, LocalDateTime endDate) {
        this.type = type;
        this.timePattern = timePattern;
        this.dose = dose;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

  /*  public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }*/

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}
