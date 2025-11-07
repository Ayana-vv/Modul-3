package restAssured.tests;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import restAssured.entites.EmployeeRequest;
import restAssured.entites.ValidationErrorResponse;
import restAssured.helpers.AuthHelper;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class UpdateEmployeeContractTest {

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
    @DisplayName("Изменение полей у сотрудника и проверка тела")
    public void updateEmployeeCode400() {
        String token = authHelper.getToken("admin", "admin");
        ValidationErrorResponse employee = given().
                body(new EmployeeRequest("Test5", "driver", "Testov")).
                contentType(ContentType.JSON).header("Authorization", "Bearer " + token).
        when().
                put("/employee/" + "2216").
        then().
                statusCode(400).extract().body().as(ValidationErrorResponse.class);

        System.out.println(employee.getMessage());
        System.out.println(employee.getError());
        System.out.println(employee.getWrongTypeFields());
    }
}
