package com.spring.webapp.dao;

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

    public Patient save(Patient patient) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(patient);
        session.clear();//
        return patient;
    }

    public Patient update(Patient patient) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(patient);
        return patient;
    }


    public Patient get(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Patient.class, id);
    }

    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        Query<Patient> query = session.createQuery("delete from Patient " + "where id = :patientId");
        query.setParameter("patientId", id);
        query.executeUpdate();
    }

    public List<Patient> getBySurname(String surname) {
        Session session = sessionFactory.getCurrentSession();
        Query<Patient> query = session.createQuery("from Patient " + "where surname like:patientSurname");
        query.setParameter("patientSurname", surname);
        return query.list();
    }

    public List<Patient> getAllByDoctorId(int id) {
        Session session = sessionFactory.getCurrentSession();
        Query<Patient> query = session.createQuery("from Patient " + "where doctor.id =:id");
        query.setParameter("id", id);
        return query.list();
    }


//TODO: treatments count check -> delete

    public List<Treatment> getTreatments(int id) {
        Session session = sessionFactory.getCurrentSession();
        Query<Treatment> query = session.createQuery("from Treatment " + "where patient.id =:patientID");
        query.setParameter("patientID", id);
        return query.list();
    }


}
