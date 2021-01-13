package com.spring.webapp.dao;

import com.spring.webapp.entity.Doctor;
import com.spring.webapp.entity.TreatmentEvent;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TreatmentEventDAOImpl {

    @Autowired
    private SessionFactory sessionFactory;

    public List<TreatmentEvent> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from TreatmentEvent ", TreatmentEvent.class).getResultList();
    }

    public void save(TreatmentEvent treatmentEvent) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(treatmentEvent);
    }

    public TreatmentEvent get(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(TreatmentEvent.class, id);
    }

    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        Query<TreatmentEvent> query = session.createQuery("delete from TreatmentEvent " + "where id =:treatmentEventID");
        query.setParameter("treatmentEventID", id);
        query.executeUpdate();
    }
}
