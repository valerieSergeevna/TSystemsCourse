package com.spring.webapp.entity;

import com.spring.webapp.TreatmentType;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "bin_treatments")
public class BinTreatment extends Treatment{
    public BinTreatment() {
    }

    public BinTreatment(TreatmentType type, int timePattern, double dose, LocalDateTime startDate, LocalDateTime endDate) {
        super(type, timePattern, dose, startDate, endDate);
    }
}
