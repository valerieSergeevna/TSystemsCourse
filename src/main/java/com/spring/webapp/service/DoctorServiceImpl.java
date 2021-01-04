package com.spring.webapp.service;

import com.spring.webapp.dao.EntityDAO;
import com.spring.webapp.dto.DoctorDTOImpl;
import com.spring.webapp.dto.EntityDTO;
import com.spring.webapp.entity.Doctor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class DoctorServiceImpl implements EntityService<DoctorDTOImpl> {

    @Autowired
    private EntityDAO doctorDAO;

    @Override
    @Transactional
    public List<DoctorDTOImpl> getAll() {
        return doctorDAO.getAll();
    }

    @Override
    @Transactional
    public void save(DoctorDTOImpl doctorDTO) {
        Doctor doctor = new Doctor();
        BeanUtils.copyProperties(doctorDTO, doctor);
        doctorDAO.save(doctor);
    }

    @Override
    @Transactional
    public void delete(int id) {
        doctorDAO.delete(id);
    }

    @Override
    @Transactional
    public DoctorDTOImpl get(int id) {
        EntityDTO doctorDTO = new DoctorDTOImpl();
        BeanUtils.copyProperties(doctorDAO.get(id),doctorDTO);
        return (DoctorDTOImpl) doctorDTO;
    }
}
