package restAssured.tests;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import restAssured.entites.EmployeeRequest;
import restAssured.entites.EmployeeResponse;
import restAssured.entites.ValidationErrorResponse;
import restAssured.helpers.AuthHelper;
import restAssured.helpers.EmployeeHelper;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.isA;

public class GetEmployeeContractTest {

    private EmployeeHelper employeeHelper;
    private int createdEmployeeId;

    @BeforeAll
    public static void setUri() {
        baseURI = "https://innopolispython.onrender.com";
    }

    @BeforeEach
    public void setUp(){
        employeeHelper =new EmployeeHelper();
        EmployeeRequest employee = new EmployeeRequest("Barcelona", "Test5", "driver", "Testov");
        createdEmployeeId = employeeHelper.createEmployee(employee);
    }

    @Test
    @DisplayName("Проверить типы параметров в ответе при получении списка сотрудников")
    public void getEmployeeResponseTypes() {
        given().
        when().
                get("/employee/" + createdEmployeeId).
//                get("/employee/" + createdEmployeeId).prettyPrint(); //проверка добавив prettyPrint(); и без then
        then().
                body("city",isA(String.class)).
                body("name",isA(String.class)).
                body("surname",isA(String.class)).
                body("position",isA(String.class));
    }

    @Test
    @DisplayName("2й способ - Проверить типы параметров в ответе при получении списка сотрудников")
    public void getEmployeeResponseTypes2() {
        EmployeeResponse employee = given().
        when().
                get("/employee/" + createdEmployeeId).as(EmployeeResponse.class);
        System.out.println(employee.getCity());
        System.out.println(employee.getName());
        System.out.println(employee.getSurname());
        System.out.println(employee.getPosition());
        System.out.println(employee.getId());
    }

    @Disabled
    @Test
    @DisplayName("Получение несуществующего сотрудника")
    public void getNonexistentEmployee() {
        given().
                when().
                get("/employee/" + 5999).as(EmployeeResponse.class);
    }
}
