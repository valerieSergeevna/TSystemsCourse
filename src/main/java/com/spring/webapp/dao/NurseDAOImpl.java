package com.spring.webapp.dao;

import com.spring.exception.ServerException;
import com.spring.webapp.entity.Doctor;
import com.spring.webapp.entity.Nurse;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NurseDAOImpl {

    @Autowired
    private SessionFactory sessionFactory;

    public List<Nurse> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Nurse", Nurse.class).getResultList();
    }

    public Nurse save(Nurse nurse) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(nurse);
        return nurse;
    }

    public Nurse get(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Nurse.class, id);
    }

    public Nurse getByUserName(String name) throws ServerException {
        Session session = sessionFactory.getCurrentSession();
        Query<Nurse> query= session.createQuery("from Nurse " + "where userName =:name");
        query.setParameter("name", name);
        if (query.list().size() == 0){
            throw new NullPointerException();
        }
        return query.list().get(0);
    }

    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        Query<Doctor> query = session.createQuery("delete from Nurse " + "where id =:nurseID");
        query.setParameter("nurseID", id);
        query.executeUpdate();
    }
}
