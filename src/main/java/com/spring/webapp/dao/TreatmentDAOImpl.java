package com.spring.webapp.dao;

import com.spring.webapp.dto.PatientDTOImpl;
import com.spring.webapp.entity.Doctor;
import com.spring.webapp.entity.Patient;
import com.spring.webapp.entity.Treatment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class TreatmentDAOImpl {

    @Autowired
    private SessionFactory sessionFactory;

    public List<Treatment> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Treatment ", Treatment.class).getResultList();
    }

    public void save(Treatment treatment) {
        Session session = sessionFactory.getCurrentSession();
      //  Query query = session.createQuery("select ")
        session.saveOrUpdate(treatment);
    }

    public Treatment get(int id) {
        Session session = sessionFactory.getCurrentSession();

        Treatment treatment = session.get(Treatment.class, id);
        return treatment;
    }

    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        Query<Doctor> query = session.createQuery("delete from Treatment " + "where id =:treatmentID");
        query.setParameter("treatmentID", id);
        query.executeUpdate();
    }

    public Patient getPatient(int id){
        Session session = sessionFactory.getCurrentSession();
        return get(id).getPatient();
    }
}
