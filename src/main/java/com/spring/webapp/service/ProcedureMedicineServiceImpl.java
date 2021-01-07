package com.spring.webapp.service;

import com.spring.webapp.dao.EntityDAO;
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

@Service
public class ProcedureMedicineServiceImpl implements EntityService<ProcedureMedicineDTOImpl> {

    @Autowired
    @Qualifier("procedureMedicineDAOImpl")
    private EntityDAO procedureMedicineDAO;

    @Override
    @Transactional
    public List<ProcedureMedicineDTOImpl> getAll() {

        List<ProcedureMedicine> procedureMedicineList = procedureMedicineDAO.getAll();
        List<ProcedureMedicineDTOImpl> procedureMedicineDTOList = procedureMedicineList.stream()
                .map(procedureMedicine -> new ProcedureMedicineDTOImpl(procedureMedicine.getId(),procedureMedicine.getName(),procedureMedicine.getType()))
                .collect(Collectors.toList());
        return procedureMedicineDTOList;
    }

    @Override
    @Transactional
    public void save(ProcedureMedicineDTOImpl procedureMedicineDTO) {
        ProcedureMedicine treatment = new ProcedureMedicine();
        BeanUtils.copyProperties(procedureMedicineDTO, treatment);
        procedureMedicineDAO.save(treatment);
    }

    @Override
    @Transactional
    public void delete(int id) {
        procedureMedicineDAO.delete(id);
    }

    @Override
    @Transactional
    public ProcedureMedicineDTOImpl get(int id) {
        EntityDTO procedureMedicineDTO = new TreatmentDTOImpl();
        BeanUtils.copyProperties(procedureMedicineDAO.get(id),procedureMedicineDTO);
        return (ProcedureMedicineDTOImpl) procedureMedicineDTO;
    }
}
