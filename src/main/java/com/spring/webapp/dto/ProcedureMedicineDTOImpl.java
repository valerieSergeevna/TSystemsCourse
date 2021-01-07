package com.spring.webapp.dto;

import com.spring.webapp.entity.Treatment;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

public class ProcedureMedicineDTOImpl implements EntityDTO {
    private int id;
    private String name;
    private String type;

    public ProcedureMedicineDTOImpl() {
    }

    public ProcedureMedicineDTOImpl( int id, String name, String type) {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
