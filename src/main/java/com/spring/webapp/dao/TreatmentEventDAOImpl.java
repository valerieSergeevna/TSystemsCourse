package com.spring.webapp.dao;

import com.spring.webapp.dto.PatientDTOImpl;
import com.spring.webapp.dto.TreatmentDTOImpl;
import com.spring.webapp.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TreatmentEventDAOImpl {

    @Autowired
    private SessionFactory sessionFactory;

    public List<TreatmentEvent> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from TreatmentEvent ", TreatmentEvent.class).getResultList();
    }

    public List<TreatmentEvent> getAllByTreatmentID(int id) {
        Session session = sessionFactory.getCurrentSession();
        Query<TreatmentEvent> query = session.createQuery("from TreatmentEvent " + "where treatment.id =:id");
        query.setParameter("id", id);
        return query.list();
    }

    public void save(TreatmentEvent treatmentEvent) {
        Session session = sessionFactory.getCurrentSession();
        session.evict(treatmentEvent);
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

    public void deleteByTreatment(int id) {
        Session session = sessionFactory.getCurrentSession();
        Query<TreatmentEvent> query = session.createQuery("delete from TreatmentEvent " + "where treatment.id =:id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    public List<TreatmentEvent> createTimeTable(Treatment treatment) {
        List<TreatmentEvent> treatmentEventList = new ArrayList<>();

        String type = treatment.getType();
        Patient patient = treatment.getPatient();

        ProcedureMedicine procedureMedicine = treatment.getProcedureMedicine();

        int timePattern = treatment.getTimePattern();
        double dose = treatment.getDose();

        String[] period = treatment.getPeriod().split(" ");


        LocalDateTime startDate = LocalDateTime.now().plusDays(1);
        LocalDateTime endDate = null;

        String duration = "months";

        //  switch (period[1]) {
        switch (duration) {
            case "days":
                endDate = LocalDateTime.now().plusDays(Long.parseLong(period[0]));
                break;
            case "weeks":
                endDate = LocalDateTime.now().plusWeeks(Long.parseLong(period[0]));
                break;
            case "months":
                endDate = LocalDateTime.now().plusMonths(Long.parseLong(period[0]));
                break;
        }

        deleteByTreatment(treatment.getTreatmentId());

        while (startDate.isBefore(endDate)) {
            TreatmentEvent treatmentEvent = new TreatmentEvent();
            if (type.equals("treatment")) {
                // startDate.plusDays((timePattern / 7));
                for (int i = 1; i <= timePattern; i++) {
                    treatmentEvent.setDose(dose);
                    treatmentEvent.setTreatmentTime(LocalDateTime.of(startDate.getYear(), startDate.getMonth(),
                            startDate.getDayOfMonth(), 8+ 24 - (24/i), 0, 0));
                    startDate = startDate.plusDays(1);
                }
            }else{
                treatmentEvent.setDose(1);
                startDate = startDate.plusDays((7 / timePattern));
                treatmentEvent.setTreatmentTime(startDate);
            }
            treatmentEvent.setPatient(patient);
            treatmentEvent.setProcedureMedicine(procedureMedicine);
            // treatmentEvent.setStatus();
            treatmentEvent.setType(type);
            treatmentEvent.setTreatment(treatment);

            save(treatmentEvent);
            treatmentEventList.add(treatmentEvent);
        }
        return treatmentEventList;
    }

    public  List<TreatmentEvent> getByPatient(int id){
        Session session = sessionFactory.getCurrentSession();
        Query<TreatmentEvent> query = session.createQuery("from TreatmentEvent " + "where patient.id =:patientID");
        query.setParameter("patientID", id);
        return query.list();
    }

    public  List<TreatmentEvent> getNearestEvents(LocalDateTime time){
        Session session = sessionFactory.getCurrentSession();
        Query<TreatmentEvent> query = session.createQuery("from TreatmentEvent " + "where treatmentTime >=:time and treatmentTime <=:endTime");
        query.setParameter("time", time);
        query.setParameter("endTime", time.plusHours(1));
        return query.list();
    }

    public  List<TreatmentEvent> getTodayEvents(LocalDateTime time){
        Session session = sessionFactory.getCurrentSession();
        Query<TreatmentEvent> query = session.createQuery("from TreatmentEvent " + "where treatmentTime >=:time and treatmentTime <=:endTime");
        LocalDateTime endTime = LocalDateTime.of(time.getYear(), time.getMonth(),
                time.getDayOfMonth(), 23, 59, 59);
        query.setParameter("time", time);
        query.setParameter("endTime", endTime);
        return query.list();
    }

    public  List<TreatmentEvent> sortByDate(LocalDateTime time){
        Session session = sessionFactory.getCurrentSession();
        Query<TreatmentEvent> query = session.createQuery("from TreatmentEvent " + "where treatmentTime >=:time order by treatmentTime");
        query.setParameter("time", time);
        return query.list();
    }
}
