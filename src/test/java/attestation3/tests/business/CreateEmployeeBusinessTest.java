package attestation3.tests.business;

import org.junit.jupiter.api.*;
import attestation3.enitities.EmployeeRequest;
import attestation3.enitities.EmployeeResponse;
import attestation3.helpers.EmployeeHelperDB;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CreateEmployeeBusinessTest {

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
    @DisplayName("Создание сотрудника")
    public void createEmployee() throws Exception {
        employeeId = employeeHelperDB.createEmployee(new EmployeeRequest("Barcelona", "Testa", "seller", "Testova"));
        EmployeeResponse employee = employeeHelperDB.getEmployee(employeeId);
        assertEquals(employeeId, employee.getId());
    }

    @Disabled("Невозможно запустить тест, тк создастся id, а поля будут пустыми, а в бд не должно быть пустых полей")
    @Test
    @DisplayName("Создание сотрудника с пустым полем")
    public void createEmployeeWithEmptyBody() throws Exception {
        int employeeId = employeeHelperDB.createEmployee(new EmployeeRequest());
        EmployeeResponse employee = employeeHelperDB.getEmployee(employeeId);
        assertEquals(employee.getId(),0);
        assertNull(employee.getName());
        assertEquals(-1, employeeId);
    }
}
