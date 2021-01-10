package com.spring.webapp.dao;

import com.spring.webapp.dto.TreatmentDTOImpl;
import com.spring.webapp.entity.Doctor;
import com.spring.webapp.entity.Patient;
import com.spring.webapp.entity.Treatment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PatientDAOImpl {

    @Autowired
    private SessionFactory sessionFactory;

    public List<Patient> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Patient", Patient.class).getResultList();
    }

    public void save(Patient patient) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(patient);
    }

    public Patient get(int id) {
        Session session = sessionFactory.getCurrentSession();

        Patient patient = session.get(Patient.class, id);
        return patient;
    }

    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        Query<Doctor> query = session.createQuery("delete from Patient " + "where id =:patientID");
        query.setParameter("patientID", id);
        query.executeUpdate();
    }

    public List<Treatment> getTreatments(int id){
        Session session = sessionFactory.getCurrentSession();
        Query<Treatment> query = session.createQuery("from Treatment " + "where patient.id =:patientID");
        query.setParameter("patientID", id);
        return query.list();
    }


}