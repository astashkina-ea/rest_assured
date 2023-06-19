package homework_2.tests;

import homework_2.models.*;
import homework_2.utils.RandomUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static homework_2.data.enums.ErrorTextsForLogin.*;
import static homework_2.specs.Specs.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Tag("api")
public class LoginUserTests {

    @Test
    @DisplayName("Successful login")
    void successfulLoginTest() {
        LoginUserNameRequestModel requestBody = new LoginUserNameRequestModel();

        requestBody.setEmail("eve.holt@reqres.in");
        requestBody.setPassword("cityslicka");

        LoginUserNameResponseModel response = step("Make request", () ->
                given(requestSpec)
                        .body(requestBody)
                        .when()
                        .post("/login")
                        .then()
                        .spec(response200Spec)
                        .extract().as(LoginUserNameResponseModel.class));

        step("Check error text in response", () ->
                assertNotNull(response.getToken()));
    }

    static Stream<Arguments> loginAndPasswordDataProvider() {
        return Stream.of(
                Arguments.of(RandomUtils.getRandomEmail(), RandomUtils.getRandomPassword(), USER_NOT_FOUND.getText()),
                Arguments.of(RandomUtils.getRandomEmail(), "", MISSING_PASSWORD.getText()),
                Arguments.of("", RandomUtils.getRandomPassword(), MISSING_EMAIL_OR_USERNAME.getText())
        );
    }

    @MethodSource("loginAndPasswordDataProvider")
    @DisplayName("Unsuccessful login with")
    @ParameterizedTest(name = "email: {0} and password:{1}")
    void unsuccessfulLoginTest(String email, String password, String errorText) {

        LoginUserNameRequestModel requestBody = new LoginUserNameRequestModel();

        requestBody.setEmail(email);
        requestBody.setPassword(password);

        LoginUserNameUnsuccessfulResponseModel response = step("Make request", () ->
                given(requestSpec)
                        .body(requestBody)
                        .when()
                        .post("/login")
                        .then()
                        .spec(response400Spec)
                        .extract().as(LoginUserNameUnsuccessfulResponseModel.class));

        step("Check error text in response", () ->
                assertEquals(errorText, response.getError()));
    }
}