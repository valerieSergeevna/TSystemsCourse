package serviceTests;

import com.spring.SpringBootRehaApplication;
import com.spring.configuration.MyConfig;
import com.spring.exception.DataBaseException;
import com.spring.exception.ServerException;
import com.spring.jms.JmsProducer;
import com.spring.webapp.PatientStatus;
import com.spring.webapp.StatusEnum;
import com.spring.webapp.dao.*;
import com.spring.webapp.dto.PatientDTOImpl;
import com.spring.webapp.dto.TreatmentDTOImpl;
import com.spring.webapp.entity.Doctor;
import com.spring.webapp.entity.Patient;
import com.spring.webapp.service.DoctorUserServiceImpl;
import com.spring.webapp.service.PatientServiceImpl;
import com.spring.webapp.service.TreatmentEventServiceImpl;
import com.spring.webapp.service.TreatmentServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
//@SpringBootTest(classes = {PatientServiceImpl.class})
public class PatientServiceTest {
    @TestConfiguration
    static class PatientUtilityServiceTestConfiguration {

        @Bean
        public PatientServiceImpl patientService() {
            return new PatientServiceImpl();
        }
    }

    @Autowired
    private PatientServiceImpl patientService;

    @MockBean
    private PatientDAOImpl patientDAO;

    @MockBean
    private TreatmentDAOImpl treatmentDAO;

    @MockBean
    private ProcedureMedicineDAOImpl procedureMedicineDAO;

    @MockBean
    private DoctorDAOImpl doctorDAO;

    @MockBean
    private TreatmentEventDAOImpl treatmentEventDAO;

    @MockBean
    private TreatmentServiceImpl treatmentService;

    @MockBean
    private DoctorUserServiceImpl doctorService;

    @MockBean
    private TreatmentEventServiceImpl treatmentEventService;

    @MockBean
    private JmsProducer jmsProducer;

    private PatientDTOImpl patientDTO;

    private List<Patient> patientList;

    private Doctor doctor;

    @Before
    public void init() {
        doctor = new Doctor();
        doctor.setSurname("doc");

        patientList = new ArrayList<>();
        Patient patient1 = new Patient("TestName", "TestSurname", 12, 123456, "Cold",PatientStatus.IN_PROCESS);
        patient1.setId(1);
        patientList.add(patient1);

        Patient patient2 = new Patient("TestName2", "TestSurname2", 22, 123456, "Cold",PatientStatus.IN_PROCESS);
        patient1.setId(2);
        patient2.setDoctor(doctor);
        patientList.add(patient2);


        patientDTO = new PatientDTOImpl();
        patientDTO.setName("TestName");
        patientDTO.setSurname("TestSurname");
        patientDTO.setTreatments(null);
        patientDTO.setAges(20);
        patientDTO.setDisease("TestDisease");
        patientDTO.setStatus(PatientStatus.IN_PROCESS.getTitle());
        patientDTO.setInsuranceNumber(123456);
        patientDTO.setDoctor(null);
        patientDTO.setId(1);
    }


    @Test
    public void getPatientTest() throws DataBaseException {
        // PatientDTOImpl newPatientDTO = new PatientDTOImpl();
        Mockito.when(patientDAO.get(1)).thenReturn(patientList.get(0));
        PatientDTOImpl gottenPatient = patientService.get(1);
        Assert.assertEquals(gottenPatient.getId(), patientList.get(0).getId());
    }


    @Test
    public void getAllPatientsTest() throws DataBaseException {
        Mockito.when(patientDAO.getAll()).thenReturn(patientList);
        Assert.assertEquals(patientService.getAll().size(), patientService.toPatientDTOList(patientList).size());
    }

    @Test
    public void getAllPatientsByDoctorUserNameTest() throws DataBaseException, ServerException {

        Mockito.when(doctorDAO.getByUserName("doc")).thenReturn(doctor);

        List<Patient> patients = new ArrayList<>();
        patients.add(patientList.get(1));

        Mockito.when(patientDAO.getAllByDoctorId(doctor.getId())).thenReturn(patients);
        Assert.assertEquals(patientService.getAllByDoctorUserName("doc").get(0).getId(), patients.get(0).getId());
    }

//
//    @Test
//    public void dischargedPatientTest() throws DataBaseException {
//        List<TreatmentDTOImpl> treatmentDTOList = new ArrayList<>();
//        treatmentDTOList.add(new TreatmentDTOImpl());
//        patientDTO.setTreatments(treatmentDTOList);
//        patientDTO.setStatus(PatientStatus.DISCHARGED.getTitle());
//        Mockito.when(patientDAO.update(patientService.toPatient(patientDTO))).thenReturn(patientService.toPatient(patientDTO));
//        Mockito.verify(treatmentDAO).delete(0);
//        patientService.dischargePatient(patientDTO,treatmentDTOList);
//        Assert.assertEquals(treatmentDTOList.size(), 0);
//    }
}
