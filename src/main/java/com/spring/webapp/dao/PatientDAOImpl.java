package com.spring.webapp.dao;

import com.spring.webapp.entity.Doctor;
import com.spring.webapp.entity.Patient;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PatientDAOImpl implements EntityDAO<Patient> {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Patient> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Patient", Patient.class).getResultList();
    }

    @Override
    public void save(Patient patient) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(patient);
    }

    @Override
    public Patient get(int id) {
        Session session = sessionFactory.getCurrentSession();

        Patient patient = session.get(Patient.class, id);
        return patient;
    }

    @Override
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        Query<Doctor> query = session.createQuery("delete from Patient " + "where id =:patientID");
        query.setParameter("patientID", id);
        query.executeUpdate();
    }
}
