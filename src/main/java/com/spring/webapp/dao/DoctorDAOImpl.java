package com.spring.webapp.dao;

import com.spring.webapp.entity.Doctor;
import org.hibernate.QueryException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class DoctorDAOImpl {

    @Autowired
    private SessionFactory sessionFactory;

    public List<Doctor> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Doctor", Doctor.class).getResultList();
    }

    public void save(Doctor doctor) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(doctor);
    }

    public Doctor get(int id) {
        Session session = sessionFactory.getCurrentSession();

        Doctor doctor = session.get(Doctor.class, id);
        return doctor;
    }

    public Doctor getByUserName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Query<Doctor> query= session.createQuery("delete from Doctor " + "where userName =:userName");
        query.setParameter("userName", name);
        return query.list().get(0);
    }

    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        Query<Doctor> query = session.createQuery("delete from Doctor " + "where id =:doctorID");
        query.setParameter("doctorID", id);
        query.executeUpdate();
    }
}
