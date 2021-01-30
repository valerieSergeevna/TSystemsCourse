package com.spring.webapp.dto;

import com.spring.webapp.PatientStatus;
import com.spring.webapp.entity.Doctor;
import org.springframework.security.access.method.P;

import javax.print.Doc;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class PatientDTOImpl {

    private int id;
    @NotBlank(message = "Name field must to be filled")
    private String name;

    @NotBlank(message = "Last name field must to be filled")
    private String surname;

    @NotBlank(message = "Disease field must to be filled")
    private String disease;

    @NotNull(message = "Status field must to be filled")
    private String status;

    @Min(value = 1, message = "Age has to be over 1 years old")
    @NotNull
    private Integer ages;

    @Min(value = 0, message = "Must be inserted correct insurance number")
    @NotNull
    private Integer insuranceNumber;

    private Doctor doctor;


    private List<TreatmentDTOImpl> treatments = new ArrayList<>();

    public PatientDTOImpl(List<TreatmentDTOImpl> treatments) {
        this.treatments = treatments;
    }


    public PatientDTOImpl() {
    }

    public Integer getAges() {
        return ages;
    }

    public void setAges(Integer ages) {
        this.ages = ages;
    }

    public PatientDTOImpl(int id, String name, String surname, Integer ages, String disease, String status, List<TreatmentDTOImpl> treatments, Doctor doctor) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.disease = disease;
        this.status = status;
        this.ages = ages;
        this.treatments = treatments;
        this.doctor = doctor;
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

    public Integer getInsuranceNumber() {
        return insuranceNumber;
    }

    public void setInsuranceNumber(Integer insuranceNumber) {
        this.insuranceNumber = insuranceNumber;
    }

    public List<TreatmentDTOImpl> getTreatments() {
        return treatments;
    }

    public void setTreatments(List<TreatmentDTOImpl> treatments) {
        this.treatments = treatments;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}
