package lesson.tests.demowebshop;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class CartTests extends TestBase {

    @Test
    void addToCartAsAuthorizedTest() {
        // move to api class
        String authCookieKey = "NOPCOMMERCE.AUTH";
        String authCookieValue = given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("Email", login)
                .formParam("Password", password)
                .when()
                .post("/login")
                .then()
                .log().all()
                .statusCode(302)
                .extract()
                .cookie(authCookieKey);

        // get actual cart size

        String data = "product_attribute_72_5_18=52" +
                "&product_attribute_72_6_19=54" +
                "&product_attribute_72_3_20=58" +
                "&addtocart_72.EnteredQuantity=2";

        given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .cookie(authCookieKey, authCookieValue)
                .body(data)
                .when()
                .post("/addproducttocart/details/72/1")
                .then()
                .log().all()
                .statusCode(200)
                .body("success", is(true))
                .body("message", is("The product has been added to your <a href=\"/cart\">shopping cart</a>"));
        // todo check cart size
    }

    @Test
    void addToCartAsAnonymTest() {
        String data = "product_attribute_72_5_18=52" +
                "&product_attribute_72_6_19=54" +
                "&product_attribute_72_3_20=58" +
                "&addtocart_72.EnteredQuantity=2";

        given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .body(data)
                .when()
                .post("/addproducttocart/details/72/1")
                .then()
                .log().all()
                .statusCode(200)
                .body("success", is(true))
                .body("message", is("The product has been added to your <a href=\"/cart\">shopping cart</a>"))
                .body("updatetopcartsectionhtml", is("(2)"));
    }
}
