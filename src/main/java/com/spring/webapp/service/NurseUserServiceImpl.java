package com.spring.webapp.service;

import com.spring.exception.DataBaseException;
import com.spring.exception.ServerException;
import com.spring.webapp.dao.NurseDAOImpl;
import com.spring.webapp.dto.NurseDTOImpl;
import com.spring.webapp.entity.Doctor;
import com.spring.webapp.entity.Nurse;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NurseUserServiceImpl extends AbstractUserService<NurseDTOImpl> {
    private static final Logger logger = Logger.getLogger(NurseUserServiceImpl.class);

    @Autowired
    private NurseDAOImpl nurseDAO;

    @Transactional
    public List<NurseDTOImpl> getAll() throws DataBaseException {
        try {
            return toNurseDTOList(nurseDAO.getAll());
        } catch (
                HibernateException ex) {
            logger.error("[!NurseServiceImpl 'getAll' method:" + ex.getMessage() + "!]");
            logger.error("STACK TRACE: " + Arrays.toString(ex.getStackTrace()));
            throw new DataBaseException(ex.getMessage());
        }
    }

    @Transactional
    public NurseDTOImpl save(NurseDTOImpl nurseDTO) throws DataBaseException {
        try {
            return toNurseDTO(nurseDAO.save(toNurse(nurseDTO)));
        } catch (HibernateException ex) {
            logger.error("[!NurseServiceImpl 'save' method:" + ex.getMessage() + "!]");
            logger.error("STACK TRACE: " + Arrays.toString(ex.getStackTrace()));
            throw new DataBaseException(ex.getMessage());
        }
    }

    @Transactional
    public void delete(int id) throws DataBaseException {
        try {
            nurseDAO.delete(id);
        } catch (HibernateException ex) {
            logger.error("[!NurseServiceImpl 'delete' method:" + ex.getMessage() + "!]");
            logger.error("STACK TRACE: " + Arrays.toString(ex.getStackTrace()));
            throw new DataBaseException(ex.getMessage());
        }
    }

    @Transactional
    public NurseDTOImpl get(int id) throws DataBaseException {
        try {
            return toNurseDTO(nurseDAO.get(id));
        } catch (HibernateException ex) {
            logger.error("[!NurseServiceImpl 'get' method:" + ex.getMessage() + "!]");
            logger.error("STACK TRACE: " + Arrays.toString(ex.getStackTrace()));
            throw new DataBaseException(ex.getMessage());
        }
    }

    @Transactional
    public NurseDTOImpl getByUserName(String name) throws DataBaseException, ServerException {
        try {
            return toNurseDTO(nurseDAO.getByUserName(name));
        }catch (HibernateException ex) {
            logger.error("[!NurseServiceImpl 'getByUserName' method:" + ex.getMessage() + "!]");
            logger.error("STACK TRACE: " + Arrays.toString(ex.getStackTrace()));
            throw new DataBaseException(ex.getMessage());
        }
    }

    @Transactional
    public NurseDTOImpl update(NurseDTOImpl UserDTO) throws DataBaseException {
        try {
            return toNurseDTO(nurseDAO.save(toNurse(UserDTO)));
        } catch (HibernateException ex) {
            logger.error("[!NurseServiceImpl 'update' method:" + ex.getMessage() + "!]");
            logger.error("STACK TRACE: " + Arrays.toString(ex.getStackTrace()));
            throw new DataBaseException(ex.getMessage());
        }
    }

    //convert from-to

    public NurseDTOImpl toNurseDTO(Nurse nurse) {
        return new NurseDTOImpl(nurse.getId(), nurse.getName(), nurse.getSurname(), nurse.getPosition(), nurse.getUserName());
    }

    public List<NurseDTOImpl> toNurseDTOList(List<Nurse> nurseList) {
        return nurseList.stream()
                .map(nurse -> new NurseDTOImpl(nurse.getId(), nurse.getName(), nurse.getSurname(), nurse.getPosition(), nurse.getUserName()))
                .collect(Collectors.toList());
    }

    public Nurse toNurse(NurseDTOImpl nurseDTO) {
        Nurse nurse = new Nurse(nurseDTO.getName(), nurseDTO.getSurname(),nurseDTO.getPosition(),nurseDTO.getUsername());
        if (nurseDTO.getId() > 0) {
            nurse.setId(nurseDTO.getId());
        }
        return nurse;
    }
}
