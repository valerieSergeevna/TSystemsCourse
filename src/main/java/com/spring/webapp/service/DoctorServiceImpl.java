package com.spring.webapp.service;

import com.spring.webapp.dao.DoctorDAOImpl;
import com.spring.webapp.dao.EntityDAO;
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
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorServiceImpl {

    @Autowired
    private DoctorDAOImpl doctorDAO;

    @Transactional
    public List<DoctorDTOImpl> getAll() {
        List<Doctor> doctorList = doctorDAO.getAll();
        List<DoctorDTOImpl> doctorDTOList = doctorList.stream()
                .map(doctor -> new DoctorDTOImpl(doctor.getId(),doctor.getName(),doctor.getSurname(),doctor.getPosition()))
                .collect(Collectors.toList());
        return doctorDTOList;
    }

    @Transactional
    public void save(DoctorDTOImpl doctorDTO) {
        Doctor doctor = new Doctor();
        BeanUtils.copyProperties(doctorDTO, doctor);
        doctorDAO.save(doctor);
    }

    @Transactional
    public void delete(int id) {
        doctorDAO.delete(id);
    }

    @Transactional
    public DoctorDTOImpl get(int id) {
        DoctorDTOImpl doctorDTO = new DoctorDTOImpl();
        BeanUtils.copyProperties(doctorDAO.get(id),doctorDTO);
        return doctorDTO;
    }
}
