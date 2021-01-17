package com.spring.webapp.service;

import com.spring.webapp.dao.*;
import com.spring.webapp.dto.PatientDTOImpl;
import com.spring.webapp.dto.TreatmentDTOImpl;
import com.spring.webapp.dto.TreatmentEventDTOImpl;
import com.spring.webapp.entity.Patient;
import com.spring.webapp.entity.ProcedureMedicine;
import com.spring.webapp.entity.Treatment;
import com.spring.webapp.entity.TreatmentEvent;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class
PatientServiceImpl {

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
    public List<PatientDTOImpl> getAll() {

        List<Patient> patientsList = patientDAO.getAll();
       /* List<PatientDTOImpl> patientDTOList = patientsList.stream()
                .map(patient -> new PatientDTOImpl(patient.getId(), patient.getName(),
                        patient.getSurname(), patient.getBirthDate(), patient.getDisease(), patient.getStatus(), treatmentDAO.toTreatmentDTOList(patient.getTreatments())))
                .collect(Collectors.toList());
        return patientDTOList;*/
        return toPatientDTOList(patientsList);
    }

    @Transactional
    public List<PatientDTOImpl> getBySurname(String surname) {
        List<Patient> patientsList = patientDAO.getBySurname(surname);
       /* List<PatientDTOImpl> patientDTOList = patientsList.stream()
                .map(patient -> new PatientDTOImpl(patient.getId(), patient.getName(),
                        patient.getSurname(), patient.getBirthDate(), patient.getDisease(), patient.getStatus(), treatmentDAO.toTreatmentDTOList(patient.getTreatments())))
                .collect(Collectors.toList());
        return patientDTOList;*/
        return toPatientDTOList(patientsList);
    }

    @Transactional
    public void save(PatientDTOImpl patientDTO) {
        Patient patient = new Patient();
        BeanUtils.copyProperties(patientDTO, patient);
        patientDAO.save(patient);
    }

    @Transactional
    public void delete(int id) {
        patientDAO.delete(id);
    }

    @Transactional
    public void deleteTreatments(int id) {
        List<Treatment> treatmentList = patientDAO.get(id).getTreatments();
        for (Treatment treatment : treatmentList) {
            treatmentDAO.delete(treatment.getTreatmentId());
        }
    }

    @Transactional
    public PatientDTOImpl get(int id) {
        PatientDTOImpl patientDTO = new PatientDTOImpl();
        Patient patient = patientDAO.get(id);
        BeanUtils.copyProperties(patient, patientDTO);
        patientDTO.setTreatments(treatmentDAO.toTreatmentDTOList(patient.getTreatments()));
        return patientDTO;
    }

    @Transactional
    public List<TreatmentDTOImpl> getTreatments(int id) {//it's already exist, need to FIX
        List<Treatment> treatmentList = patientDAO.getTreatments(id);
        return treatmentList.stream()
                .map(treatment -> new TreatmentDTOImpl(treatment.getTreatmentId(), treatment.getType(),
                        treatment.getTimePattern(), treatment.getPeriod(), treatment.getDose()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void saveOrUpdateTreatments(List<TreatmentDTOImpl> treatments, PatientDTOImpl patientDTO) {
        //TODO: ADD STATUS AND INSURANCE
        patientDTO.getTreatments().clear();
        Patient patient = patientDAO.get(patientDTO.getId());

        if (patient != null) {
            patient.getTreatments().clear();
        } else {
            patient = new Patient();
            BeanUtils.copyProperties(patientDTO, patient);
        }

        List<Treatment> treatmentList = treatments.stream()
                .map(treatment ->
                {
                    Treatment newTreatment = new Treatment(treatment.getType(), treatment.getTimePattern(), treatment.getPeriod(), treatment.getDose());
                    newTreatment.setTreatmentId(treatment.getTreatmentId());
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
//SET DOCTOR
        patient.setDoctor(doctorDAO.get(1));
        ////
        patientDAO.save(patient);
        patientDTO.setId(patient.getId());

        for (Treatment treatment : treatmentList) {
            treatment.setPatient(patient);
           if (treatmentDAO.get(treatment.getTreatmentId()) != null) {
                treatmentDAO.update(treatment);
            } else {
                treatmentDAO.save(treatment);
            }
            List<TreatmentEvent> treatmentEvent = treatmentEventDAO.getAllByTreatmentID(treatment.getTreatmentId());
            TreatmentEvent gottenTreatmentEvent = treatmentEventDAO.get(treatment.getTreatmentId());

            //  treatmentEventDAO.deleteByTreatment(treatment.getTreatmentId());
            treatmentEventDAO.createTimeTable(treatment);
        }

        patient.setTreatments(treatmentList);

        patientDTO.setTreatments(treatmentDAO.toTreatmentDTOList(treatmentList));
    }

    @Transactional
    public PatientDTOImpl createEmpty() {
        Patient patient = new Patient();
        patient.setDoctor(doctorDAO.get(1));
        patientDAO.save(patient);
        PatientDTOImpl patientDTO = new PatientDTOImpl();
        BeanUtils.copyProperties(patient, patientDTO);
        return patientDTO;
    }

    //convector's method from-to

    public List<PatientDTOImpl> toPatientDTOList(List<Patient> patientList) {
        return patientList.stream()
                .map(this::toPatientDTO)
                .collect(Collectors.toList());
    }

    public PatientDTOImpl toPatientDTO(Patient patient) {
        PatientDTOImpl patientDTO = new PatientDTOImpl(patient.getId(), patient.getName(),
                patient.getSurname(), patient.getBirthDate(), patient.getDisease(), patient.getStatus(), treatmentDAO.toTreatmentDTOList(patient.getTreatments()));
        patientDTO.setInsuranceNumber(patient.getInsuranceNumber());
        return patientDTO;
    }

   /* public List<TreatmentEvent> toTreatmentEventList(List<TreatmentEventDTOImpl> treatmentsEventDTOList) {
        return treatmentsEventDTOList.stream()
                .map(this::toTreatmentEvent)
                .collect(Collectors.toList());
    }

    public Patient toPatient(PatientDTOImpl patientDTO) {
        TreatmentEvent treatmentEvent = new TreatmentEvent(patientDTO.getType(), patientDTO.getTreatmentTime(), patientDTO.getDose(),
                patientDTO.getStatus(), patientDTO.getCancelReason());
        if (patientDTO.getId() > 0) {
            treatmentEvent.setId(patientDTO.getId());
        }
        treatmentEvent.setTreatment(patientDTO.getTreatment());
        treatmentEvent.setPatient(patientDTO.getPatient());
        treatmentEvent.setProcedureMedicine(patientDTO.getProcedureMedicine());
        treatmentEvent.setStatus(patientDTO.getStatus());
        return treatmentEvent;
    }*/

}
