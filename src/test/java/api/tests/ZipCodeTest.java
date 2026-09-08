package api.tests;

import api.base.BaseApiTest;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.responseSpecification;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;

public class ZipCodeTest extends BaseApiTest {
    private static final String ENDPOINT = "{country}/{postalCode}";
   //Happy scenarios status and data validations
    @Test
    public void validUsPostalCode_returnsOk() {
        given(reqSpec)
                .pathParam("country", "us")
                .pathParam("postalCode", "90210")
        .when()
                .get(ENDPOINT)
        .then()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @Test
    public void validUsPostalCode_returnsValidData() {
        given(reqSpec)
                .pathParam("country", "us")
                .pathParam("postalCode", "90210")
        .when()
                .get(ENDPOINT)
        .then()
                .statusCode(200)
                .body("country", equalTo("United States"))
                .body("'country abbreviation'", equalTo("US"))
                .body("'post code'", equalTo("90210"))
                .body("places", is(not(empty())))
                .body("places[0].state", equalTo("California"))
                .body("places[0]['place name']", equalTo("Beverly Hills"));
    }
    //Negative scenrios
    @Test
    public void inValidUsPostalCode_returnsNotFound() {
        given(reqSpec)
            .pathParam("country", "us")
            .pathParam("postalCode", "9010")
        .when()
            .get(ENDPOINT)
        .then()
            .statusCode(404);

    }
    @Test
    public void inValidCountryCode_returnsNotFound() {
        given(reqSpec)
                .pathParam("country", "uss")
                .pathParam("postalCode", "90210")
        .when()
                .get(ENDPOINT)
        .then()
                .statusCode(404);

    }
    @Test
    public void emptyCountryCode_returnsNotFoundOrBadRequest() {
        given(reqSpec)
                .when()
                .get("https://api.zippopotam.us//90210")
                .then()
                //.log().all()
                .statusCode(anyOf(is(404), is(400)));

    }

    @Test
    public void emptypostalCode_returnsNotFoundOrBadRequest() {
        given(reqSpec)
                .when()
                .get("https://api.zippopotam.us/us/")
                .then()
                //.log().all()
                .statusCode(anyOf(is(404), is(400)));

    }
}
