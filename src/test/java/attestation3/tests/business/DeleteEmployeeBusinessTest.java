package attestation3.tests.business;

import attestation3.enitities.EmployeeRequest;
import attestation3.enitities.EmployeeResponse;
import attestation3.helpers.EmployeeHelperDB;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DeleteEmployeeBusinessTest {

    private static EmployeeHelperDB employeeHelperDB;
    private int employeeId;

    @BeforeAll
    public static void setUri() throws SQLException, IOException {
        employeeHelperDB = new EmployeeHelperDB();
    }
    @AfterEach
    public void tearDown() throws IOException, SQLException {
        employeeHelperDB.deleteEmployee(employeeId);
    }

    @Test
    @DisplayName("Удаление сотрудника")
    public void deleteEmployee() throws Exception {
        employeeId = employeeHelperDB.createEmployee(new EmployeeRequest("Barcelona", "Testa", "seller", "Testova"));
        EmployeeResponse employee = employeeHelperDB.getEmployee(employeeId);
        employeeHelperDB.deleteEmployee(employee.getId());
        assertEquals(employeeId, employee.getId());
    }

    @Test
    @DisplayName("Удаление сотрудника с несуществующим id")
    public void deleteEmployeeNotExistId() throws Exception {
        int employeeIdNull = employeeHelperDB.deleteEmployee(333);
        employeeHelperDB.deleteEmployee(employeeIdNull);
    }
}
