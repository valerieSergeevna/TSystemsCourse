package com.spring.webapp.service;

import com.spring.webapp.dao.EntityDAO;
import com.spring.webapp.entity.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class DoctorServiceImpl implements EntityService<Doctor> {

    @Autowired
    private EntityDAO doctorDAO;

    @Override
    @Transactional
    public List<Doctor> getAll() {
        return doctorDAO.getAll();
    }

    @Override
    @Transactional
    public void save(Doctor doctor) {
        doctorDAO.save(doctor);
    }

    @Override
    @Transactional
    public void delete(int id) {
        doctorDAO.delete(id);
    }

    @Override
    @Transactional
    public Doctor get(int id) {
        return (Doctor) doctorDAO.get(id);
    }


}
