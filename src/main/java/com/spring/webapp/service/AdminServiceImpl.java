package com.spring.webapp.service;

import com.spring.exception.DataBaseException;
import com.spring.exception.ServerException;
import com.spring.webapp.dao.AdminDAOImpl;
import com.spring.webapp.dao.DoctorDAOImpl;
import com.spring.webapp.dto.AdminDTOImpl;
import com.spring.webapp.dto.DoctorDTOImpl;
import com.spring.webapp.entity.Admin;
import com.spring.webapp.entity.Doctor;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl extends AbstractUserService<AdminDTOImpl>{
    private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    private AdminDAOImpl adminDAO;

    @Autowired
    public void setAdminDAO(AdminDAOImpl adminDAO) {
        this.adminDAO = adminDAO;
    }

    @Transactional
    public List<AdminDTOImpl> getAll() throws DataBaseException {
        try {
            return toAdminDTOList(adminDAO.getAll());
        } catch (
                HibernateException ex) {
            logger.error("[!AdminServiceImpl 'getAll' method:" + ex.getMessage() + "!]");
            logger.error("STACK TRACE: " + Arrays.toString(ex.getStackTrace()));
            throw new DataBaseException(ex.getMessage());
        }
    }

    @Transactional
    public AdminDTOImpl update(AdminDTOImpl adminDTO) throws DataBaseException {
        try {
            return toAdminDTO(adminDAO.update(toAdmin(adminDTO)));
        } catch (HibernateException ex) {
            logger.error("[!AdminServiceImpl 'update' method:" + ex.getMessage() + "!]");
            logger.error("STACK TRACE: " + Arrays.toString(ex.getStackTrace()));
            throw new DataBaseException(ex.getMessage());
        }
    }

    @Transactional
    public AdminDTOImpl save(AdminDTOImpl adminDTO) throws DataBaseException {
        try {
            return toAdminDTO(adminDAO.save(toAdmin(adminDTO)));
        } catch (HibernateException ex) {
            logger.error("[!AdminServiceImpl 'save' method:" + ex.getMessage() + "!]");
            logger.error("STACK TRACE: " + Arrays.toString(ex.getStackTrace()));
            throw new DataBaseException(ex.getMessage());
        }

    }

    @Transactional
    public void delete(int id) throws DataBaseException {
        try {
            adminDAO.delete(id);
        } catch (HibernateException ex) {
            logger.error("[!AdminServiceImpl 'delete' method:" + ex.getMessage() + "!]");
            logger.error("STACK TRACE: " + Arrays.toString(ex.getStackTrace()));
            throw new DataBaseException(ex.getMessage());
        }
    }

    @Transactional
    public AdminDTOImpl get(int id) throws DataBaseException {
        try {
            return toAdminDTO(adminDAO.get(id));
        } catch (HibernateException ex) {
            logger.error("[!AdminServiceImpl 'get' method:" + ex.getMessage() + "!]");
            logger.error("STACK TRACE: " + Arrays.toString(ex.getStackTrace()));
            throw new DataBaseException(ex.getMessage());
        }
    }

    @Transactional
    public AdminDTOImpl getByUserName(String name) throws DataBaseException, ServerException {
        try {
            return toAdminDTO(adminDAO.getByUserName(name));
        }catch (HibernateException ex) {
            logger.error("[!AdminServiceImpl 'getByUserName' method:" + ex.getMessage() + "!]");
            logger.error("STACK TRACE: " + Arrays.toString(ex.getStackTrace()));
            throw new DataBaseException(ex.getMessage());
        }
    }

    //convert from-to

    public AdminDTOImpl toAdminDTO(Admin admin) {
        return new AdminDTOImpl(admin.getId(), admin.getName(), admin.getSurname(), admin.getPosition(), admin.getUserName());
    }

    public List<AdminDTOImpl> toAdminDTOList(List<Admin> doctorList) {
        return doctorList.stream()
                .map(admin -> new AdminDTOImpl(admin.getId(), admin.getName(), admin.getSurname(), admin.getPosition(), admin.getUserName()))
                .collect(Collectors.toList());
    }

    public Admin toAdmin(AdminDTOImpl adminDTO) {
        Admin admin = new Admin(adminDTO.getName(), adminDTO.getSurname(),adminDTO.getPosition(),adminDTO.getUsername());
        if (adminDTO.getId() > 0) {
            admin.setId(adminDTO.getId());
        }
        return admin;
    }
}
