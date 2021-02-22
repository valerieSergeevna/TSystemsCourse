package integrationTests;

import com.spring.configuration.MyConfig;
import com.spring.configuration.MyWebInitializer;
import com.spring.webapp.controller.GeneralController;
import com.spring.webapp.service.AdminServiceImpl;
import com.spring.webapp.service.DoctorUserServiceImpl;
import com.spring.webapp.service.NurseUserServiceImpl;
import com.spring.webapp.service.securityService.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;
//@RunWith(SpringRunner.class)
//@ContextConfiguration(classes = MyConfig.class)
//@AutoConfigureMockMvc
//public class IntegrationTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private DoctorUserServiceImpl doctorService;
//
//    @MockBean
//    private UserService userService;
//
//    @MockBean
//    private NurseUserServiceImpl nurseService;
//
//
//    @MockBean
//    private AdminServiceImpl adminService;
//
//    @Test
//    public void contextLoads() throws Exception {
//        this.mockMvc.perform(get("/"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().string(containsString("Hello,")));
//    }
//
//}

//@Test
//    public void shouldGetAllUsersTest() {
//        List<Patient> patientList =  patientDAO.getAll();
//        int[] ids = new int[]{1,6,5,7};
//
//        int i = 0;
//
//        for (Patient patient:patientList) {
//            Assert.assertEquals(patient.getId(), ids[i]);
//        }
//
//    }