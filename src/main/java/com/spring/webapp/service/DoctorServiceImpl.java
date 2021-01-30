package com.spring.webapp.service;

import com.spring.exception.DataBaseException;
import com.spring.webapp.dao.DoctorDAOImpl;
import com.spring.webapp.dao.EntityDAO;
import com.spring.webapp.dto.DoctorDTOImpl;
import com.spring.webapp.dto.EntityDTO;
import com.spring.webapp.dto.PatientDTOImpl;
import com.spring.webapp.entity.Doctor;
import com.spring.webapp.entity.Patient;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorServiceImpl {
    private static final Logger logger = Logger.getLogger(Doctor.class);

    @Autowired
    private DoctorDAOImpl doctorDAO;

    @Transactional
    public List<DoctorDTOImpl> getAll() throws DataBaseException {
        try {
            return toDoctorDTOList(doctorDAO.getAll());
        } catch (
                HibernateException ex) {
            logger.error("[!DoctorServiceImpl 'getAll' method:" + ex.getMessage() + "!]");
            logger.error("STACK TRACE: " + Arrays.toString(ex.getStackTrace()));
            throw new DataBaseException(ex.getMessage());
        }
    }

    @Transactional
    public DoctorDTOImpl save(DoctorDTOImpl doctorDTO) throws DataBaseException {
        Doctor doctor = new Doctor();
        BeanUtils.copyProperties(doctorDTO, doctor);
        try {
            return toDoctorDTO(doctorDAO.save(doctor));
        } catch (HibernateException ex) {
            logger.error("[!DoctorServiceImpl 'save' method:" + ex.getMessage() + "!]");
            logger.error("STACK TRACE: " + Arrays.toString(ex.getStackTrace()));
            throw new DataBaseException(ex.getMessage());
        }

    }

    @Transactional
    public void delete(int id) throws DataBaseException {
        try {
            doctorDAO.delete(id);
        } catch (HibernateException ex) {
            logger.error("[!DoctorServiceImpl 'delete' method:" + ex.getMessage() + "!]");
            logger.error("STACK TRACE: " + Arrays.toString(ex.getStackTrace()));
            throw new DataBaseException(ex.getMessage());
        }
    }

    @Transactional
    public DoctorDTOImpl get(int id) throws DataBaseException {
        DoctorDTOImpl doctorDTO = new DoctorDTOImpl();
        try {
            BeanUtils.copyProperties(doctorDAO.get(id), doctorDTO);
            return doctorDTO;
        } catch (HibernateException ex) {
            logger.error("[!DoctorServiceImpl 'get' method:" + ex.getMessage() + "!]");
            logger.error("STACK TRACE: " + Arrays.toString(ex.getStackTrace()));
            throw new DataBaseException(ex.getMessage());
        }
    }

    @Transactional
    public DoctorDTOImpl getByUserName(String name) throws DataBaseException {
        try {
            return toDoctorDTO(doctorDAO.getByUserName(name));
        }catch (HibernateException ex) {
            logger.error("[!DoctorServiceImpl 'getByUserName' method:" + ex.getMessage() + "!]");
            logger.error("STACK TRACE: " + Arrays.toString(ex.getStackTrace()));
            throw new DataBaseException(ex.getMessage());
        }
    }

    //convert from-to

    public DoctorDTOImpl toDoctorDTO(Doctor doctor) {
        return new DoctorDTOImpl(doctor.getId(), doctor.getName(), doctor.getSurname(), doctor.getPosition(), doctor.getUserName());
    }

    public List<DoctorDTOImpl> toDoctorDTOList(List<Doctor> doctorList) {
        return doctorList.stream()
                .map(doctor -> new DoctorDTOImpl(doctor.getId(), doctor.getName(), doctor.getSurname(), doctor.getPosition(), doctor.getUserName()))
                .collect(Collectors.toList());
    }
}
