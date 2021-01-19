package com.spring.webapp.dto;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class PatientDTOImpl implements EntityDTO {

    private int id;
    private String name;
    private String surname;
    private String disease;
    private String status;
    private int ages;
    private int insuranceNumber;
    private List<TreatmentDTOImpl> treatments = new ArrayList<>();

    public PatientDTOImpl(List<TreatmentDTOImpl> treatments) {
        this.treatments = treatments;
    }

    public List<TreatmentDTOImpl> getTreatments() {
        return treatments;
    }

    public void setTreatments(List<TreatmentDTOImpl> treatments) {
        this.treatments = treatments;
    }

    //***TODO**** add birthdate//
    // @Column(name = "doctor_id")
    //  private int doctorId;
    public PatientDTOImpl() {
    }

    public int getAges() {
        return ages;
    }

    public void setAges(int ages) {
        this.ages = ages;
    }

    public PatientDTOImpl(int id, String name, String surname, int ages, String disease, String status, List<TreatmentDTOImpl> treatments) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.disease = disease;
        this.status = status;
        this.ages = ages;
        this.treatments = treatments;
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

    public int getInsuranceNumber() {
        return insuranceNumber;
    }

    public void setInsuranceNumber(int insuranceNumber) {
        this.insuranceNumber = insuranceNumber;
    }
}
