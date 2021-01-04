package com.spring.webapp.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "insurance_number")
    private int insuranceNumber;

    @Column(name = "disease")
    private String disease;

    @Column(name = "status")
    private String status;
//***TODO**** add birthdate//
   // @Column(name = "doctor_id")
  //  private int doctorId;

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.DETACH,
            CascadeType.MERGE,CascadeType.REFRESH})
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @OneToMany(cascade =  CascadeType.ALL, mappedBy = "patient")
    private List<Treatment> treatments;

    public Patient() {
    }

    public Patient(String name, String surname, int insuranceNumber, String disease, String status) {
        this.name = name;
        this.surname = surname;
        this.insuranceNumber = insuranceNumber;
        this.disease = disease;
        this.status = status;
    }

    public void addTreatment(Treatment treatment) {
        if (treatments == null) {
            treatments = new ArrayList<>();
        }
        treatments.add(treatment);
        treatment.setPatient(this);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getDisease() {
        return disease;
    }

    public String getStatus() {
        return status;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public List<Treatment> getTreatments() {
        return treatments;
    }

    public void setTreatments(List<Treatment> treatments) {
        this.treatments = treatments;
    }

}
