package restAssured.helpers;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import restAssured.entites.User;

import static io.restassured.RestAssured.*;

public class AuthHelper {

    public AuthHelper() {
        baseURI = "https://innopolispython.onrender.com";
    }

    public String getToken(String username, String password) {
        return given().
                    body(new User(username,password)).contentType(ContentType.JSON).
                when().
                    post("/login").jsonPath().getString("token");
        //then нужны для проверок, если проверки не нужны, то сразу результат
    }
}
