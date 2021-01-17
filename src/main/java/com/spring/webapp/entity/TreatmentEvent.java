package com.spring.webapp.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "treatment_events")
public class TreatmentEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "type")
    private String type;

    @Column(name = "time")
    private LocalDateTime treatmentTime;

    @Column(name = "dose")
    private double dose;

    @Column(name = "status")
    private String status;

    @Column(name = "cancel_reason")
    private String cancelReason;


    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.DETACH,
            CascadeType.MERGE,CascadeType.REFRESH})
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.DETACH,
            CascadeType.MERGE,CascadeType.REFRESH},fetch = FetchType.EAGER)
    @JoinColumn(name = "treatment_id")
    private Treatment treatment;

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.DETACH,
            CascadeType.MERGE,CascadeType.REFRESH})
    @JoinColumn(name = "type_id")
    private ProcedureMedicine procedureMedicine;

    public TreatmentEvent() {
    }

    public TreatmentEvent(String type, LocalDateTime treatmentTime, double dose, String status, String cancelReason) {
        this.type = type;
        this.treatmentTime = treatmentTime;
        this.dose = dose;
        this.status = status;
        this.cancelReason = cancelReason;
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

    public LocalDateTime getTreatmentTime() {
        return treatmentTime;
    }

    public void setTreatmentTime(LocalDateTime treatmentTime) {
        this.treatmentTime = treatmentTime;
    }

    public double getDose() {
        return dose;
    }

    public void setDose(double dose) {
        this.dose = dose;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Treatment getTreatment() {
        return treatment;
    }

    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }

    public ProcedureMedicine getProcedureMedicine() {
        return procedureMedicine;
    }

    public void setProcedureMedicine(ProcedureMedicine procedureMedicine) {
        this.procedureMedicine = procedureMedicine;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }
}
