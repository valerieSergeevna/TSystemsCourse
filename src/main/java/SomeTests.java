import com.spring.webapp.entity.Doctor;
import com.spring.webapp.entity.Patient;
import com.spring.webapp.entity.ProcedureMedicine;
import com.spring.webapp.entity.Treatment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class SomeTests {
    public static void main(String[] args) {
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(ProcedureMedicine.class)
                .addAnnotatedClass(Doctor.class)
                .addAnnotatedClass(Patient.class)
                .addAnnotatedClass(Treatment.class)
                .buildSessionFactory();
        Session session = null;
        try{
         /*   session = factory.getCurrentSession();
            Doctor doctor = new Doctor("Михаил","Жуковский","Терапевт");
            Patient patient = new Patient("Валерий","М", 12334, "ОРВИ","лечится");
            ProcedureMedicine procedureMedicine = new ProcedureMedicine( "advil", "лекарство");
            Treatment treatment = new Treatment("лекарство", 2, "1 месяц", 125.0);
            doctor.addPatient(patient);
            patient.addTreatment(treatment);
            procedureMedicine.addTreatment(treatment);
            session.beginTransaction();

            session.save(doctor);
            session.save(patient);
            session.save(procedureMedicine);
            session.save(treatment);

            session.getTransaction().commit();
            System.out.println("DONE!");*/
        }finally {
            factory.close();
        }
    }
}
