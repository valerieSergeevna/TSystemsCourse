package com.spring.webapp.service;

import com.spring.exception.DataBaseException;
import com.spring.utils.TimeParser;
import com.spring.webapp.dao.PatientDAOImpl;
import com.spring.webapp.dao.ProcedureMedicineDAOImpl;
import com.spring.webapp.dao.TreatmentDAOImpl;
import com.spring.webapp.dao.TreatmentEventDAOImpl;
import com.spring.webapp.dto.PatientDTOImpl;
import com.spring.webapp.dto.TreatmentDTOImpl;
import com.spring.webapp.entity.Patient;
import com.spring.webapp.entity.ProcedureMedicine;
import com.spring.webapp.entity.Treatment;
import com.spring.webapp.entity.TreatmentEvent;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TreatmentServiceImpl {
    private static final Logger logger = Logger.getLogger(TreatmentServiceImpl.class);

    @Autowired
    private TreatmentDAOImpl treatmentDAO;
    @Autowired
    private ProcedureMedicineDAOImpl procedureMedicineDAO;
    @Autowired
    private TreatmentEventDAOImpl treatmentEventDAO;


    @Transactional
    public List<TreatmentDTOImpl> getAll() throws DataBaseException {
        try {
            return toTreatmentDTOList(treatmentDAO.getAll());
        } catch (HibernateException ex) {
            logger.error("[!TreatmentServiceImpl 'getAll' method:" + ex.getMessage() + "!]");
            logger.error("STACK TRACE: " + Arrays.toString(ex.getStackTrace()));
            throw new DataBaseException(ex.getMessage());
        }
    }


    @Transactional
    public void save(TreatmentDTOImpl treatmentDTO) throws DataBaseException {
        Treatment treatment = new Treatment();
        BeanUtils.copyProperties(treatmentDTO, treatment);
        try {
            int procedureMedicineID = procedureMedicineDAO.getIdByName(treatment.getProcedureMedicine().getName());
            if (procedureMedicineID > 0) {
                treatment.setProcedureMedicine(procedureMedicineDAO.get(procedureMedicineID));
                procedureMedicineDAO.delete(procedureMedicineDAO.getIdByName(""));
            } else {
                procedureMedicineDAO.save(new ProcedureMedicine(treatmentDTO.getTypeName(), treatmentDTO.getType()));
            }
            treatmentDAO.save(treatment);
        } catch (HibernateException ex) {
            logger.error("[!TreatmentServiceImpl 'save' method:" + ex.getMessage() + "!]");
            logger.error("STACK TRACE: " + Arrays.toString(ex.getStackTrace()));
            throw new DataBaseException(ex.getMessage());
        }
    }


    @Transactional
    public void deleteWithPatientId(int id) {
        treatmentDAO.deleteWithPatientId(id);
    }

    @Transactional
    public void delete(int id) throws DataBaseException {
        try {
            List<TreatmentEvent> treatmentEventList = treatmentEventDAO.getAllByTreatmentID(id);
            for (TreatmentEvent treatmentEvent : treatmentEventList) {
                treatmentEventDAO.delete(treatmentEvent.getId());
            }
            treatmentDAO.delete(id);
        } catch (HibernateException ex) {
            logger.error("[!TreatmentServiceImpl 'delete' method:" + ex.getMessage() + "!]");
            logger.error("STACK TRACE: " + Arrays.toString(ex.getStackTrace()));
            throw new DataBaseException(ex.getMessage());
        }
    }


    @Transactional
    public TreatmentDTOImpl get(int id) throws DataBaseException {
        TreatmentDTOImpl treatmentDTO = new TreatmentDTOImpl();
        try {
            Treatment treatment = treatmentDAO.get(id);
            BeanUtils.copyProperties(treatment, treatmentDTO);
            treatmentDTO.setTypeName(treatment.getProcedureMedicine().getName());
            return treatmentDTO;
        } catch (HibernateException ex) {
            logger.error("[!TreatmentServiceImpl 'get' method:" + ex.getMessage() + "!]");
            logger.error("STACK TRACE: " + Arrays.toString(ex.getStackTrace()));
            throw new DataBaseException(ex.getMessage());
        }
    }

    @Transactional
    public PatientDTOImpl getPatient(int id) throws DataBaseException {
        PatientDTOImpl patientDTO = new PatientDTOImpl();
        try {
            BeanUtils.copyProperties(treatmentDAO.getPatient(id), patientDTO);
        } catch (HibernateException ex) {
            logger.error("[!TreatmentServiceImpl 'getPatient' method:" + ex.getMessage() + "!]");
            logger.error("STACK TRACE: " + Arrays.toString(ex.getStackTrace()));
            throw new DataBaseException(ex.getMessage());
        }
        return patientDTO;
    }

    public List<TreatmentDTOImpl> toTreatmentDTOList(List<Treatment> treatmentList) {
        return treatmentList.stream()
                .map(this::toTreatmentDTO)
                .collect(Collectors.toList());
    }

    public TreatmentDTOImpl toTreatmentDTO(Treatment treatment) {
        TreatmentDTOImpl treatmentDTO = new TreatmentDTOImpl(treatment.getTreatmentId(), treatment.getType(), treatment.getTimePattern(),
                treatment.getDose());
        treatmentDTO.setStartDate(TimeParser.fromLocalDateTimeToLocalDate(treatment.getStartDate()));
        treatmentDTO.setEndDate(TimeParser.fromLocalDateTimeToLocalDate(treatment.getEndDate()));
        return treatmentDTO;
    }
}
