package restAssured.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;

public class GetEmployeesContractTest {

    @Test
    @DisplayName("Проверить код ответа при получени списка сотрудников")
    public void getEmployeesCode200() {
        //given : дано - урл, тело, хэдер и тд
        //when : когда - get()
        //then : тогда - проверки
        given().baseUri("https://innopolispython.onrender.com").when().get("/employees").then().
                statusCode(200).header("Content-Type", "application/json");
//        given().
//                baseUri("https://innopolispython.onrender.com").
//        when().
//                get("/employees").
//        then().
//                statusCode(200).header("Content-Type", "application/json");
    }
}
