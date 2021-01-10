package com.spring.webapp.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "procedures_medicines")
public class ProcedureMedicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;


  /*  @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.DETACH,
            CascadeType.MERGE,CascadeType.REFRESH}, mappedBy = "procedureMedicine")
    private List<Treatment> treatments;
*/
    public ProcedureMedicine() {
    }

    public ProcedureMedicine( String name, String type) {
        this.name = name;
        this.type = type;
    }

 /*   public void addTreatment(Treatment treatment) {
        if (treatments == null) {
            treatments = new ArrayList<>();
        }
        treatments.add(treatment);
        treatment.setProcedureMedicine(this);
    }*/

    public int getId() {
        return id;
    }

  /*  public List<Treatment> getTreatment() {
        return treatments;
    }

    public void setTreatment(List<Treatment> treatments) {
        this.treatments = treatments;
    }*/

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
