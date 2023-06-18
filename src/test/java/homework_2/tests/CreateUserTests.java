package homework_2.tests;

import homework_2.models.CreateUserResponse;
import homework_2.models.UserRequest;
import homework_2.utils.RandomUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static homework_2.specs.Specs.requestSpec;
import static homework_2.specs.Specs.response201Spec;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CreateUserTests {

    @Test
    @DisplayName("Successful user creation")
    void checkCreateUserTest() {
        UserRequest requestBody = new UserRequest();

        String name = RandomUtils.getRandomName();
        String job = RandomUtils.getRandomJob();

        requestBody.setName(name);
        requestBody.setJob(job);

        CreateUserResponse response = step("Make request", () ->
                given(requestSpec)
                        .body(requestBody)
                        .when()
                        .post("/users")
                        .then()
                        .spec(response201Spec)
                        .extract().as(CreateUserResponse.class));

        step("Check user name in response body", () ->
                assertEquals(name, response.getName()));

        step("Check user job in response body", () ->
                assertEquals(job, response.getJob()));

        step("Check user id in response", () ->
                assertNotNull(response.getId()));

        step("Check date of creation in response body", () ->
                assertNotNull(response.getCreatedAt()));
    }
}