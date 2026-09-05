package api.base;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

public class BaseApiTest {

    protected RequestSpecification reqSpec;

    @BeforeClass
    public void setUp(){
        reqSpec =
                        RestAssured
                        .given()
                        .baseUri("https://api.zippopotam.us/")
                        .contentType(ContentType.JSON);

    }
}
