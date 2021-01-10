package com.spring.webapp.service;

import com.spring.webapp.dao.EntityDAO;
import com.spring.webapp.dao.ProcedureMedicineDAOImpl;
import com.spring.webapp.dto.EntityDTO;
import com.spring.webapp.dto.PatientDTOImpl;
import com.spring.webapp.dto.ProcedureMedicineDTOImpl;
import com.spring.webapp.dto.TreatmentDTOImpl;
import com.spring.webapp.entity.ProcedureMedicine;
import com.spring.webapp.entity.Treatment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import org.hibernate.query.Query;

@Service
public class ProcedureMedicineServiceImpl  {

    @Autowired
    private ProcedureMedicineDAOImpl procedureMedicineDAO;

    @Transactional
    public List<ProcedureMedicineDTOImpl> getAll() {

        List<ProcedureMedicine> procedureMedicineList = procedureMedicineDAO.getAll();
        return procedureMedicineList.stream()
                .map(procedureMedicine -> new ProcedureMedicineDTOImpl(procedureMedicine.getId(),procedureMedicine.getName(),procedureMedicine.getType()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void save(ProcedureMedicineDTOImpl procedureMedicineDTO) {
        ProcedureMedicine treatment = new ProcedureMedicine();
        BeanUtils.copyProperties(procedureMedicineDTO, treatment);
        procedureMedicineDAO.save(treatment);
    }

    @Transactional
    public void delete(int id) {
        procedureMedicineDAO.delete(id);
    }

    @Transactional
    public ProcedureMedicineDTOImpl get(int id) {
        ProcedureMedicineDTOImpl procedureMedicineDTO = new ProcedureMedicineDTOImpl();
        BeanUtils.copyProperties(procedureMedicineDAO.get(id),procedureMedicineDTO);
        return  procedureMedicineDTO;
    }

    @Transactional
    public void clearNullProcedureMedicine(){
        Query listOfEmptyProcedureMedicine = procedureMedicineDAO.clear();
        if(!listOfEmptyProcedureMedicine.list().isEmpty()) {
            for (Object item:listOfEmptyProcedureMedicine.list()) {
                procedureMedicineDAO.delete((int)item);
            }
        }
    }
}
