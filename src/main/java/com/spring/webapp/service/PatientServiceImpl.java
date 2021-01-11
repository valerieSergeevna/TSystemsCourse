package com.spring.webapp.service;

import com.spring.webapp.dao.*;
import com.spring.webapp.dto.PatientDTOImpl;
import com.spring.webapp.dto.TreatmentDTOImpl;
import com.spring.webapp.entity.Patient;
import com.spring.webapp.entity.ProcedureMedicine;
import com.spring.webapp.entity.Treatment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl {

    @Autowired
    private PatientDAOImpl patientDAO;
    @Autowired
    private TreatmentDAOImpl treatmentDAO;
    @Autowired
    private ProcedureMedicineDAOImpl procedureMedicineDAO;

    @Autowired
    private DoctorDAOImpl doctorDAO;

    @Transactional
    public List<PatientDTOImpl> getAll() {

        List<Patient> patientsList = patientDAO.getAll();
        List<PatientDTOImpl> patientDTOList = patientsList.stream()
                .map(patient -> new PatientDTOImpl(patient.getId(), patient.getName(),
                        patient.getSurname(), patient.getBirthDate(), patient.getDisease(), patient.getStatus(), treatmentDAO.toTreatmentDTOList(patient.getTreatments())))
                .collect(Collectors.toList());
        return patientDTOList;
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
    public void deleteTreatments(int id){
        List<Treatment> treatmentList = patientDAO.get(id).getTreatments();
        for (Treatment treatment : treatmentList) {
            treatmentDAO.delete(treatment.getTreatmentId());
        }
    }

    @Transactional
    public PatientDTOImpl get(int id) {
        PatientDTOImpl patientDTO = new PatientDTOImpl();
        Patient patient =patientDAO.get(id);
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
    public void saveOrUpdateTreatments(List<TreatmentDTOImpl> treatments, PatientDTOImpl patient) {
        patient.getTreatments().clear();
        patientDAO.get(patient.getId()).getTreatments().clear();
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
                        //  procedureMedicineDAO.save(new ProcedureMedicine(treatmentDTO.getTypeName(), treatmentDTO.getType()));
                        procedureMedicine = new ProcedureMedicine(treatment.getTypeName(), treatment.getType());
                        procedureMedicineDAO.save(procedureMedicine);
                    }
                    newTreatment.setProcedureMedicine(procedureMedicine);

                    return newTreatment;
                })
                .collect(Collectors.toList());

        for (Treatment treatment : treatmentList) {
            //treatmentDAO.delete(patient.getId());
            treatment.setPatient(patientDAO.get(patient.getId()));
            treatmentDAO.save(treatment);
            //patientDAO.get(id).addTreatment(treatment1);
        }
        patientDAO.get(patient.getId()).setTreatments(treatmentList);
        patient.setTreatments(treatmentDAO.toTreatmentDTOList(treatmentList));

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

}
