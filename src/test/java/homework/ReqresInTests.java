package homework;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.is;


public class ReqresInTests {

    @BeforeEach
    public void beforeEach(){
        baseURI = "https://reqres.in";
        basePath = "/api";
    }

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
    void checkCreateUser() {
        String body = "{ \"name\": \"morpheus\", \"job\": \"leader\" }";
        given()
                .log().uri()
                .log().body()
                .contentType(JSON)
                .body(body)
                .when()
                .post("/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body(matchesJsonSchemaInClasspath("schemes/create-user-schema.json"))
                .body("name", is("morpheus"))
                .body("job", is("leader"));
    }

    @Test
    void checkUpdateUserPut() {
        String body = "{ \"name\": \"morpheus\", \"job\": \"zion resident\"\n}";
        given()
                .log().uri()
                .log().body()
                .contentType(JSON)
                .body(body)
                .when()
                .put("/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("name", is("morpheus"))
                .body("job", is("zion resident"));
    }

    @Test
    void checkUpdateUserPatch() {
        String body = "{ \"name\": \"morpheus\", \"job\": \"zion\"\n}";
        given()
                .log().uri()
                .log().body()
                .contentType(JSON)
                .body(body)
                .when()
                .patch("/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("name", is("morpheus"))
                .body("job", is("zion"));
    }

    @Test
    void checkDeleteUser() {
        given()
                .log().uri()
                .when()
                .delete("/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(204);
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
