package serviceTests;

import com.spring.exception.DataBaseException;
import com.spring.exception.ServerException;
import com.spring.webapp.dao.AdminDAOImpl;
import com.spring.webapp.dao.ProcedureMedicineDAOImpl;
import com.spring.webapp.dto.AdminDTOImpl;
import com.spring.webapp.dto.PatientDTOImpl;
import com.spring.webapp.dto.ProcedureMedicineDTOImpl;
import com.spring.webapp.entity.Admin;
import com.spring.webapp.entity.ProcedureMedicine;
import com.spring.webapp.service.AdminServiceImpl;
import com.spring.webapp.service.ProcedureMedicineServiceImpl;
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
import org.springframework.security.core.parameters.P;

@RunWith(MockitoJUnitRunner.class)
public class ProcedureMedicineServiceTest {
    @InjectMocks
    private ProcedureMedicineServiceImpl procedureMedicineService;

    @Mock
    private ProcedureMedicineDAOImpl procedureMedicineDAO;

    ProcedureMedicine procedureMedicine;

    @Before
    public void init() {
      procedureMedicine = new ProcedureMedicine();
    }


    @Test
    public void saveProcedureMedicineTest() throws DataBaseException {

        Mockito.when(procedureMedicineDAO.save(Mockito.any(ProcedureMedicine.class))).thenReturn(procedureMedicine);
        ProcedureMedicineDTOImpl procedureMedicineDTO = procedureMedicineService.toProcedureMedicineDTO(procedureMedicine);
        ProcedureMedicineDTOImpl procedureMedicineDTO1 = procedureMedicineService.save(procedureMedicineDTO);
        Assert.assertNotNull(procedureMedicineDTO1);
    }

    @Test
    public void getByIdeTest() throws DataBaseException, ServerException {
        Mockito.when(procedureMedicineDAO.get(0)).thenReturn(procedureMedicine);
        ProcedureMedicineDTOImpl procedureMedicineDTO = procedureMedicineService.get(0);
        Assert.assertEquals(procedureMedicine.getId(), procedureMedicineDTO.getId());
    }

    @Test(expected = DataBaseException.class)
    public void getByIdWithFailTest() throws DataBaseException, ServerException {
        Mockito.when(procedureMedicineDAO.get(0)).thenThrow(new HibernateException(""));
        procedureMedicineService.get(0);
    }
}
