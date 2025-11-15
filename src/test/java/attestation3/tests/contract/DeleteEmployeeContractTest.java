package attestation3.tests.contract;

import attestation3.helpers.EmployeeHelperDB;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import attestation3.enitities.EmployeeRequest;
import attestation3.helpers.AuthHelper;

import java.io.IOException;
import java.sql.SQLException;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class DeleteEmployeeContractTest {

    private static EmployeeHelperDB employeeHelperDB;
    private AuthHelper authHelper;
    private int createdEmployeeId;

    @BeforeAll
    public static void setUri() throws SQLException, IOException {
        baseURI = "https://innopolispython.onrender.com";
        employeeHelperDB = new EmployeeHelperDB();
    }

    @BeforeEach
    public void setUp() throws SQLException {
        authHelper = new AuthHelper();
        EmployeeRequest employee = new EmployeeRequest("Barcelona", "Test5", "driver", "Testov");
        createdEmployeeId = employeeHelperDB.createEmployee(employee);
    }

    @Test
    @DisplayName("Удаление сотрудника")
    public void deleteEmployeeCode200() {
        given().
        when().
                delete("/employee/" + createdEmployeeId).
        then().
                statusCode(200);
    }

    @Test
    @DisplayName("Удаление несуществующего сотрудника")
    public void deleteNotExistEmployeeCode404() {
        given().
        when().
                delete("/employee/" + 222).             //id несуществующего сотрудника
        then().
                statusCode(404);
    }

    @Disabled("Баг - Удаляется сотрудник, хотя в body переданы данные другого сотрудника")         //это баг, тк нет проверки на body
    @Test
    @DisplayName("Удаление сотрудника с другими данными")
    public void deleteEmployeeOtherFields() {
        String token = authHelper.getToken("admin", "admin");
        given().
                body(new EmployeeRequest("London","Test","QA","String","email")).
                contentType(ContentType.JSON).header("Authorization", "Bearer " + token).
        when().
                delete("/employee/" + createdEmployeeId).
        then().
                statusCode(400);
    }
}
