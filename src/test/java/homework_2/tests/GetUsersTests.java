package homework_2.tests;

import homework_2.models.getUsersResponse.RootModel;
import homework_2.testData.UsersList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static homework_2.specs.Specs.*;
import static homework_2.utils.RandomUtils.getRandomElementFromList;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

@Tag("api")
@DisplayName("Get Users API")
public class GetUsersTests {

    @Test
    @DisplayName("Successful getting user data")
    void checkEmailUserTest() {
        List<UsersList> usersList = new ArrayList<>();
        usersList.add(new UsersList(1, "george.bluth@reqres.in", "George", "Bluth", "https://reqres.in/img/faces/1-image.jpg"));
        usersList.add(new UsersList(2, "janet.weaver@reqres.in", "Janet", "Weaver", "https://reqres.in/img/faces/2-image.jpg"));
        usersList.add(new UsersList(3, "emma.wong@reqres.in", "Emma", "Wong", "https://reqres.in/img/faces/3-image.jpg"));
        usersList.add(new UsersList(4, "eve.holt@reqres.in", "Eve", "Holt", "https://reqres.in/img/faces/4-image.jpg"));
        usersList.add(new UsersList(5, "charles.morris@reqres.in", "Charles", "Morris", "https://reqres.in/img/faces/5-image.jpg"));
        usersList.add(new UsersList(6, "tracey.ramos@reqres.in", "Tracey", "Ramos", "https://reqres.in/img/faces/6-image.jpg"));
        usersList.add(new UsersList(7, "michael.lawson@reqres.in", "Michael", "Lawson", "https://reqres.in/img/faces/7-image.jpg"));
        usersList.add(new UsersList(8, "lindsay.ferguson@reqres.in", "Lindsay", "Ferguson", "https://reqres.in/img/faces/8-image.jpg"));
        usersList.add(new UsersList(9, "tobias.funke@reqres.in", "Tobias", "Funke", "https://reqres.in/img/faces/9-image.jpg"));
        usersList.add(new UsersList(10, "byron.fields@reqres.in", "Byron", "Fields", "https://reqres.in/img/faces/10-image.jpg"));
        usersList.add(new UsersList(11, "george.edwards@reqres.in", "George", "Edwards", "https://reqres.in/img/faces/11-image.jpg"));
        usersList.add(new UsersList(12, "rachel.howell@reqres.in", "Rachel", "Howell", "https://reqres.in/img/faces/12-image.jpg"));

        UsersList user = getRandomElementFromList(usersList);
        int size = usersList.size();
        int page = 1;
        int totalPages = 1;
        String supportUrl = "https://reqres.in/#support-heading";
        String supportText = "To keep ReqRes free, contributions towards server costs are appreciated!";

        RootModel response = step("Make request", () ->
                given(requestSpec)
                        .when()
                        .queryParam("per_page", String.format("%d", size))
                        .get("/users")
                        .then()
                        .spec(response200Spec)
                        .extract().as(RootModel.class));

        step("Check per_page in response body", () ->
                assertEquals(page, response.getPage()));

        step("Check page in response body", () ->
                assertEquals(size, response.getPerPage()));

        step("Check total in response body", () ->
                assertEquals(size, response.getTotal()));

        step("Check total_pages in response body", () ->
                assertEquals(totalPages, response.getTotalPages()));

        step("Check result in users list for random user", () -> assertAll(
                () -> assertEquals(user.getId(), response.getData().get(user.getId() - 1).getId()),
                () -> assertEquals(user.getEmail(), response.getData().get(user.getId() - 1).getEmail()),
                () -> assertEquals(user.getFirstName(), response.getData().get(user.getId() - 1).getFirstName()),
                () -> assertEquals(user.getLastName(), response.getData().get(user.getId() - 1).getLastName()),
                () -> assertEquals(user.getAvatar(), response.getData().get(user.getId() - 1).getAvatar())
        ));

        step("Check support.url in response body", () ->
                assertEquals(supportUrl, response.getSupport().getUrl()));

        step("Check support.text in response body", () ->
                assertEquals(supportText, response.getSupport().getText()));
    }

    @Test
    @DisplayName("Unsuccessful getting user data")
    void checkNegativeUserNot() {
        step("Make request", () ->
                given(requestSpec)
                        .when()
                        .get("/users/23")
                        .then()
                        .spec(response404Spec)
                        .extract().as(RootModel.class));
    }
}