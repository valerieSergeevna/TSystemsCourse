package com.spring.webapp.dto;

import com.spring.webapp.TreatmentType;
import com.spring.webapp.entity.Patient;
import com.spring.webapp.entity.ProcedureMedicine;
import com.spring.webapp.entity.Treatment;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

public class TreatmentEventDTOImpl {

    private int id;
    private TreatmentType type;
    private LocalDateTime treatmentTime;
    private double dose;
    private String status;
    private String cancelReason;

    private Patient patient;
    private Treatment treatment;
    private ProcedureMedicine procedureMedicine;

    public TreatmentEventDTOImpl() {
    }

    public TreatmentEventDTOImpl(int id, TreatmentType type, LocalDateTime treatmentTime, double dose, String status) {
        this.id = id;
        this.type = type;
        this.treatmentTime = treatmentTime;
        this.dose = dose;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TreatmentType getType() {
        return type;
    }

    public void setType(TreatmentType type) {
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
