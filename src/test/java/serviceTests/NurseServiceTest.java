package serviceTests;

import com.spring.exception.DataBaseException;
import com.spring.exception.ServerException;
import com.spring.webapp.dao.DoctorDAOImpl;
import com.spring.webapp.dao.NurseDAOImpl;
import com.spring.webapp.dao.PatientDAOImpl;
import com.spring.webapp.dto.DoctorDTOImpl;
import com.spring.webapp.dto.NurseDTOImpl;
import com.spring.webapp.entity.Doctor;
import com.spring.webapp.entity.Nurse;
import com.spring.webapp.service.NurseUserServiceImpl;
import com.spring.webapp.service.PatientServiceImpl;
import org.hibernate.HibernateException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class NurseServiceTest {
    @InjectMocks
    private NurseUserServiceImpl nurseUserService;


    @Mock
    private NurseDAOImpl nurseDAO;

    private Nurse nurse;

    @Before
    public void init() {
        nurse = new Nurse();
        nurse.setUserName("nurse");
    }


    @Test
    public void saveUserTest() throws DataBaseException {

        Mockito.when(nurseDAO.save(Mockito.any(Nurse.class))).thenReturn(nurse);
        NurseDTOImpl nurseDTO = nurseUserService.toNurseDTO(nurse);
        NurseDTOImpl gottenNurse = nurseUserService.save(nurseDTO);
        Assert.assertNotNull(gottenNurse);
    }

    @Test
    public void getByUserNameTest() throws DataBaseException, ServerException {
        Mockito.when(nurseDAO.getByUserName("nurse")).thenReturn(nurse);
        NurseDTOImpl gottenNurse = nurseUserService.getByUserName("nurse");
        Assert.assertEquals(nurse.getUserName(), gottenNurse.getUsername());
    }

    @Test(expected = DataBaseException.class)
    public void getByUserNameWithFailTest() throws DataBaseException, ServerException {
        Mockito.when(nurseDAO.getByUserName("a")).thenThrow(new HibernateException(""));
        nurseUserService.getByUserName("a");
    }
}
