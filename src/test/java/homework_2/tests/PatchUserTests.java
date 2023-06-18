package homework_2.tests;

import homework_2.models.UpdateUserResponse;
import homework_2.models.UserRequest;
import homework_2.specs.Specs;
import homework_2.utils.RandomUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static homework_2.specs.Specs.requestSpec;
import static homework_2.specs.Specs.response200Spec;
import static homework_2.utils.RandomUtils.getRandomBetweenNumber;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PatchUserTests {

    @Test
    @DisplayName("Successful user update")
    void checkUpdateUserPatch() {

        UserRequest requestBody = new UserRequest();

        String name = RandomUtils.getRandomName();
        String job = RandomUtils.getRandomJob();

        requestBody.setName(name);
        requestBody.setJob(job);


        UpdateUserResponse response = step("Make request", () ->
                given(requestSpec)
                        .body(requestBody)
                        .when()
                        .patch(String.format("/users/%d", getRandomBetweenNumber(1,12)))
                        .then()
                        .spec(response200Spec)
                        .extract().as(UpdateUserResponse.class));

        step("Check user name in response body", () ->
                assertEquals(name, response.getName()));

        step("Check user job in response body", () ->
                assertEquals(job, response.getJob()));

        step("Check update date in response", () ->
                assertNotNull(response.getUpdatedAt()));
    }
}
