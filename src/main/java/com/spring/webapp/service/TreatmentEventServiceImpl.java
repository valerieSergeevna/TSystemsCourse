package com.spring.webapp.service;

import com.spring.webapp.dao.*;
import com.spring.webapp.dto.DoctorDTOImpl;
import com.spring.webapp.dto.PatientDTOImpl;
import com.spring.webapp.dto.TreatmentDTOImpl;
import com.spring.webapp.dto.TreatmentEventDTOImpl;
import com.spring.webapp.entity.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TreatmentEventServiceImpl {
    @Autowired
    private TreatmentEventDAOImpl treatmentEventDAO;

    @Autowired
    private PatientDAOImpl patientDAO;

    @Autowired
    private TreatmentDAOImpl treatmentDAO;

    @Transactional
    public List<TreatmentEventDTOImpl> getAll() {
        List<TreatmentEvent> treatmentEventList = treatmentEventDAO.getAll();
        return toTreatmentEventDTOList(treatmentEventList);
    }

    @Transactional
    public void save(TreatmentEventDTOImpl treatmentEventDTO) {
        TreatmentEvent treatmentEvent = new TreatmentEvent();
        BeanUtils.copyProperties(treatmentEventDTO, treatmentEvent);
        treatmentEventDAO.save(treatmentEvent);
    }

    @Transactional
    public void update(TreatmentEventDTOImpl treatmentEventDTO) {
        treatmentEventDAO.save(toTreatmentEvent(treatmentEventDTO));
    }

    @Transactional
    public void delete(int id) {
        treatmentEventDAO.delete(id);
    }

    @Transactional
    public TreatmentEventDTOImpl get(int id) {
        TreatmentEventDTOImpl treatmentEventDTO = new TreatmentEventDTOImpl();
        BeanUtils.copyProperties(treatmentEventDAO.get(id), treatmentEventDTO);
        return treatmentEventDTO;
    }

    @Transactional
    public List<TreatmentEventDTOImpl> createTimeTable(TreatmentDTOImpl treatmentDTO) {
       /* TreatmentEventDTOImpl treatmentEventDTO = new TreatmentEventDTOImpl();


        List<TreatmentEvent> treatmentEventList = new ArrayList<>();

        String type = treatmentDTO.getType();
        Patient patient = treatmentDAO.get(treatmentDTO.getTreatmentId()).getPatient();

        ProcedureMedicine procedureMedicine = treatmentDAO.get(treatmentDTO.getTreatmentId()).getProcedureMedicine();

        int timePattern = treatmentDTO.getTimePattern();
        double dose = treatmentDTO.getDose();

        String[] period = treatmentDTO.getPeriod().split(" ");




        LocalDateTime startDate = LocalDateTime.now().plusDays(1);
        LocalDateTime endDate = null;

        String duration = "months";

      //  switch (period[1]) {
        switch (duration) {
            case "days":
                endDate = LocalDateTime.now().plusDays(Long.parseLong(period[0]));
                break;
            case "weeks":
                endDate = LocalDateTime.now().plusWeeks(Long.parseLong(period[0]));
                break;
            case "months":
                endDate = LocalDateTime.now().plusMonths(Long.parseLong(period[0]));
                break;
        }

        while (startDate.isBefore(endDate)) {
            TreatmentEvent treatmentEvent = new TreatmentEvent();
            if (type.equals("treatment")) {
               // startDate.plusDays((timePattern / 7));
                for (int i = 1; i <= timePattern; i++) {
                    treatmentEvent.setDose(dose);
                    treatmentEvent.setTreatmentTime(LocalDateTime.of(startDate.getYear(), startDate.getMonth(),
                            startDate.getDayOfMonth(), 8+ 24 - (24/i), 0, 0));
                    startDate = startDate.plusDays(1);
                }
            }else{
                treatmentEvent.setDose(1);
                startDate = startDate.plusDays((7 / timePattern));
            }
            treatmentEvent.setPatient(patient);
            treatmentEvent.setProcedureMedicine(procedureMedicine);
            // treatmentEvent.setStatus();
            treatmentEvent.setType(type);
            treatmentEvent.setTreatment(treatmentDAO.get(treatmentDTO.getTreatmentId()));
            treatmentEventDAO.save(treatmentEvent);
            treatmentEventList.add(treatmentEvent);
        }
        return treatmentEventList;*/
        return toTreatmentEventDTOList(treatmentEventDAO.createTimeTable(treatmentDAO.get(treatmentDTO.getTreatmentId())));
    }

    @Transactional
    public  List<TreatmentEventDTOImpl> getByPatient(int patientId){
        return toTreatmentEventDTOList(treatmentEventDAO.getByPatient(patientId));
    }

    @Transactional
    public  List<TreatmentEventDTOImpl> getNearestEvents(LocalDateTime time){
        return toTreatmentEventDTOList(treatmentEventDAO.getNearestEvents(time));
    }

    @Transactional
    public  List<TreatmentEventDTOImpl> getTodayEvents(LocalDateTime time){
        return toTreatmentEventDTOList(treatmentEventDAO.getTodayEvents(time));
    }

    //convector's method from-to

    public  List<TreatmentEventDTOImpl> toTreatmentEventDTOList(List<TreatmentEvent> treatmentsEventList) {
        return treatmentsEventList.stream()
                .map(this::toTreatmentEventDTO)
                .collect(Collectors.toList());
    }

    public TreatmentEventDTOImpl toTreatmentEventDTO(TreatmentEvent treatmentEvent){
        TreatmentEventDTOImpl treatmentEventDTO = new TreatmentEventDTOImpl(treatmentEvent.getId(), treatmentEvent.getType(),
                treatmentEvent.getTreatmentTime(), treatmentEvent.getDose(), treatmentEvent.getStatus());
        treatmentEventDTO.setPatient(treatmentEvent.getPatient());
        treatmentEventDTO.setTreatment(treatmentEvent.getTreatment());
        treatmentEventDTO.setProcedureMedicine(treatmentEvent.getProcedureMedicine());
        return treatmentEventDTO;
    }

    public  List<TreatmentEvent> toTreatmentEventList(List<TreatmentEventDTOImpl> treatmentsEventDTOList) {
        return treatmentsEventDTOList.stream()
                .map(this::toTreatmentEvent)
                .collect(Collectors.toList());
    }

    public TreatmentEvent toTreatmentEvent(TreatmentEventDTOImpl treatmentEventDTO){
        TreatmentEvent treatmentEvent= new TreatmentEvent(treatmentEventDTO.getType(),treatmentEventDTO.getTreatmentTime(),treatmentEventDTO.getDose(),
                treatmentEventDTO.getStatus());
        treatmentEvent.setTreatment(treatmentEventDTO.getTreatment());
        treatmentEvent.setPatient(treatmentEventDTO.getPatient());
        treatmentEvent.setProcedureMedicine(treatmentEventDTO.getProcedureMedicine());
        return treatmentEvent;
    }

}
