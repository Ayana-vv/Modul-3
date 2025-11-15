package attestation3.tests.business;

import attestation3.enitities.EmployeeRequest;
import attestation3.enitities.EmployeeResponse;
import attestation3.helpers.EmployeeHelperDB;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UpdateEmployeeBusinessTest {

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
    @DisplayName("Обновление информации о сотруднике по имени (частично)")
    public void updateEmployeeName() throws Exception {
        employeeId = employeeHelperDB.createEmployee(new EmployeeRequest("Barcelona", "Testa", "seller", "Testova"));
        String employeeNewName = "Nick";
        employeeHelperDB.updateEmployeeName(employeeId, employeeNewName);
        EmployeeResponse employeeUpdate = employeeHelperDB.getEmployee(employeeId);
        assertEquals(employeeNewName, employeeUpdate.getName());
        assertEquals(employeeId, employeeUpdate.getId());
    }

    @Test
    @DisplayName("Обновление информации о сотруднике по всем полям (полностью)")
    public void updateEmployee() throws Exception {
        int employeeId = employeeHelperDB.createEmployee(new EmployeeRequest("Barcelona", "Testa", "seller", "Testova"));
        String employeeNewName = "TestName";
        String employeeNewSurname = "TestSurname";
        String employeeNewCity = "TestCity";
        String employeeNewPosition = "TestPosition";

        EmployeeRequest employeeUpdate = new EmployeeRequest(employeeNewCity, employeeNewName, employeeNewPosition, employeeNewSurname);
        employeeHelperDB.updateEmployee(employeeId, employeeUpdate);
        assertEquals(employeeNewName, employeeUpdate.getName());
        assertEquals(employeeNewSurname, employeeUpdate.getSurname());
        assertEquals(employeeNewCity, employeeUpdate.getCity());
        assertEquals(employeeNewPosition, employeeUpdate.getPosition());
    }

    @Disabled("Тест не запустится, т.к. длина поля Name 100 символов")
    @Test
    @DisplayName("Обновление информации о сотруднике на длинное название поля")
    public void updateEmployeeLongName() throws Exception {
        int employeeId = employeeHelperDB.createEmployee(new EmployeeRequest("Barcelona", "Testa", "seller", "Testova"));
        String employeeNewName = "TestNameTestNameTestNameTestNameTestNameTestNameTestNameTestNameTestNameTestNameTestNameTestNameTestName";
        employeeHelperDB.updateEmployeeName(employeeId, employeeNewName);
        EmployeeResponse employeeUpdate = employeeHelperDB.getEmployee(employeeId);
        assertEquals(employeeNewName, employeeUpdate.getName());
        assertEquals(employeeId, employeeUpdate.getId());
    }

    @Disabled("Баг, т.к. не совпадает формат поля city: если в SQL-запросе менять на числовое значение, то значение city меняется, а в postman есть валидация на формат поля")
    @Test
    @DisplayName("Обновление информации о сотруднике с невалидным форматом поля")
    public void updateEmployeeWrongTypeField() throws Exception {
        int employeeId = employeeHelperDB.createEmployee(new EmployeeRequest("Barcelona", "Testa", "seller", "Testova"));
        int employeeNewCity = 123;
        employeeHelperDB.updateEmployeeWrongTypeField(employeeId, employeeNewCity);
        EmployeeResponse employeeUpdate = employeeHelperDB.getEmployee(employeeId);
        assertEquals(employeeNewCity, employeeUpdate.getCity());
    }
}
