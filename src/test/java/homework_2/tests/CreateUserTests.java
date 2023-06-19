package homework_2.tests;

import homework_2.models.CreateUserResponseModel;
import homework_2.models.UserRequestModel;
import homework_2.utils.DateTimeUtils;
import homework_2.utils.RandomUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static homework_2.specs.Specs.requestSpec;
import static homework_2.specs.Specs.response201Spec;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@Tag("api")
public class CreateUserTests {

    @Test
    @DisplayName("Successful user creation")
    void checkCreateUserTest() {
        long currentTimeInSeconds = DateTimeUtils.getCurrentTimeInSeconds();

        UserRequestModel requestBody = new UserRequestModel();

        String name = RandomUtils.getRandomName();
        String job = RandomUtils.getRandomJob();

        requestBody.setName(name);
        requestBody.setJob(job);

        CreateUserResponseModel response = step("Make request", () ->
                given(requestSpec)
                        .body(requestBody)
                        .when()
                        .post("/users")
                        .then()
                        .spec(response201Spec)
                        .extract().as(CreateUserResponseModel.class));

        long createdAtInSeconds = DateTimeUtils.getIsoFormatTimeToSeconds(response.getCreatedAt());

        step("Check user name in response body", () ->
                assertEquals(name, response.getName()));

        step("Check user job in response body", () ->
                assertEquals(job, response.getJob()));

        step("Check user id in response", () ->
                assertNotNull(response.getId()));

        step("Check date of creation in response body", () ->
                assertTrue(createdAtInSeconds >= currentTimeInSeconds));
    }
}