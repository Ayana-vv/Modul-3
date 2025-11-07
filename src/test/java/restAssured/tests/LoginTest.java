package restAssured.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import restAssured.entites.User;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class LoginTest {

    @BeforeAll
    public static void setUp() {
        baseURI = "https://innopolispython.onrender.com";
    }

    @Test
    @DisplayName("Успешная авторизация")
    public void validLogin() {
        given().
                body(new User("admin","admin")).contentType(ContentType.JSON).
        when().
                post("/login").
        then().
                statusCode(200).body("token",is(not(blankOrNullString())));
    }
}
