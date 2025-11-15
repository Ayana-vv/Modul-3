package attestation3.tests.contract;

import attestation3.enitities.EmployeeRequest;
import attestation3.helpers.AuthHelper;
import attestation3.helpers.EmployeeHelperDB;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import restAssured.entites.ValidationErrorResponse;

import java.io.IOException;
import java.sql.SQLException;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class UpdateEmployeeContractTest {

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
    @DisplayName("Изменение имени сотрудника")
    public void updateEmployeeToNameCode200() {
        String newName = "name";
        String token = authHelper.getToken("admin", "admin");
        given().
                body(employeeName).
                contentType(ContentType.JSON).header("Authorization", "Bearer " + token).
        when().
                put("/employee/" + createdEmployeeId).
        then().
                statusCode(200);
    }

    @Test
    @DisplayName("Сотрудник не найден")
    public void updateEmployeeCode404() {
        String newName = "name";
        String token = authHelper.getToken("admin", "admin");
        given().
                body(employeeName).
                contentType(ContentType.JSON).header("Authorization", "Bearer " + token).
        when().
                put("/employee/" + 222).            //id несуществующего сотрудника
        then().
                statusCode(404);
    }
}
