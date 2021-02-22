package serviceTests;

import com.spring.exception.DataBaseException;
import com.spring.exception.ServerException;
import com.spring.webapp.PatientStatus;
import com.spring.webapp.dao.DoctorDAOImpl;
import com.spring.webapp.dao.NurseDAOImpl;
import com.spring.webapp.dao.PatientDAOImpl;
import com.spring.webapp.dao.securityDAO.RoleDAO;
import com.spring.webapp.dao.securityDAO.UserDAO;
import com.spring.webapp.dto.DoctorDTOImpl;
import com.spring.webapp.entity.Doctor;
import com.spring.webapp.entity.Patient;
import com.spring.webapp.entity.securityEntity.Role;
import com.spring.webapp.entity.securityEntity.User;
import com.spring.webapp.service.*;
import com.spring.webapp.service.securityService.UserService;
import org.hibernate.HibernateException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import sun.tools.tree.DoStatement;

import javax.print.Doc;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
//@SpringBootTest(classes = {PatientServiceImpl.class})
public class DoctorServiceTest {
    @InjectMocks
    private DoctorUserServiceImpl doctorUserService;


    @Mock
    private DoctorDAOImpl doctorDAO;

    @Mock
    private PatientDAOImpl patientDAO;

    @Mock
    private PatientServiceImpl patientService;

    private Doctor doctor;

    @Before
    public void init() {
        doctor = new Doctor();
        doctor.setUserName("doc");
    }


    @Test
    public void saveUserTest() throws DataBaseException {

        Mockito.when(doctorDAO.save(Mockito.any(Doctor.class))).thenReturn(doctor);
        DoctorDTOImpl doctorDTO = doctorUserService.toDoctorDTO(doctor);
        DoctorDTOImpl gottenDoctor = doctorUserService.save(doctorDTO);
        Assert.assertNotNull(gottenDoctor);
    }

    @Test
    public void getByUserNameTest() throws DataBaseException, ServerException {
        Mockito.when(doctorDAO.getByUserName("doc")).thenReturn(doctor);
        DoctorDTOImpl gottenDoctor = doctorUserService.getByUserName("doc");
        Assert.assertEquals(doctor.getUserName(), gottenDoctor.getUsername());
    }

    @Test(expected = DataBaseException.class)
    public void getByUserNameWithFailTest() throws DataBaseException, ServerException {
        Mockito.when(doctorDAO.getByUserName("a")).thenThrow(new HibernateException(""));
        doctorUserService.getByUserName("a");
    }
}
