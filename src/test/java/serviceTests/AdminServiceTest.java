package serviceTests;

import com.spring.exception.DataBaseException;
import com.spring.exception.ServerException;
import com.spring.webapp.dao.AdminDAOImpl;
import com.spring.webapp.dto.AdminDTOImpl;
import com.spring.webapp.dto.DoctorDTOImpl;
import com.spring.webapp.entity.Admin;
import com.spring.webapp.entity.Doctor;
import com.spring.webapp.service.AdminServiceImpl;
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
//@SpringBootTest(classes = {PatientServiceImpl.class})
public class AdminServiceTest {
    @InjectMocks
    private AdminServiceImpl adminService;


    @Mock
    private AdminDAOImpl adminDAO;



    private Admin admin;

    @Before
    public void init() {
        admin = new Admin();
        admin.setUserName("doc");
    }


    @Test
    public void saveUserTest() throws DataBaseException {

        Mockito.when(adminDAO.save(Mockito.any(Admin.class))).thenReturn(admin);
        AdminDTOImpl adminDTO = adminService.toAdminDTO(admin);
        AdminDTOImpl gottenAdmin = adminService.save(adminDTO);
        Assert.assertNotNull(gottenAdmin);
    }

    @Test
    public void getByUserNameTest() throws DataBaseException, ServerException {
        Mockito.when(adminDAO.getByUserName("doc")).thenReturn(admin);
        AdminDTOImpl gottenAdmin = adminService.getByUserName("doc");
        Assert.assertEquals(admin.getUserName(), gottenAdmin.getUsername());
    }

    @Test(expected = DataBaseException.class)
    public void getByUserNameWithFailTest() throws DataBaseException, ServerException {
        Mockito.when(adminDAO.getByUserName("a")).thenThrow(new HibernateException(""));
        adminService.getByUserName("a");
    }
}
