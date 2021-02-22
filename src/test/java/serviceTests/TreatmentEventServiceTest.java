package serviceTests;

import com.spring.exception.DataBaseException;
import com.spring.jms.JmsProducer;
import com.spring.webapp.EventStatus;
import com.spring.webapp.PatientStatus;
import com.spring.webapp.dao.DoctorDAOImpl;
import com.spring.webapp.dao.NurseDAOImpl;
import com.spring.webapp.dao.TreatmentEventDAOImpl;
import com.spring.webapp.dao.securityDAO.RoleDAO;
import com.spring.webapp.dao.securityDAO.UserDAO;
import com.spring.webapp.dto.TreatmentEventDTOImpl;
import com.spring.webapp.entity.Doctor;
import com.spring.webapp.entity.Patient;
import com.spring.webapp.entity.Treatment;
import com.spring.webapp.entity.TreatmentEvent;
import com.spring.webapp.entity.securityEntity.Role;
import com.spring.webapp.entity.securityEntity.User;
import com.spring.webapp.service.*;
import com.spring.webapp.service.securityService.UserService;
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

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
//@SpringBootTest(classes = {PatientServiceImpl.class})
public class TreatmentEventServiceTest {


    @InjectMocks
    private TreatmentEventServiceImpl treatmentEventService;

    @Mock
    private TreatmentEventDAOImpl treatmentEventDAO;

    @Mock
    private PatientServiceImpl patientService;

    @Mock
    private JmsProducer producer;

    private List<User> usersList = new ArrayList<>();
    private List<Patient> patientList;

    private Doctor doctor;

    private TreatmentEventDTOImpl treatmentEventDTO;

    @Before
    public void init() {
        treatmentEventDTO = new TreatmentEventDTOImpl();
        treatmentEventDTO.setTreatmentTime(LocalDateTime.now());
        treatmentEventDTO.setId(1);
        treatmentEventDTO.setStatus(EventStatus.IN_PLAN.getTitle());
    }


    @Test
    public void updateStatus() throws DataBaseException {
        TreatmentEventDTOImpl updatedTreatmentEventDTO = new TreatmentEventDTOImpl();
        updatedTreatmentEventDTO.setTreatmentTime(LocalDateTime.now().plusDays(1));
        updatedTreatmentEventDTO.setId(1);
        updatedTreatmentEventDTO.setStatus(EventStatus.CANCELED.getTitle());

        TreatmentEvent treatmentEvent =treatmentEventService.toTreatmentEvent(updatedTreatmentEventDTO);

        Mockito.when(treatmentEventDAO.get(1))
                .thenReturn(treatmentEventService.toTreatmentEvent(treatmentEventDTO));

        Mockito.when(treatmentEventDAO.update(Mockito.any(TreatmentEvent.class)))
                .thenReturn(treatmentEvent);

        treatmentEventService.setCancelReason(updatedTreatmentEventDTO);

        Mockito.verify(producer)
                .send("UPDATE" );
    }


}
