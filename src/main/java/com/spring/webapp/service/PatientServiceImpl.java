package com.spring.webapp.service;

import com.spring.webapp.dao.EntityDAO;
import com.spring.webapp.dao.PatientDAOImpl;
import com.spring.webapp.dto.DoctorDTOImpl;
import com.spring.webapp.dto.EntityDTO;
import com.spring.webapp.dto.PatientDTOImpl;
import com.spring.webapp.entity.Doctor;
import com.spring.webapp.entity.Patient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements EntityService<PatientDTOImpl> {

    @Autowired
    @Qualifier("patientDAOImpl")
    private EntityDAO patientDAO;

    @Override
    @Transactional
    public List<PatientDTOImpl> getAll() {

        List<Patient> patientsList = patientDAO.getAll();
        List<PatientDTOImpl> patientDTOList = patientsList.stream()
                .map(patient -> new PatientDTOImpl(patient.getId(),patient.getName(),
                        patient.getSurname(),patient.getDisease(),patient.getStatus()))
                .collect(Collectors.toList());
        return patientDTOList;
    }

    @Override
    @Transactional
    public void save(PatientDTOImpl patientDTO) {
        Patient patient = new Patient();
        BeanUtils.copyProperties(patientDTO, patient);
        patientDAO.save(patient);
    }

    @Override
    @Transactional
    public void delete(int id) {
        patientDAO.delete(id);
    }

    @Override
    @Transactional
    public PatientDTOImpl get(int id) {
        EntityDTO patientDTO = new PatientDTOImpl();
        BeanUtils.copyProperties(patientDAO.get(id),patientDTO);
        return (PatientDTOImpl) patientDTO;
    }
}
