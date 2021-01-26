package com.spring.webapp.dto;

import com.spring.webapp.TreatmentType;
import com.spring.webapp.entity.Treatment;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

public class ProcedureMedicineDTOImpl implements EntityDTO {
    private int id;
    private String name;
    private TreatmentType type;

    public ProcedureMedicineDTOImpl() {
    }

    public ProcedureMedicineDTOImpl( int id, String name, TreatmentType type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TreatmentType getType() {
        return type;
    }

    public void setType(TreatmentType type) {
        this.type = type;
    }
}
