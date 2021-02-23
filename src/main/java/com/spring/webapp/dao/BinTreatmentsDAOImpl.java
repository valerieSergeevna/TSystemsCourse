package com.spring.webapp.dao;

import com.spring.utils.TimeParser;
import com.spring.webapp.dto.BinTreatmentDTOImpl;
import com.spring.webapp.dto.TreatmentDTOImpl;
import com.spring.webapp.entity.BinTreatment;
import com.spring.webapp.entity.Patient;
import com.spring.webapp.entity.Treatment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class BinTreatmentsDAOImpl{
    @Autowired
    private SessionFactory sessionFactory;

    public List<BinTreatment> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from BinTreatment ", BinTreatment.class).getResultList();
    }

    public BinTreatment save(BinTreatment treatment) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(treatment);
        session.clear();//
        return treatment;
    }

    public BinTreatment update(BinTreatment treatment) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(treatment);
        return treatment;
    }

    public BinTreatment get(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(BinTreatment.class, id);
    }

    public int getIdByProcedureMedicineId(int id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select id from BinTreatment  where procedureMedicine.id=:prMedNameID");
        query.setParameter("prMedNameID", id);
        return !query.list().isEmpty()?(int)query.list().get(0):-1;
    }

    public void deleteWithPatientId(int id) {
        Session session = sessionFactory.getCurrentSession();
        Query<BinTreatment> query = session.createQuery("delete from BinTreatment " + "where patient.id =:patientID");
        query.setParameter("patientID", id);
        query.executeUpdate();
    }

    public List<BinTreatment> getByPatientId(int id) {
        Session session = sessionFactory.getCurrentSession();
        Query<BinTreatment> query = session.createQuery("from BinTreatment " + "where patient.id =:patientID");
        query.setParameter("patientID", id);
        return !query.list().isEmpty()?query.list():new ArrayList<>();
    }

    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        Query<BinTreatment> query = session.createQuery("delete from BinTreatment " + "where id =:ID");
        query.setParameter("ID", id);
        query.executeUpdate();
    }

    public Patient getPatient(int id){
        Session session = sessionFactory.getCurrentSession();
        return get(id).getPatient();
    }

    public List<BinTreatmentDTOImpl> toTreatmentDTOList(List<BinTreatment> treatments){
        return treatments.stream()
                .map(treatment -> {
                    BinTreatmentDTOImpl treatmentDTO = new BinTreatmentDTOImpl(treatment.getTreatmentId(),treatment.getType(),treatment.getTimePattern(),treatment.getDose());
                    treatmentDTO.setTypeName(treatment.getProcedureMedicine().getName());
                    treatmentDTO.setStartDate(TimeParser.fromLocalDateTimeToLocalDate(treatment.getStartDate()));
                    treatmentDTO.setEndDate(TimeParser.fromLocalDateTimeToLocalDate(treatment.getEndDate()));
                    return treatmentDTO;
                })
                .collect(Collectors.toList());
    }
}
