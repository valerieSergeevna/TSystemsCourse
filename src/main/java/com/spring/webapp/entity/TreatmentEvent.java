package com.spring.webapp.entity;

import com.spring.utils.converter.EventStatusConverter;
import com.spring.webapp.EventStatus;
import com.spring.webapp.TreatmentType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "treatment_events")
public class TreatmentEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private TreatmentType type;

    @Column(name = "time")
    private LocalDateTime treatmentTime;

    @Column(name = "dose")
    private double dose;

    @Column(name = "status")
    @Convert(converter = EventStatusConverter.class)
    private EventStatus status;

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

    public TreatmentEvent(TreatmentType type, LocalDateTime treatmentTime, double dose, EventStatus status, String cancelReason) {
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

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
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
