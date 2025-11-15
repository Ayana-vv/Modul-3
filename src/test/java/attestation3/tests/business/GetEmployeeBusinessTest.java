package attestation3.tests.business;

import attestation3.helpers.AuthHelper;
import org.junit.jupiter.api.*;
import attestation3.enitities.EmployeeRequest;
import attestation3.enitities.EmployeeResponse;
import attestation3.helpers.EmployeeHelperDB;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class GetEmployeeBusinessTest {

    private static EmployeeHelperDB employeeHelperDB;
    private int employeeId;

    @BeforeAll
    public static void setUri() throws SQLException, IOException {
        employeeHelperDB = new EmployeeHelperDB();
    }

    @BeforeEach
    public void setUp() throws SQLException {
        employeeId = employeeHelperDB.createEmployee(new EmployeeRequest("Barcelona", "Testa2", "seller", "Testova"));
    }

    @AfterEach
    public void tearDown() throws IOException, SQLException {
        employeeHelperDB.deleteEmployee(employeeId);
    }

    @Test
    @DisplayName("Получение сотрудника по имени")
    public void getEmployeeToName() throws Exception {
        EmployeeResponse employee = employeeHelperDB.getEmployee(employeeId);
        EmployeeResponse employeeName = employeeHelperDB.getEmployeeToName(employee.getName());
        assertEquals(employee.getName(), employeeName.getName());
    }

    @Test
    @DisplayName("Получение сотрудника по несуществующему имени")
    public void getEmployeeToNotExistName() throws Exception {
        EmployeeResponse employee = employeeHelperDB.getEmployee(employeeId);
        EmployeeResponse employeeName = employeeHelperDB.getEmployeeToNotExistName(employee.toString());
        assertNull(employeeName.getName());
    }

    @Test
    @DisplayName("Получение сотрудника по id")
    public void getEmployeeToId() throws Exception {
        EmployeeResponse employee = employeeHelperDB.getEmployee(employeeId);
        assertEquals(employeeId, employee.getId());
    }
}
