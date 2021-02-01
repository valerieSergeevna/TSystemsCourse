package com.spring.webapp.dao;

import com.spring.exception.ServerException;
import com.spring.webapp.entity.Admin;
import com.spring.webapp.entity.Doctor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdminDAOImpl {

    @Autowired
    private SessionFactory sessionFactory;

    public List<Admin> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Admin", Admin.class).getResultList();
    }

    public Admin save(Admin admin) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(admin);
        return admin;
    }

    public Admin get(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Admin.class, id);
    }

    public Admin getByUserName(String name) throws ServerException {
        Session session = sessionFactory.getCurrentSession();
        Query<Admin> query= session.createQuery("from Admin " + "where userName =:name");
        query.setParameter("name", name);
        if (query.list().size() == 0){
            throw new NullPointerException();
        }
        return query.list().get(0);
    }

    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        Query<Doctor> query = session.createQuery("delete from Admin " + "where id =:adminID");
        query.setParameter("adminID", id);
        query.executeUpdate();
    }
}
