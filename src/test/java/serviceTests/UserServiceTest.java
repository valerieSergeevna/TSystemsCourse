package serviceTests;

import com.spring.exception.DataBaseException;
import com.spring.exception.ServerException;
import com.spring.jms.JmsProducer;
import com.spring.webapp.PatientStatus;
import com.spring.webapp.dao.*;
import com.spring.webapp.dao.securityDAO.RoleDAO;
import com.spring.webapp.dao.securityDAO.UserDAO;
import com.spring.webapp.dto.AllDTOUser;
import com.spring.webapp.dto.DoctorDTOImpl;
import com.spring.webapp.dto.PatientDTOImpl;
import com.spring.webapp.entity.Doctor;
import com.spring.webapp.entity.Patient;
import com.spring.webapp.entity.securityEntity.Role;
import com.spring.webapp.entity.securityEntity.User;
import com.spring.webapp.service.*;
import com.spring.webapp.service.securityService.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.*;

@RunWith(MockitoJUnitRunner.Silent.class)
//@SpringBootTest(classes = {PatientServiceImpl.class})
public class UserServiceTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule().strictness(Strictness.LENIENT);

    @InjectMocks
    private UserService userService;

    @Mock
    private UserDAO userDAO;


    @Mock
    HttpServletRequest request;

    @Mock
    RoleDAO roleRepository;
    @Mock
    DoctorDAOImpl doctorDAO;
    @Mock
    DoctorUserServiceImpl doctorService;

    @Mock
    private MailService mailSender;

    @Mock
    NurseDAOImpl nurseDAO;
    @Mock
    NurseUserServiceImpl nurseService;

    @Mock
    PatientServiceImpl patientService;

    @Mock
    AdminServiceImpl adminService;

    @Mock
    PasswordEncoder bCryptPasswordEncoder;


    private List<User> usersList = new ArrayList<>();
    private List<Patient> patientList;

    private Doctor doctor;

    private User user;

    @Before
    public void init() {
        user = new User();
        user.setUsername("doc");
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(1L, "ROLE_DOCTOR"));
        user.setRoles(roles);
        usersList.add(user);

        patientList = new ArrayList<>();
        Patient patient1 = new Patient("TestName", "TestSurname", 12, 123456, "Cold",PatientStatus.IN_PROCESS);
        patient1.setId(1);
        patientList.add(patient1);

        Patient patient2 = new Patient("TestName2", "TestSurname2", 22, 123456, "Cold",PatientStatus.IN_PROCESS);
        patient1.setId(2);
        patient2.setDoctor(doctor);
        patientList.add(patient2);

        doctor = new Doctor();
        doctor.setUserName("doc");

    }


    @Test
    public void saveUserTestWithFail() throws DataBaseException, ServerException {
        Mockito.when(userDAO.findByUsername("doc")).thenReturn(user);
        boolean isUserCreated = userService.saveUser(user, request);
        Assert.assertFalse(isUserCreated);
    }

    @Test
    public void updateUserTest() throws DataBaseException, ServerException {
        User updateUser  = new User();
        user.setUsername("doc");
        user.setGoogleName("some@some.com");
        updateUser.setUsername("doc");
        updateUser.setGoogleName("some@some1.com");
        Mockito.when(request.getParameter("email")).thenReturn("some@some.com");
        Mockito.when(request.getParameter("name")).thenReturn("some@some.com");
        Mockito.when(request.getParameter("surname")).thenReturn("some@some.com");
        Mockito.when(request.getParameter("username")).thenReturn("doc");
        Mockito.when(request.getParameter("role")).thenReturn("doctor");

        DoctorDTOImpl doctorDTO = new DoctorDTOImpl();
        doctorDTO.setUsername("doc");


        Mockito.when(doctorService.getByUserName("doc")).thenReturn(doctorDTO);
        Mockito.when(doctorService.update(Mockito.any(DoctorDTOImpl.class))).thenReturn(doctorDTO);

        Mockito.when(userDAO.findByUsername("doc")).thenReturn(user);
        Mockito.when(userDAO.save(user)).thenReturn(user);

        boolean isUserCreated = userService.updateUser(updateUser, request);

        Assert.assertTrue(isUserCreated);
    }

    @Test
    public void saveUserTestWithMessage() throws DataBaseException, ServerException {
        User updateUser  = new User();
        user.setUsername("doc");
        user.setGoogleUsername("some@some.com");
        user.setPassword("qwerty");
        Mockito.when(request.getParameter("email")).thenReturn("some@some.com");
        Mockito.when(request.getParameter("name")).thenReturn("some@some.com");
        Mockito.when(request.getParameter("surname")).thenReturn("some@some.com");
        Mockito.when(request.getParameter("role")).thenReturn("doctor");

        Mockito.when(userDAO.findByUsername("doc")).thenReturn(null);
        Mockito.when(doctorDAO.save(Mockito.any(Doctor.class))).thenReturn(doctor);
      //  Mockito.when(doctorDAO.getByUserName("doc")).thenThrow(new NullPointerException());
        Mockito.doThrow(new NullPointerException()).when(doctorService).getByUserName("doc");
        Mockito.when(userDAO.findByUsername(user.getUsername())).thenReturn(null);
        Mockito.when(userDAO.save(user)).thenReturn(null);
        Mockito.when(bCryptPasswordEncoder.encode(user.getPassword())).thenReturn(null);

        boolean isUserCreated = userService.saveUser(user, request);

        Assert.assertTrue(isUserCreated);
        Mockito.verify(mailSender)
               .send(user.getGoogleUsername(), "RehaApp password and username", "Hi!" +
                       "\nCatch your password: " + "qwerty"+ " \nAnd username: " + user.getUsername() +
                       "\n Now you can log in in this app:  http://localhost:8080/" + "\n Have a good day:)" );

    }

    @Test
    public void saveNotUniqUserTest() throws DataBaseException, ServerException {
        User oldUser  = new User();
        oldUser.setUsername("doc");
        user.setUsername("doc");
        user.setGoogleUsername("some@some.com");
        user.setPassword("qwerty");
        Mockito.when(request.getParameter("email")).thenReturn("some@some.com");
        Mockito.when(request.getParameter("name")).thenReturn("some@some.com");
        Mockito.when(request.getParameter("surname")).thenReturn("some@some.com");
        Mockito.when(request.getParameter("role")).thenReturn("doctor");

        Mockito.when(userDAO.findByUsername("doc")).thenReturn(oldUser);
        boolean isUserCreated = userService.saveUser(user, request);
        Assert.assertFalse(isUserCreated);
    }

}
