package com.spring.webapp.entity;


import com.spring.utils.YearMonthDateAttributeConverter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "patients")
public class Patient{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    @NotBlank(message = "Name field must to be filled")
    private String name;

    @Column(name = "surname")
    @NotBlank(message = "Last name field must to be filled")
    private String surname;

    @Column(name = "ages")
   // @Convert(converter = YearMonthDateAttributeConverter.class)
    @Min(value = 1, message = "Age has to be over 1 years old")
    private int ages;


    @Column(name = "insurance_number")
    @Min(value = 0, message = "Must be inserted correct insurance number")
    @NotNull
    private int insuranceNumber;

    @Column(name = "disease")
    @NotBlank(message = "Disease field must to be filled")
    private String disease;

    @Column(name = "status")
    @NotBlank(message = "Status field must to be filled")
    private String status;

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.DETACH,
            CascadeType.MERGE,CascadeType.REFRESH})
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @OneToMany(cascade =  CascadeType.ALL, mappedBy = "patient")
    private List<Treatment> treatments = new ArrayList<>();

    public Patient() {
    }

    public int getAges() {
        return ages;
    } //TODO: CHANGE TYPE AND CONVERTER

    public void setAges(int ages) {
        this.ages = ages;
    }

    public int getInsuranceNumber() {
        return insuranceNumber;
    }

    public void setInsuranceNumber(int insuranceNumber) {
        this.insuranceNumber = insuranceNumber;
    }

    public Patient(String name, String surname, int ages, int insuranceNumber, String disease, String status) {
        this.name = name;
        this.surname = surname;
        this.insuranceNumber = insuranceNumber;
        this.disease = disease;
        this.status = status;
        this.ages = ages;
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
