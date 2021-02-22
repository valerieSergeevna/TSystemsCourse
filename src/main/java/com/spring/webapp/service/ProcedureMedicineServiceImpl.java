package com.spring.webapp.service;

import com.spring.exception.DataBaseException;
import com.spring.webapp.dao.ProcedureMedicineDAOImpl;
import com.spring.webapp.dao.TreatmentDAOImpl;
import com.spring.webapp.dto.ProcedureMedicineDTOImpl;
import com.spring.webapp.dto.TreatmentEventDTOImpl;
import com.spring.webapp.entity.ProcedureMedicine;

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

import org.hibernate.query.Query;

@Service
public class ProcedureMedicineServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(ProcedureMedicineServiceImpl.class);

    private ProcedureMedicineDAOImpl procedureMedicineDAO;

    @Autowired
    public void setProcedureMedicineDAO(ProcedureMedicineDAOImpl procedureMedicineDAO) {
        this.procedureMedicineDAO = procedureMedicineDAO;
    }

    @Transactional
    public List<ProcedureMedicineDTOImpl> getAll() throws DataBaseException {
        try {
            List<ProcedureMedicine> procedureMedicineList = procedureMedicineDAO.getAll();
            return procedureMedicineList.stream()
                    .map(procedureMedicine -> new ProcedureMedicineDTOImpl(procedureMedicine.getId(), procedureMedicine.getName(), procedureMedicine.getType()))
                    .collect(Collectors.toList());
        } catch (
                HibernateException ex) {
            logger.error("[!ProcedureMedicineServiceImpl 'save' method:" + ex.getMessage() + "!]");
            logger.error("STACK TRACE: " + Arrays.toString(ex.getStackTrace()));
            throw new DataBaseException(ex.getMessage());
        }
    }

    @Transactional
    public ProcedureMedicineDTOImpl save(ProcedureMedicineDTOImpl procedureMedicineDTO) throws DataBaseException {
        ProcedureMedicine treatment = new ProcedureMedicine();
        try {
            BeanUtils.copyProperties(procedureMedicineDTO, treatment);
            return toProcedureMedicineDTO(procedureMedicineDAO.save(treatment));
        } catch (
                HibernateException ex) {
            logger.error("[!ProcedureMedicineServiceImpl 'save' method:" + ex.getMessage() + "!]");
            logger.error("STACK TRACE: " + Arrays.toString(ex.getStackTrace()));
            throw new DataBaseException(ex.getMessage());
        }
    }

    @Transactional
    public void delete(int id) throws DataBaseException {
        try {
            procedureMedicineDAO.delete(id);
        } catch (
                HibernateException ex) {
            logger.error("[!ProcedureMedicineServiceImpl 'delete' method:" + ex.getMessage() + "!]");
            logger.error("STACK TRACE: " + Arrays.toString(ex.getStackTrace()));
            throw new DataBaseException(ex.getMessage());
        }
    }

    @Transactional
    public ProcedureMedicineDTOImpl get(int id) throws DataBaseException {
        ProcedureMedicineDTOImpl procedureMedicineDTO = new ProcedureMedicineDTOImpl();
        try {
            BeanUtils.copyProperties(procedureMedicineDAO.get(id), procedureMedicineDTO);
            return procedureMedicineDTO;
        } catch (
                HibernateException ex) {
            logger.error("[!ProcedureMedicineServiceImpl 'get' method:" + ex.getMessage() + "!]");
            logger.error("STACK TRACE: " + Arrays.toString(ex.getStackTrace()));
            throw new DataBaseException(ex.getMessage());
        }
    }

    //
    public ProcedureMedicineDTOImpl toProcedureMedicineDTO(ProcedureMedicine procedureMedicine) {
        return new ProcedureMedicineDTOImpl(procedureMedicine.getId(),
                procedureMedicine.getName(),procedureMedicine.getType());
    }
}
