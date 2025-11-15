package attestation3.tests.contract;

import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import attestation3.enitities.EmployeeRequest;
import attestation3.helpers.AuthHelper;
import attestation3.helpers.EmployeeHelperDB;

import java.io.IOException;
import java.sql.SQLException;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class CreateEmployeeContractTest {

    private AuthHelper authHelper;
    private int employeeId;

    @BeforeAll
    public static void setUri() throws SQLException, IOException {
        baseURI = "https://innopolispython.onrender.com";
    }

    @BeforeEach
    public void setUp() throws SQLException {
        authHelper = new AuthHelper();
    }

    @Test
    @DisplayName("Создание сотрудника")
    public void createEmployeeCode201() {
        String token = authHelper.getToken("admin", "admin");
        given().body(new EmployeeRequest("Barcelona", "Test5", "driver", "Testov")).
                contentType(ContentType.JSON).header("Authorization", "Bearer " + token).
        when().
                post("/employee").
        then().
                statusCode(201);
    }

    @Test
    @DisplayName("Создание сотрудника без авторизации")
    public void createEmployeeCode401() {
        given().
        when().
                post("/employee").
        then().
                statusCode(401);
    }

    @Test
    @DisplayName("Создание сотрудника без обязательных полей")
    public void createEmployeeWithoutRequiredFieldsCode400() {
        String token = authHelper.getToken("admin", "admin");
        ExtractableResponse<Response> response = given().
                body(new EmployeeRequest("Barcelona")).                               //без обязательных полей
                contentType(ContentType.JSON).header("Authorization", "Bearer " + token).
        when().
                post("/employee").
        then().
                statusCode(400).extract();
        response.jsonPath().getString("missing_fields");
    }

    @Disabled("Баг - Создается сотрудник с лишними полями")         //это баг, тк в бд нет столбца для email, хоть и email добавлен в конструктор
    @Test
    @DisplayName("Создание сотрудника только с обязательными полями")
    public void createEmployeeOtherFields() {
        String token = authHelper.getToken("admin", "admin");
        given().
                body(new EmployeeRequest("London","Test","QA","String","email")).
                contentType(ContentType.JSON).header("Authorization", "Bearer " + token).
        when().
                post("/employee").
        then().
                statusCode(400);
    }
}
