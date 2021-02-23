package com.spring.webapp.service;

import com.spring.exception.DataBaseException;
import com.spring.exception.ServerException;
import com.spring.webapp.dao.DoctorDAOImpl;
import com.spring.webapp.dao.PatientDAOImpl;
import com.spring.webapp.dto.DoctorDTOImpl;
import com.spring.webapp.dto.PatientDTOImpl;
import com.spring.webapp.entity.Doctor;
import com.spring.webapp.entity.Patient;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorUserServiceImpl extends AbstractUserService<DoctorDTOImpl> {
    private static final Logger logger = LoggerFactory.getLogger(DoctorUserServiceImpl.class);

    private DoctorDAOImpl doctorDAO;

    @Autowired
    public void setDoctorDAO(DoctorDAOImpl doctorDAO) {
        this.doctorDAO = doctorDAO;
    }

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
    public DoctorDTOImpl update(DoctorDTOImpl doctorDTO) throws DataBaseException {
        try {
            return toDoctorDTO(doctorDAO.update(toDoctor(doctorDTO)));
        } catch (HibernateException ex) {
            logger.error("[!DoctorServiceImpl 'update' method:" + ex.getMessage() + "!]");
            logger.error("STACK TRACE: " + Arrays.toString(ex.getStackTrace()));
            throw new DataBaseException(ex.getMessage());
        }
    }

    @Transactional
    public DoctorDTOImpl save(DoctorDTOImpl doctorDTO) throws DataBaseException {
        try {
            return toDoctorDTO(doctorDAO.save(toDoctor(doctorDTO)));
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
            return toDoctorDTO(doctorDAO.get(id));
        } catch (HibernateException ex) {
            logger.error("[!DoctorServiceImpl 'get' method:" + ex.getMessage() + "!]");
            logger.error("STACK TRACE: " + Arrays.toString(ex.getStackTrace()));
            throw new DataBaseException(ex.getMessage());
        }catch (NullPointerException ex){
            logger.error("[!DoctorServiceImpl 'get' method:" + ex.getMessage() + "!] - doctor doesn't exist");
            logger.error("STACK TRACE: " + Arrays.toString(ex.getStackTrace()));
            throw new DataBaseException(ex.getMessage());
        }
    }

    @Transactional
    public DoctorDTOImpl getByUserName(String name) throws DataBaseException, ServerException {
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

    public Doctor toDoctor(DoctorDTOImpl doctorDTO) {
        Doctor doctor = new Doctor(doctorDTO.getName(), doctorDTO.getSurname(),doctorDTO.getPosition(),doctorDTO.getUsername());
        if (doctorDTO.getId() > 0) {
            doctor.setId(doctorDTO.getId());
        }
        return doctor;
    }
}
