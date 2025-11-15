package attestation3.tests.contract;

import attestation3.enitities.EmployeeRequest;
import attestation3.helpers.AuthHelper;
import attestation3.helpers.EmployeeHelperDB;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.sql.SQLException;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class GetEmployeeContractTest {

    private static EmployeeHelperDB employeeHelperDB;
    private AuthHelper authHelper;
    private int createdEmployeeId;
    private String employeeName;

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
        employeeName = employee.getName();
    }

    @Test
    @DisplayName("Получение сотрудника по имени")
    public void getEmployeeToNameCode200() {
        given().
        when().
                get("/employee/name/" + employeeName).
        then().
                statusCode(200);
    }

    @Test
    @DisplayName("Получение несуществующего сотрудника по имени")
    public void getNotExistEmployeeToNameCode404() {
        given().
        when().
                get("/employee/name/" + "Мистер Х").             //имя несуществующего сотрудника
        then().
                statusCode(404);
    }

    @Test
    @DisplayName("Получение сотрудника по id")
    public void getEmployeeToIdCode200() {
        given().
        when().
                get("/employee/" + createdEmployeeId).
        then().
                statusCode(200);
    }

    @Test
    @DisplayName("Получение несуществующего сотрудника по id")
    public void getNotExistEmployeeToIdCode404() {
        given().
        when().
                get("/employee/" + 222).                        //id несуществующего сотрудника
        then().
                statusCode(404);
    }
}
