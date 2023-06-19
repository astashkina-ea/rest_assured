package homework_2.tests;

import homework_2.models.UpdateUserResponseModel;
import homework_2.models.UserRequestModel;
import homework_2.utils.DateTimeUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static homework_2.specs.Specs.requestSpec;
import static homework_2.specs.Specs.response200Spec;
import static homework_2.utils.RandomUtils.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class PutUserTests {

    @Test
    @DisplayName("Successful user update")
    void checkUpdateUserTest() {
        long currentTimeInSeconds = DateTimeUtils.getCurrentTimeInSeconds();

        UserRequestModel requestBody = new UserRequestModel();

        String name = getRandomName();
        String job = getRandomJob();

        requestBody.setName(name);
        requestBody.setJob(job);

        UpdateUserResponseModel response = step("Make request", () ->
                given(requestSpec)
                        .body(requestBody)
                        .when()
                        .put(String.format("/users/%d", getRandomBetweenNumber(1,12)))
                        .then()
                        .spec(response200Spec)
                        .extract().as(UpdateUserResponseModel.class));

        long updatedAtInSeconds = DateTimeUtils.getIsoFormatTimeToSeconds(response.getUpdatedAt());

        step("Check user name in response body", () ->
                assertEquals(name, response.getName()));

        step("Check user job in response body", () ->
                assertEquals(job, response.getJob()));

        step("Check update date in response", () ->
                assertTrue(updatedAtInSeconds >= currentTimeInSeconds));
    }
}