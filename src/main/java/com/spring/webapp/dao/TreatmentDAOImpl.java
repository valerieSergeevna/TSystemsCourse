package com.spring.webapp.dao;

import com.spring.utils.TimeParser;
import com.spring.webapp.dto.PatientDTOImpl;
import com.spring.webapp.dto.TreatmentDTOImpl;
import com.spring.webapp.entity.Doctor;
import com.spring.webapp.entity.Patient;
import com.spring.webapp.entity.Treatment;
import com.spring.webapp.entity.TreatmentEvent;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TreatmentDAOImpl {

    @Autowired
    private SessionFactory sessionFactory;

    public List<Treatment> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Treatment", Treatment.class).getResultList();
    }

    public void save(Treatment treatment) {
        Session session = sessionFactory.getCurrentSession();
      //  Query query = session.createQuery("select ")


        session.saveOrUpdate(treatment);
       // session.evict(treatment);
        session.clear();

    }

    public void update(Treatment treatment) {
        Session session = sessionFactory.getCurrentSession();
     //  session.evict(treatment);

        session.merge(treatment);
  //      session.clear();
       // session.flush();
     //   session.merge(treatment);
    }

    public Treatment get(int id) {
        Session session = sessionFactory.getCurrentSession();

        Treatment treatment = session.get(Treatment.class, id);
        return treatment;
    }

    public int getIdByProcedureMedicineId(int id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select id from Treatment  where procedureMedicine.id=:prMedNameID");
        query.setParameter("prMedNameID", id);
        return !query.list().isEmpty()?(int)query.list().get(0):-1;
    }

    public void deleteWithPatientId(int id) {
        Session session = sessionFactory.getCurrentSession();
        Query<Treatment> query = session.createQuery("delete from Treatment " + "where patient.id =:patientID");
        query.setParameter("patientID", id);
        query.executeUpdate();
    }

    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        Query<Treatment> query = session.createQuery("delete from Treatment " + "where id =:ID");
        query.setParameter("ID", id);
        query.executeUpdate();
    }

    public Patient getPatient(int id){
        Session session = sessionFactory.getCurrentSession();
        return get(id).getPatient();
    }

    public List<TreatmentDTOImpl> toTreatmentDTOList(List<Treatment> treatments){
        return treatments.stream()
                .map(treatment -> {
                    TreatmentDTOImpl treatmentDTO = new TreatmentDTOImpl(treatment.getTreatmentId(),treatment.getType(),treatment.getTimePattern(),treatment.getDose());
                    treatmentDTO.setTypeName(treatment.getProcedureMedicine().getName());
                    treatmentDTO.setStartDate(TimeParser.fromLocalDateTimeToLocalDate(treatment.getStartDate()));
                    treatmentDTO.setEndDate(TimeParser.fromLocalDateTimeToLocalDate(treatment.getEndDate()));
                    return treatmentDTO;
                })
                .collect(Collectors.toList());
    }
}
