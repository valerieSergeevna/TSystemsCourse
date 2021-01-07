package com.spring.webapp.dto;

import com.spring.webapp.entity.Doctor;
import com.spring.webapp.entity.Treatment;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDTOImpl implements EntityDTO {

    private int id;
    private String name;
    private String surname;
    private String disease;
    private String status;
//***TODO**** add birthdate//
    // @Column(name = "doctor_id")
    //  private int doctorId;
    public PatientDTOImpl() {
    }

    public PatientDTOImpl(int id, String name, String surname, String disease, String status) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.disease = disease;
        this.status = status;
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

}
