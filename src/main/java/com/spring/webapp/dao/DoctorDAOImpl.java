package com.spring.webapp.dao;

import com.spring.webapp.entity.Doctor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class DoctorDAOImpl implements DoctorDAO{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public List<Doctor> getAllDoctors() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Doctor", Doctor.class).getResultList();
    }
}
