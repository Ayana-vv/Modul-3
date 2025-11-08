package restAssured.tests;

import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import restAssured.entites.EmployeeRequest;
import restAssured.entites.EmployeeResponse;
import restAssured.helpers.AuthHelper;
import restAssured.helpers.EmployeeHelper;

import java.util.List;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CreateEmployeeBusinessTest {

    private EmployeeHelper employeeHelper;

    @BeforeAll
    public static void setUri() {
        baseURI = "https://innopolispython.onrender.com";
    }

    @BeforeEach
    public void setUp(){
        employeeHelper = new EmployeeHelper();
    }

    @Test
    @DisplayName("Создание сотрудника")
    public void createEmployee() {
        int employeeId = employeeHelper.createEmployee(new EmployeeRequest("Barcelona", "Test5", "driver", "Testov"));
        EmployeeResponse employee = employeeHelper.getEmployee(employeeId);  //ИСПОЛЬЗОВАТЬ БД
        //Преимущества БД:
        //1.Стабильность
        //2.Быстрота запроса к БД
        //Недостатки БД:
        //1.Иногда 1 эндпоинт выполняет не просто создание объекта в 1табл, а может запускать целую цепочку событий
        //2.Может не быть доступа к БД
        //3.Подключение к БД и написание запросов может быть сложнее, чем отправки АПИ запроса
        //4.Процессы могут меняться
        assertEquals(employeeId, employee.getId());
    }

    @Test
    @DisplayName("Создание сотрудника с пустым полем")
    public void createEmployeeWithEmptyBody() {
        int employeeId = employeeHelper.createEmployee(new EmployeeRequest());
        EmployeeResponse employee = employeeHelper.getEmployee(employeeId);
        assertEquals(employee.getId(),0);
        assertNull(employee.getName());
        assertEquals(-1, employeeId);
    }
}
