package com.spring.webapp.dto;

import com.spring.webapp.entity.Patient;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorDTOImpl{
    private int id;//????
    private String name;
    private String surname;
    private String position;
    //private List<Patient> patients;//?????

    public DoctorDTOImpl() {
    }

    public DoctorDTOImpl(int id,String name, String surname, String position) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.position = position;
    }

    public void setPosition(String position) {
        this.position = position;
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
        this.position = position;
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

    public String getPosition() {
        return position;
    }

}

