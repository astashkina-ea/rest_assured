package homework_2.tests;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;

public class GetUsersTests {

    //TO DO
    @Test
    void checkEmailUser() {
        given()
                .log().uri()
                .log().body()
                .when()
                .get("/users?page=2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data[1].email", is("lindsay.ferguson@reqres.in"));
    }

    @Test
    void checkNegativeUserNot() {
        given()
                .log().uri()
                .when()
                .get("/users/23")
                .then()
                .log().status()
                .log().body()
                .statusCode(404);
    }
}
