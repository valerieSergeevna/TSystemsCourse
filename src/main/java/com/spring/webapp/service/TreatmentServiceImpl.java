package com.spring.webapp.service;

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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TreatmentServiceImpl {

    @Autowired
    private TreatmentDAOImpl treatmentDAO;
    @Autowired
    private ProcedureMedicineDAOImpl procedureMedicineDAO;

    @Autowired
    private PatientDAOImpl patientDAO;

    @Autowired
    private TreatmentEventDAOImpl treatmentEventDAO;


    @Transactional
    public List<TreatmentDTOImpl> getAll() {

        List<Treatment> treatmentList = treatmentDAO.getAll();
        List<TreatmentDTOImpl> treatmentDTOList = treatmentList.stream()
                .map(treatment -> new TreatmentDTOImpl(treatment.getTreatmentId(), treatment.getType(), treatment.getTimePattern(),
                        treatment.getPeriod(), treatment.getDose()))
                .collect(Collectors.toList());
        return treatmentDTOList;
    }


    @Transactional
    public void save(TreatmentDTOImpl treatmentDTO) {
        Treatment treatment = new Treatment();
        BeanUtils.copyProperties(treatmentDTO, treatment);
        int procedureMedicineID = procedureMedicineDAO.getIdByName(treatment.getProcedureMedicine().getName());
        if (procedureMedicineID > 0) {
            treatment.setProcedureMedicine(procedureMedicineDAO.get(procedureMedicineID));
            procedureMedicineDAO.delete(procedureMedicineDAO.getIdByName(""));
        } else {
            procedureMedicineDAO.save(new ProcedureMedicine(treatmentDTO.getTypeName(), treatmentDTO.getType()));
        }
        treatmentDAO.save(treatment);
    }


    @Transactional
    public void deleteWithPatientId(int id) {
        treatmentDAO.deleteWithPatientId(id);
    }

    @Transactional
    public void delete(int id) {
       List <TreatmentEvent> treatmentEventList = treatmentEventDAO.getAllByTreatmentID(id);
        for (TreatmentEvent treatmentEvent:treatmentEventList) {
            treatmentEventDAO.delete(treatmentEvent.getId());
        }
        treatmentDAO.delete(id);
    }


    @Transactional
    public TreatmentDTOImpl get(int id) {
        TreatmentDTOImpl treatmentDTO = new TreatmentDTOImpl();
        Treatment treatment = treatmentDAO.get(id);
        BeanUtils.copyProperties(treatment, treatmentDTO);
        treatmentDTO.setTypeName(treatment.getProcedureMedicine().getName());
        return treatmentDTO;
    }

    @Transactional
    public PatientDTOImpl getPatient(int id) {
        PatientDTOImpl patientDTO = new PatientDTOImpl();
        BeanUtils.copyProperties(treatmentDAO.getPatient(id), patientDTO);
        return patientDTO;
    }

    @Transactional
    public TreatmentDTOImpl createEmpty(PatientDTOImpl patientDTO) {
        TreatmentDTOImpl treatmentDTO = new TreatmentDTOImpl();
        Patient patient = new Patient();
        BeanUtils.copyProperties(patientDTO, patient);
        Treatment treatment = new Treatment();
        treatment.setType("treatment");
        ProcedureMedicine procedureMedicine = new ProcedureMedicine("", "treatment");
        procedureMedicineDAO.save(procedureMedicine);
        treatment.setProcedureMedicine(procedureMedicine);
        treatment.setPatient(patient);
        treatmentDAO.save(treatment);
        BeanUtils.copyProperties(treatment, treatmentDTO);
        treatmentDTO.setTypeName("");
        return treatmentDTO;
    }
}
