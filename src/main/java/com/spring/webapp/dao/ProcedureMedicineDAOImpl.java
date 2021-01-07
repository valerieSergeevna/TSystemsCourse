package com.spring.webapp.dao;

import com.spring.webapp.entity.Doctor;
import com.spring.webapp.entity.ProcedureMedicine;
import com.spring.webapp.entity.Treatment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProcedureMedicineDAOImpl implements EntityDAO<ProcedureMedicine> {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<ProcedureMedicine> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from ProcedureMedicine ", ProcedureMedicine.class).getResultList();
    }

    @Override
    public void save(ProcedureMedicine procedureMedicine) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select name from ProcedureMedicine  where name =:prMedName");
        query.setParameter("prMedName", procedureMedicine.getName());
        if(query.list().isEmpty()){
            session.save(procedureMedicine);
        }
    }

    @Override
    public ProcedureMedicine get(int id) {
        Session session = sessionFactory.getCurrentSession();

        ProcedureMedicine procedureMedicine = session.get(ProcedureMedicine.class, id);
        return procedureMedicine;
    }

    @Override
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        Query<Doctor> query = session.createQuery("delete from ProcedureMedicine " + "where id =:prMedID");
        query.setParameter("prMedID", id);
        query.executeUpdate();
    }
}
