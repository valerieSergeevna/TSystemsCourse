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
        return session.createQuery("from TreatmentEvent order by treatmentTime", TreatmentEvent.class).getResultList();
    }

    public List<TreatmentEvent> getAllByTreatmentID(int id) {
        Session session = sessionFactory.getCurrentSession();
        Query<TreatmentEvent> query = session.createQuery("from TreatmentEvent " + "where treatment.id =:id");
        query.setParameter("id", id);
        return query.list();
    }

    public void save(TreatmentEvent treatmentEvent) {
        Session session = sessionFactory.getCurrentSession();
        //   session.evict(treatmentEvent);
        //treatmentEvent = session.merge(treatmentEvent);
        session.saveOrUpdate(treatmentEvent);
        session.clear();
    }

    public void update(TreatmentEvent treatmentEvent) {
        Session session = sessionFactory.getCurrentSession();

      //  session.update(treatmentEvent);
        session.merge(treatmentEvent);
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
        List<TreatmentEvent> treatmentEventList;
        treatmentEventList = getAllByTreatmentID(treatment.getTreatmentId());

        if (treatmentEventList.size() != 0) {
            if (treatmentEventList.get(0).getTreatment().getTimePattern() == treatment.getTimePattern()
                    && treatmentEventList.get(0).getTreatment().getStartDate().equals(treatment.getStartDate())
                    && treatmentEventList.get(0).getTreatment().getEndDate().equals(treatment.getEndDate())) {
                for (TreatmentEvent treatmentEvent : treatmentEventList) {
                    TreatmentEvent currentTreatmentEvent = get(treatmentEvent.getId());
                    if (currentTreatmentEvent.getTreatmentTime().isBefore(LocalDateTime.now()))
                        currentTreatmentEvent.setStatus(treatmentEvent.getStatus());
                    currentTreatmentEvent.setProcedureMedicine(treatmentEvent.getProcedureMedicine());
                    currentTreatmentEvent.setTreatment(treatmentEvent.getTreatment());
                    currentTreatmentEvent.setDose(treatmentEvent.getDose());
                    currentTreatmentEvent.setType(treatmentEvent.getType());
                    currentTreatmentEvent.setPatient(treatmentEvent.getPatient());
                    update(currentTreatmentEvent);
                }
                return getAllByTreatmentID(treatment.getTreatmentId());
            } else {
                deleteByTreatment(treatment.getTreatmentId());
            }
        }

        String type = treatment.getType();
        Patient patient = treatment.getPatient();

        ProcedureMedicine procedureMedicine = treatment.getProcedureMedicine();

        int timePattern = treatment.getTimePattern();
        double dose = treatment.getDose();

     //   String[] period = treatment.getPeriod().split(" ");


        LocalDateTime startDate = treatment.getStartDate();
        LocalDateTime endDate = treatment.getEndDate();


        deleteByTreatment(treatment.getTreatmentId());

        while (startDate.isBefore(endDate)) {
           // TreatmentEvent treatmentEvent;
            if (type.equals("medicine")) {
                for (int i = 0; i < timePattern; i++) {
                    addToListAndInitTreatmentEvent(treatmentEventList,patient,
                            dose,LocalDateTime.of(startDate.getYear(), startDate.getMonth(),
                                    startDate.getDayOfMonth(), 8 + ((12 / timePattern)*i), 0, 0),
                            type,treatment,procedureMedicine);
                   /* treatmentEvent = new TreatmentEvent();
                    treatmentEvent.setDose(dose);
                    treatmentEvent.setTreatmentTime(LocalDateTime.of(startDate.getYear(), startDate.getMonth(),
                            startDate.getDayOfMonth(), 8 + ((12 / timePattern)*i), 0, 0));
                    treatmentEvent.setPatient(patient);
                    treatmentEvent.setProcedureMedicine(procedureMedicine);
                    treatmentEvent.setStatus("in plan");
                    treatmentEvent.setType(type);
                    treatmentEvent.setTreatment(treatment);

                    save(treatmentEvent);
                    treatmentEventList.add(treatmentEvent);*/
                }
                startDate = startDate.plusDays(1);
            } else {
                addToListAndInitTreatmentEvent(treatmentEventList,patient,
                        1,LocalDateTime.of(startDate.getYear(), startDate.getMonth(),
                                startDate.getDayOfMonth(), 14, 0, 0),
                        type,treatment,procedureMedicine);
               /* treatmentEvent = new TreatmentEvent();
                treatmentEvent.setDose(1);
                startDate = startDate.plusDays(8 - (7 / timePattern));
                treatmentEvent.setTreatmentTime(LocalDateTime.of(startDate.getYear(), startDate.getMonth(),
                        startDate.getDayOfMonth(), 14, 0, 0));
                treatmentEvent.setPatient(patient);
                treatmentEvent.setProcedureMedicine(procedureMedicine);
                treatmentEvent.setStatus("in plan");
                treatmentEvent.setType(type);
                treatmentEvent.setTreatment(treatment);

                save(treatmentEvent);
                treatmentEventList.add(treatmentEvent);*/
                startDate = startDate.plusDays(8 - (7 / timePattern));
            }

        }
        return treatmentEventList;
    }

    private void addToListAndInitTreatmentEvent(List<TreatmentEvent> treatmentEventList,
                                                Patient patient, double dose,
                                                LocalDateTime time,String type, Treatment treatment,ProcedureMedicine procedureMedicine){
        TreatmentEvent treatmentEvent = new TreatmentEvent();
        treatmentEvent.setDose(dose);
        treatmentEvent.setTreatmentTime(time);
        treatmentEvent.setPatient(patient);
        treatmentEvent.setProcedureMedicine(procedureMedicine);
        treatmentEvent.setStatus("in plan");
        treatmentEvent.setType(type);
        treatmentEvent.setTreatment(treatment);
        save(treatmentEvent);
        treatmentEventList.add(treatmentEvent);
    }

    public List<TreatmentEvent> getByPatient(int id) {
        Session session = sessionFactory.getCurrentSession();
        Query<TreatmentEvent> query = session.createQuery("from TreatmentEvent " + "where patient.id =:patientID");
        query.setParameter("patientID", id);
        return query.list();
    }

    public List<TreatmentEvent> getNearestEvents(LocalDateTime time) {
        Session session = sessionFactory.getCurrentSession();
        Query<TreatmentEvent> query = session.createQuery("from TreatmentEvent " + "where treatmentTime >=:time and treatmentTime <=:endTime");
        query.setParameter("time", time);
        query.setParameter("endTime", time.plusHours(1));
        return query.list();
    }

    public List<TreatmentEvent> getTodayEvents(LocalDateTime time) {
        Session session = sessionFactory.getCurrentSession();
        Query<TreatmentEvent> query = session.createQuery("from TreatmentEvent " + "where treatmentTime >=:time and treatmentTime <=:endTime order by treatmentTime");
        LocalDateTime endTime = LocalDateTime.of(time.getYear(), time.getMonth(),
                time.getDayOfMonth(), 23, 59, 59);
        query.setParameter("time", time);
        query.setParameter("endTime", endTime);
        return query.list();
    }

    public List<TreatmentEvent> sortByDate(LocalDateTime time) {
        Session session = sessionFactory.getCurrentSession();
        Query<TreatmentEvent> query = session.createQuery("from TreatmentEvent " + "where treatmentTime >=:time order by treatmentTime");
        query.setParameter("time", time);
        return query.list();
    }
}
