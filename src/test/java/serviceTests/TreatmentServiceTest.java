package serviceTests;

import com.spring.exception.DataBaseException;
import com.spring.webapp.PatientStatus;
import com.spring.webapp.TreatmentType;
import com.spring.webapp.dao.*;
import com.spring.webapp.dao.securityDAO.RoleDAO;
import com.spring.webapp.dao.securityDAO.UserDAO;
import com.spring.webapp.dto.TreatmentDTOImpl;
import com.spring.webapp.entity.*;
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
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class TreatmentServiceTest {

    @InjectMocks
    private TreatmentServiceImpl treatmentService;

    @Mock
    private TreatmentDAOImpl treatmentDAO;
    @Mock
    private ProcedureMedicineDAOImpl procedureMedicineDAO;

    private List<Treatment> treatmentList;
    @Before
    public void init() {
        treatmentList = new ArrayList<>();
        Treatment treatment1 = new Treatment(TreatmentType.medicine,
                2, 1, LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        treatment1.setProcedureMedicine(new ProcedureMedicine("Advil", TreatmentType.medicine));
        treatmentList.add(treatment1);

        treatmentList = new ArrayList<>();
        Treatment treatment2 = new Treatment(TreatmentType.medicine,
                3, 1, LocalDateTime.now(), LocalDateTime.now().plusDays(2));
        treatment2.setProcedureMedicine(new ProcedureMedicine("Advil", TreatmentType.medicine));

        treatmentList.add(treatment2);
    }


    @Test
    public void saveTreatment() throws DataBaseException {
        Treatment treatment3 = new Treatment(TreatmentType.medicine,
                3, 1, LocalDateTime.now(), LocalDateTime.now().plusDays(2));
        treatment3.setProcedureMedicine(new ProcedureMedicine("Advil", TreatmentType.medicine));


        TreatmentDTOImpl treatmentDTO = treatmentService.toTreatmentDTO(treatment3);
        treatmentDTO.setTypeName("Advil");

        Mockito.when(procedureMedicineDAO.getIdByName(treatmentDTO.getTypeName())).thenReturn(1);

        Mockito.when(treatmentDAO.save(Mockito.any(Treatment.class))).thenReturn(treatment3);

        treatmentService.save(treatmentDTO);
        Mockito.verify(treatmentDAO).save(Mockito.any(Treatment.class));
        Mockito.verify(procedureMedicineDAO, Mockito.times(0)).save(Mockito.any(ProcedureMedicine.class));

    }

    @Test
    public void saveWithoutExistProcedureMedicineTest() throws DataBaseException {
        Treatment treatment3 = new Treatment(TreatmentType.medicine,
                3, 1, LocalDateTime.now(), LocalDateTime.now().plusDays(2));
        ProcedureMedicine procedureMedicine = new ProcedureMedicine("Anaferon", TreatmentType.medicine);
        treatment3.setProcedureMedicine(procedureMedicine);

        TreatmentDTOImpl treatmentDTO = treatmentService.toTreatmentDTO(treatment3);
        treatmentDTO.setTypeName(procedureMedicine.getName());

        Mockito.when(procedureMedicineDAO.getIdByName(treatmentDTO.getTypeName())).thenReturn(0);

        Mockito.when(treatmentDAO.save(Mockito.any(Treatment.class))).thenReturn(treatment3);


        Mockito.when(procedureMedicineDAO.save(Mockito.any(ProcedureMedicine.class))).thenReturn(procedureMedicine);


        treatmentService.save(treatmentDTO);
        Mockito.verify(treatmentDAO).save(Mockito.any(Treatment.class));
        Mockito.verify(procedureMedicineDAO).save(Mockito.any(ProcedureMedicine.class));

    }

    @Test
    public void getPatient() throws DataBaseException {
        Patient patient = new Patient();
        patient.setId(1);
        Mockito.when(treatmentDAO.getPatient(1)).thenReturn(patient);


        Assert.assertEquals(1, treatmentDAO.getPatient(1).getId());
    }
}
