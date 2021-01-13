package com.spring.webapp.service;

import com.spring.webapp.dao.DoctorDAOImpl;
import com.spring.webapp.dao.TreatmentEventDAOImpl;
import com.spring.webapp.dto.DoctorDTOImpl;
import com.spring.webapp.dto.TreatmentEventDTOImpl;
import com.spring.webapp.entity.Doctor;
import com.spring.webapp.entity.Treatment;
import com.spring.webapp.entity.TreatmentEvent;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

public class TreatmentEventServiceImpl {
    @Autowired
    private TreatmentEventDAOImpl treatmentEventDAO;

    @Transactional
    public List<TreatmentEventDTOImpl> getAll() {
        List<TreatmentEvent> treatmentEventList = treatmentEventDAO.getAll();
        return treatmentEventList.stream()
                .map(treatmentEvent -> new TreatmentEventDTOImpl(treatmentEvent.getId(),treatmentEvent.getType(),
                        treatmentEvent.getTreatmentTime(),treatmentEvent.getDose(),treatmentEvent.getStatus()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void save(TreatmentEventDTOImpl treatmentEventDTO) {
        TreatmentEvent treatmentEvent = new TreatmentEvent();
        BeanUtils.copyProperties(treatmentEventDTO, treatmentEvent);
        treatmentEventDAO.save(treatmentEvent);
    }

    @Transactional
    public void delete(int id) {
        treatmentEventDAO.delete(id);
    }

    @Transactional
    public TreatmentEventDTOImpl get(int id) {
        TreatmentEventDTOImpl treatmentEventDTO = new TreatmentEventDTOImpl();
        BeanUtils.copyProperties(treatmentEventDAO.get(id),treatmentEventDTO);
        return treatmentEventDTO;
    }
}
