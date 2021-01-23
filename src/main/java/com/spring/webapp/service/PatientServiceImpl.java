package com.spring.webapp.service;

import com.spring.exception.DataBaseException;
import com.spring.exception.ServerException;
import com.spring.utils.TimeParser;
import com.spring.webapp.controller.MyController;
import com.spring.webapp.dao.*;
import com.spring.webapp.dto.DoctorDTOImpl;
import com.spring.webapp.dto.PatientDTOImpl;
import com.spring.webapp.dto.TreatmentDTOImpl;
import com.spring.webapp.entity.Doctor;
import com.spring.webapp.entity.Patient;
import com.spring.webapp.entity.ProcedureMedicine;
import com.spring.webapp.entity.Treatment;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class
PatientServiceImpl {

    private static final Logger logger = Logger.getLogger(PatientServiceImpl.class);

    @Autowired
    private PatientDAOImpl patientDAO;
    @Autowired
    private TreatmentDAOImpl treatmentDAO;
    @Autowired
    private ProcedureMedicineDAOImpl procedureMedicineDAO;

    @Autowired
    private DoctorDAOImpl doctorDAO;

    @Autowired
    private TreatmentEventDAOImpl treatmentEventDAO;

    @Transactional
    public List<PatientDTOImpl> getAll() throws DataBaseException {
        try {
            List<Patient> patientsList = patientDAO.getAll();
            return toPatientDTOList(patientsList);
        } catch (HibernateException ex) {
            logger.error("[!PatientServiceImpl 'getAll' method:" + ex.getMessage() + "!]");
            throw new DataBaseException(ex.getMessage());
        }
    }

    @Transactional
    public List<PatientDTOImpl> getAllByDoctorUserName(String name) throws DataBaseException {
        try {
            Doctor doctor = doctorDAO.getByUserName(name);
            List<Patient> patientsList = patientDAO.getAllByDoctorId(doctor.getId());
            return toPatientDTOList(patientsList);
        } catch (HibernateException ex) {
            logger.error("[!PatientServiceImpl 'getAllByDoctorUserName' method:" + ex.getMessage() + "!]");
            throw new DataBaseException(ex.getMessage());
        }
    }

    @Transactional
    public List<PatientDTOImpl> getBySurname(String surname) throws DataBaseException {
        try {
            List<Patient> patientsList = patientDAO.getBySurname(surname);
            return toPatientDTOList(patientsList);
        } catch (HibernateException ex) {
            logger.error("[!PatientServiceImpl 'getBySurname' method:" + ex.getMessage() + "!]");
            throw new DataBaseException(ex.getMessage());
        }
    }

    @Transactional
    public void save(PatientDTOImpl patientDTO) throws DataBaseException {
        Patient patient = new Patient();
        BeanUtils.copyProperties(patientDTO, patient);
        try {
            patientDAO.save(patient);
        } catch (HibernateException ex) {
            logger.error("[!PatientServiceImpl 'save' method:" + ex.getMessage() + "!]");
            throw new DataBaseException(ex.getMessage());
        }

    }

    @Transactional
    public void update(PatientDTOImpl patientDTO) {
        patientDTO.setDoctor(doctorDAO.get(patientDTO.getDoctor().getId()));
        Patient patient = toPatient(patientDTO);
        patientDAO.update(patient);
    }


    @Transactional
    public void delete(int id) {
        patientDAO.delete(id);
    }

    @Transactional
    public void deleteTreatments(int id) throws DataBaseException {
        try {
            List<Treatment> treatmentList = patientDAO.get(id).getTreatments();
            for (Treatment treatment : treatmentList) {
                treatmentDAO.delete(treatment.getTreatmentId());
            }
        } catch (HibernateException ex) {
            logger.error("[!PatientServiceImpl 'deleteTreatments' method:" + ex.getMessage() + "!]");
            throw new DataBaseException(ex.getMessage());
        }
    }

    @Transactional
    public PatientDTOImpl get(int id) throws DataBaseException {
        PatientDTOImpl patientDTO = new PatientDTOImpl();
        try {
            Patient patient = patientDAO.get(id);
            BeanUtils.copyProperties(patient, patientDTO);
            patientDTO.setTreatments(treatmentDAO.toTreatmentDTOList(patient.getTreatments()));
            return patientDTO;
        } catch (HibernateException ex) {
            logger.error("[!PatientServiceImpl 'get' method:" + ex.getMessage() + "!]");
            throw new DataBaseException(ex.getMessage());
        }
    }

  /*  @Transactional
    public List<TreatmentDTOImpl> getTreatments(int id) {//it's already exist, need to FIX
        List<Treatment> treatmentList = patientDAO.getTreatments(id);
        return treatmentList.stream()
                .map(treatment -> {TreatmentDTOImpl treatmentDTO = new TreatmentDTOImpl(treatment.getTreatmentId(), treatment.getType(),
                        treatment.getTimePattern(), treatment.getDose());
                treatmentDTO.setStartDate(TimeParser.fromLocalDateTimeToLocalDate(treatment.getStartDate()));
                treatmentDTO.setEndDate(TimeParser.fromLocalDateTimeToLocalDate(treatment.getEndDate()));
                return treatmentDTO;})
                .collect(Collectors.toList());
    }*/

    @Transactional
    public void saveOrUpdateTreatments(List<TreatmentDTOImpl> treatments, PatientDTOImpl patientDTO, DoctorDTOImpl doctor) {

        patientDTO.getTreatments().clear();
        Patient patient = patientDAO.get(patientDTO.getId());

        if (patient != null) {
            patient.getTreatments().clear();
        }
        patient = toPatient(patientDTO);

        List<Treatment> treatmentList = treatments.stream()
                .map(treatment ->
                {
                    Treatment newTreatment = new Treatment(treatment.getType(), treatment.getTimePattern(), treatment.getDose(),
                            TimeParser.fromLocalDateToLocalDateTime(treatment.getStartDate()),
                            TimeParser.fromLocalDateToLocalDateTime(treatment.getEndDate()));
                    newTreatment.setTreatmentId(treatment.getTreatmentId());
                    newTreatment.setStartDate(TimeParser.fromLocalDateToLocalDateTime(treatment.getStartDate()));
                    newTreatment.setEndDate(TimeParser.fromLocalDateToLocalDateTime(treatment.getEndDate()));
                    //in TreatmentService duplicated code
                    int procedureMedicineID = procedureMedicineDAO.getIdByName(treatment.getTypeName());
                    ProcedureMedicine procedureMedicine;
                    if (procedureMedicineID > 0) {
                        procedureMedicine = procedureMedicineDAO.get(procedureMedicineID);
                    } else {
                        procedureMedicine = new ProcedureMedicine(treatment.getTypeName(), treatment.getType());
                        procedureMedicineDAO.save(procedureMedicine);
                    }
                    newTreatment.setProcedureMedicine(procedureMedicine);

                    return newTreatment;
                })
                .collect(Collectors.toList());

        patient.setDoctor(doctorDAO.get(doctor.getId()));
        if (patientDAO.get(patientDTO.getId()) == null) {
            patientDAO.save(patient);
        } else {
            patientDAO.update(patient);
        }
        patientDTO.setId(patient.getId());

        for (Treatment treatment : treatmentList) {
            treatment.setPatient(patient);
            //       int oldPattern = treatmentDAO.get(treatment.getTreatmentId()).getTimePattern();
            if (treatmentDAO.get(treatment.getTreatmentId()) == null) {
                treatmentDAO.save(treatment);
                treatmentEventDAO.createTimeTable(treatment);
            } else {
                treatmentEventDAO.createTimeTable(treatment);
                treatmentDAO.update(treatment);
            }
        }

        patient.setTreatments(treatmentList);

        patientDTO.setTreatments(treatmentDAO.toTreatmentDTOList(treatmentList));
    }


    //convector's method from-to

    public List<PatientDTOImpl> toPatientDTOList(List<Patient> patientList) {
        return patientList.stream()
                .map(this::toPatientDTO)
                .collect(Collectors.toList());
    }

    public PatientDTOImpl toPatientDTO(Patient patient) {
        PatientDTOImpl patientDTO = new PatientDTOImpl(patient.getId(), patient.getName(),
                patient.getSurname(), patient.getAges(), patient.getDisease(), patient.getStatus(),
                treatmentDAO.toTreatmentDTOList(patient.getTreatments()), patient.getDoctor());
        patientDTO.setInsuranceNumber(patient.getInsuranceNumber());
        return patientDTO;
    }

    public Patient toPatient(PatientDTOImpl patientDTO) {
        Patient patient = new Patient(patientDTO.getName(), patientDTO.getSurname(), patientDTO.getAges(), patientDTO.getInsuranceNumber(),
                patientDTO.getDisease(), patientDTO.getStatus());
        if (patientDTO.getId() > 0) {
            patient.setId(patientDTO.getId());
        }
        patient.setDoctor(patientDTO.getDoctor());
        return patient;
    }

}
