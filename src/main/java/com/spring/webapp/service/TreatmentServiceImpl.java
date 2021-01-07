package com.spring.webapp.service;

import com.spring.webapp.dao.EntityDAO;
import com.spring.webapp.dao.TreatmentDAOImpl;
import com.spring.webapp.dto.EntityDTO;
import com.spring.webapp.dto.PatientDTOImpl;
import com.spring.webapp.dto.TreatmentDTOImpl;
import com.spring.webapp.entity.Patient;
import com.spring.webapp.entity.Treatment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TreatmentServiceImpl {

    @Autowired
    private TreatmentDAOImpl treatmentDAO;


    @Transactional
    public List<TreatmentDTOImpl> getAll() {

        List<Treatment> treatmentList = treatmentDAO.getAll();
        List<TreatmentDTOImpl> treatmentDTOList = treatmentList.stream()
                .map(treatment -> new TreatmentDTOImpl(treatment.getId(), treatment.getType(),treatment.getTimePattern(),
                        treatment.getPeriod(),treatment.getDose()))
                .collect(Collectors.toList());
        return treatmentDTOList;
    }


    @Transactional
    public void save(TreatmentDTOImpl treatmentDTO) {
        Treatment treatment = new Treatment();
        BeanUtils.copyProperties(treatmentDTO, treatment);
        treatmentDAO.save(treatment);
    }


    @Transactional
    public void delete(int id) {
        treatmentDAO.delete(id);
    }


    @Transactional
    public TreatmentDTOImpl get(int id) {
        TreatmentDTOImpl treatmentDTO = new TreatmentDTOImpl();
        BeanUtils.copyProperties(treatmentDAO.get(id),treatmentDTO);
        return treatmentDTO;
    }

    @Transactional
    public PatientDTOImpl getPatient(int id) {
        PatientDTOImpl patientDTO = new PatientDTOImpl();
        BeanUtils.copyProperties(((TreatmentDAOImpl)treatmentDAO).getPatient(id),patientDTO);
        return patientDTO;
    }
}
