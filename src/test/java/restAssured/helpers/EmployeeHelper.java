package restAssured.helpers;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import restAssured.entites.EmployeeRequest;
import restAssured.entites.EmployeeResponse;
import restAssured.entites.User;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class EmployeeHelper {

    private AuthHelper authHelper;

    public EmployeeHelper() {
        authHelper = new AuthHelper();
        baseURI = "https://innopolispython.onrender.com";
    }

    public int createEmployee(EmployeeRequest employee) {
        String token = authHelper.getToken("admin", "admin");
        JsonPath jsonPath = given().
                body(employee).contentType(ContentType.JSON).header("Authorization", "Bearer " + token).
                when().
                post("/employee").jsonPath();
        try {
            return  jsonPath.getInt("id");
        }
        catch (NullPointerException nullPointerException) {
            return  -1;
        }
    }

    public EmployeeResponse getEmployee(int id) {
        Response response = given().
                when().
                get("/employee/" + id);
        try {
            return response.as(EmployeeResponse.class);
        }
        catch (IllegalStateException exception) {
            return new EmployeeResponse();
        }
    }

    public void deleteEmployee(int id) {
        given().
        when().
                delete("/employee/" + id);
    }
}
