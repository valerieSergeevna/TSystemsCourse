package com.spring.webapp.service;

import com.spring.exception.ClientException;
import com.spring.exception.DataBaseException;
import com.spring.utils.TimeParser;
import com.spring.webapp.EventStatus;
import com.spring.webapp.dao.*;
import com.spring.webapp.dto.DoctorDTOImpl;
import com.spring.webapp.dto.PatientDTOImpl;
import com.spring.webapp.dto.TreatmentDTOImpl;
import com.spring.webapp.dto.TreatmentEventDTOImpl;
import com.spring.webapp.entity.*;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TreatmentEventServiceImpl {

    private static final Logger logger = Logger.getLogger(TreatmentEventServiceImpl.class);

    @Autowired
    private TreatmentEventDAOImpl treatmentEventDAO;


    @Autowired
    private PatientServiceImpl patientService;

    @Transactional
    public List<TreatmentEventDTOImpl> getAll() throws DataBaseException {
        try {
            return toTreatmentEventDTOList(treatmentEventDAO.getAll());
        } catch (
                HibernateException ex) {
            logger.error("[!TreatmentEventServiceImpl 'getAll' method:" + ex.getMessage() + "!]");
            logger.error("STACK TRACE: " + Arrays.toString(ex.getStackTrace()));
            throw new DataBaseException(ex.getMessage());
        }
    }

    @Transactional
    public void save(TreatmentEventDTOImpl treatmentEventDTO) throws DataBaseException {
        TreatmentEvent treatmentEvent = new TreatmentEvent();
        try {
            BeanUtils.copyProperties(treatmentEventDTO, treatmentEvent);
            treatmentEventDAO.save(treatmentEvent);
        } catch (
                HibernateException ex) {
            logger.error("[!TreatmentEventServiceImpl 'save' method:" + ex.getMessage() + "!]");
            logger.error("STACK TRACE: " + Arrays.toString(ex.getStackTrace()));
            throw new DataBaseException(ex.getMessage());
        }
    }


    public void update(TreatmentEventDTOImpl treatmentEventDTO) throws DataBaseException {
        try {
            treatmentEventDAO.update(toTreatmentEvent(treatmentEventDTO));
        } catch (
                HibernateException ex) {
            logger.error("[!TreatmentEventServiceImpl 'update' method:" + ex.getMessage() + "!]");
            logger.error("STACK TRACE: " + Arrays.toString(ex.getStackTrace()));
            throw new DataBaseException(ex.getMessage());
        }

    }

    @Transactional
    public void delete(int id) throws DataBaseException {
        try {
            treatmentEventDAO.delete(id);
        } catch (
                HibernateException ex) {
            logger.error("[!TreatmentEventServiceImpl 'delete' method:" + ex.getMessage() + "!]");
            logger.error("STACK TRACE: " + Arrays.toString(ex.getStackTrace()));
            throw new DataBaseException(ex.getMessage());
        }
    }

    @Transactional
    public TreatmentEventDTOImpl get(int id) throws DataBaseException {
        TreatmentEventDTOImpl treatmentEventDTO = new TreatmentEventDTOImpl();
        try {
            BeanUtils.copyProperties(treatmentEventDAO.get(id), treatmentEventDTO);
            return treatmentEventDTO;
        } catch (
                HibernateException ex) {
            logger.error("[!TreatmentEventServiceImpl 'get' method:" + ex.getMessage() + "!]");
            logger.error("STACK TRACE: " + Arrays.toString(ex.getStackTrace()));
            throw new DataBaseException(ex.getMessage());
        }
    }

    @Transactional
    public List<TreatmentEventDTOImpl> getByType(String treatmentType) throws DataBaseException {
        try {
            return toTreatmentEventDTOList(treatmentEventDAO.getByType(treatmentType));
        } catch (
                HibernateException ex) {
            logger.error("[!TreatmentEventServiceImpl 'getByType' method:" + ex.getMessage() + "!]");
            logger.error("STACK TRACE: " + Arrays.toString(ex.getStackTrace()));
            throw new DataBaseException(ex.getMessage());
        }
    }


    public List<TreatmentEventDTOImpl> getByPatient(int patientId) throws DataBaseException {
        try {
            return toTreatmentEventDTOList(treatmentEventDAO.getByPatient(patientId));
        } catch (HibernateException ex) {
            logger.error("[!TreatmentEventServiceImpl 'getByPatient' method:" + ex.getMessage() + "!]");
            logger.error("STACK TRACE: " + Arrays.toString(ex.getStackTrace()));
            throw new DataBaseException(ex.getMessage());
        }
    }

    public List<TreatmentEventDTOImpl> getNearestEvents(LocalDateTime time) throws DataBaseException {
        try {
            return toTreatmentEventDTOList(treatmentEventDAO.getNearestEvents(time));
        } catch (HibernateException ex) {
            logger.error("[!TreatmentEventServiceImpl 'getNearestEvents' method:" + ex.getMessage() + "!]");
            logger.error("STACK TRACE: " + Arrays.toString(ex.getStackTrace()));
            throw new DataBaseException(ex.getMessage());
        }
    }


    public List<TreatmentEventDTOImpl> getTodayEvents(LocalDateTime time) throws DataBaseException {
        try {
            return toTreatmentEventDTOList(treatmentEventDAO.getTodayEvents(time));
        } catch (HibernateException ex) {
            logger.error("[!TreatmentEventServiceImpl 'getTodayEvents' method:" + ex.getMessage() + "!]");
            logger.error("STACK TRACE: " + Arrays.toString(ex.getStackTrace()));
            throw new DataBaseException(ex.getMessage());
        }
    }

    //methods for controller
    @Transactional
    public void updateStatus(int id, String status) throws DataBaseException {
        TreatmentEventDTOImpl treatmentEventDTO = get(id);
        treatmentEventDTO.setStatus(status);
        update(treatmentEventDTO);
    }

    @Transactional
    public void setCancelReason(TreatmentEventDTOImpl treatmentEventDTO) throws DataBaseException {
        TreatmentEventDTOImpl newTreatmentEventDTO = get(treatmentEventDTO.getId());
        newTreatmentEventDTO.setStatus("canceled");
        newTreatmentEventDTO.setCancelReason(treatmentEventDTO.getCancelReason());
        update(newTreatmentEventDTO);
    }

    @Transactional
    public List<TreatmentEventDTOImpl> showTodayTreatments() throws DataBaseException {
        LocalDateTime nowDay = LocalDateTime.now();
        return getTodayEvents(LocalDateTime.of(nowDay.getYear(), nowDay.getMonth(),
                nowDay.getDayOfMonth(), 8, 0, 0));
    }

    @Transactional
    public List<TreatmentEventDTOImpl> showNearestHourTreatments() throws DataBaseException {
        LocalDateTime nowDay = LocalDateTime.now();
        return getNearestEvents(LocalDateTime.of(nowDay.getYear(), nowDay.getMonth(),
                nowDay.getDayOfMonth(), nowDay.getHour(), 0, 0));
    }

    @Transactional
    public List<TreatmentEventDTOImpl> findBySurname(String surname) throws DataBaseException {
        List<PatientDTOImpl> patientDTOList = patientService.getBySurname(surname);
        List<TreatmentEventDTOImpl> allEvents = new ArrayList<>();
        for (PatientDTOImpl patientDTO : patientDTOList) {
            //O(n^2)???????????
            allEvents.addAll(getByPatient(patientDTO.getId()));
        }
        return allEvents;
    }

    @Transactional
    public List<TreatmentEventDTOImpl> showTreatmentsByDate(String time) throws DataBaseException, ClientException {
        try {
            //use this method to get another date
            return getTodayEvents(TimeParser.fromLocalDateToLocalDateTime(LocalDate.parse(time)));
        } catch (DateTimeParseException ex) {
            throw new ClientException("incorrect input date");
        }
    }

    //convector's method from-to

    public List<TreatmentEventDTOImpl> toTreatmentEventDTOList(List<TreatmentEvent> treatmentsEventList) {
        return treatmentsEventList.stream()
                .map(this::toTreatmentEventDTO)
                .collect(Collectors.toList());
    }

    public TreatmentEventDTOImpl toTreatmentEventDTO(TreatmentEvent treatmentEvent) {
        TreatmentEventDTOImpl treatmentEventDTO = new TreatmentEventDTOImpl(treatmentEvent.getId(), treatmentEvent.getType(),
                treatmentEvent.getTreatmentTime(), treatmentEvent.getDose(), treatmentEvent.getStatus().getTitle());
        treatmentEventDTO.setPatient(treatmentEvent.getPatient());
        treatmentEventDTO.setTreatment(treatmentEvent.getTreatment());
        treatmentEventDTO.setProcedureMedicine(treatmentEvent.getProcedureMedicine());
        treatmentEventDTO.setStatus(treatmentEvent.getStatus().getTitle());
        treatmentEventDTO.setCancelReason(treatmentEvent.getCancelReason());
        return treatmentEventDTO;
    }

    public List<TreatmentEvent> toTreatmentEventList(List<TreatmentEventDTOImpl> treatmentsEventDTOList) {
        return treatmentsEventDTOList.stream()
                .map(this::toTreatmentEvent)
                .collect(Collectors.toList());
    }

    public TreatmentEvent toTreatmentEvent(TreatmentEventDTOImpl treatmentEventDTO) {
        TreatmentEvent treatmentEvent = new TreatmentEvent(treatmentEventDTO.getType(), treatmentEventDTO.getTreatmentTime(), treatmentEventDTO.getDose(),
                EventStatus.fromTitle(treatmentEventDTO.getStatus()), treatmentEventDTO.getCancelReason());
        if (treatmentEventDTO.getId() > 0) {
            treatmentEvent.setId(treatmentEventDTO.getId());
        }
        treatmentEvent.setTreatment(treatmentEventDTO.getTreatment());
        treatmentEvent.setPatient(treatmentEventDTO.getPatient());
        treatmentEvent.setProcedureMedicine(treatmentEventDTO.getProcedureMedicine());
        treatmentEvent.setStatus( EventStatus.fromTitle(treatmentEventDTO.getStatus()));
        return treatmentEvent;
    }


}
