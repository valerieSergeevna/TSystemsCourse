package com.spring.webapp.dao;

import com.spring.exception.ServerException;
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

    public Doctor save(Doctor doctor) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(doctor);
        return doctor;
    }

    public Doctor get(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Doctor.class, id);
    }

    public Doctor getByUserName(String name) throws ServerException {
        Session session = sessionFactory.getCurrentSession();
        Query<Doctor> query= session.createQuery("from Doctor " + "where userName =:name");
        query.setParameter("name", name);
        if (query.list().size() == 0){
            throw new NullPointerException();
        }
        return query.list().get(0);
    }

    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        Query<Doctor> query = session.createQuery("delete from Doctor " + "where id =:doctorID");
        query.setParameter("doctorID", id);
        query.executeUpdate();
    }
}
