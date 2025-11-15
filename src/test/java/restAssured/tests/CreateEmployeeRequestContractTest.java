package restAssured.tests;

import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import restAssured.entites.EmployeeRequest;
import restAssured.entites.EmployeeResponse;
import restAssured.helpers.AuthHelper;

import java.util.List;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class CreateEmployeeRequestContractTest {

    private AuthHelper authHelper;

    @BeforeAll
    public static void setUri() {
        baseURI = "https://innopolispython.onrender.com";
    }

    @BeforeEach
    public void setUp(){
        authHelper = new AuthHelper();
    }

    @Test
    @DisplayName("Создание сотрудника")
    public void createEmployeeCode201() {
        String token = authHelper.getToken("admin", "admin");
        given().
                body(new EmployeeRequest("Barcelona", "Test5", "driver", "Testov")).
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
                body(new EmployeeRequest("Barcelona", "Test5", "driver", "Testov")).
                contentType(ContentType.JSON).
        when().
                post("/employee").
        then().
                statusCode(401);
    }

    @Disabled("Баг - не создается создать сотрудника с обязательными полями")
    @Test
    @DisplayName("Создание сотрудника только с обязательными полями")
    public void createEmployeeRequiredFieldsCode201() {
        String token = authHelper.getToken("admin", "admin");
        given().
                body(new EmployeeRequest("Test5", "driver", "Testov")).
                contentType(ContentType.JSON).header("Authorization", "Bearer " + token).
        when().
                post("/employee").
        then().
                statusCode(201);
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
    @Test
    @DisplayName("2й вариант - Создание сотрудника без обязательных полей")
    public void createEmployeeWithoutRequiredFieldsCode400ANOTHERVARIANT() {
        String token = authHelper.getToken("admin", "admin");
        given().
                body(new EmployeeRequest("Barcelona")).
                        contentType(ContentType.JSON).header("Authorization", "Bearer " + token).
        when().
                post("/employee").
        then().
                statusCode(400).body("error", is(equalTo("Missing required fields"))).
                body("missing_fields", hasItems("name","position","surname"));
    }

    @Test
    @DisplayName("3й вариант - Создание сотрудника без обязательных полей")
    public void createEmployeeWithoutRequiredFieldsCode400ANOTHERVARIANT3() {
        String token = authHelper.getToken("admin", "admin");
        ExtractableResponse<Response> response = given().
                body(new EmployeeRequest("Barcelona")).
                contentType(ContentType.JSON).header("Authorization", "Bearer " + token).
        when().
                post("/employee").
        then().
                statusCode(400).extract();

        List<String> misFields = response.jsonPath().getList("missing_fields");
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(misFields.get(0)).isEqualTo("name");
            softAssertions.assertThat(misFields.get(1)).isEqualTo("surname");
            softAssertions.assertThat(misFields.get(2)).isEqualTo("position");
        });
    }
}
