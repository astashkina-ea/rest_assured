package homework_2.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static homework_2.specs.Specs.requestSpec;
import static homework_2.specs.Specs.response204Spec;
import static homework_2.utils.RandomUtils.getRandomBetweenNumber;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;

public class DeleteUserTests {

    @Test
    @DisplayName("Successful user deletion")
    void checkDeleteUserTest() {
        step("Make request", () ->
                given(requestSpec)
                        .when()
                        .delete(String.format("/users/%d", getRandomBetweenNumber(1,12)))
                        .then()
                        .spec(response204Spec));
    }
}