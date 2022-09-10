import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.is;

public class ResponseSpecTest {

    public static final String DUMMY_JSON_PATH = "https://reqres.in/api/users";

    @Test
    public void test1() {
        RestAssured.get(DUMMY_JSON_PATH)
                .then()
                .spec(responseSpecification())
                .body("data[0].id", is(1));
    }

    @Test
    public void test2() {
        RestAssured.get(DUMMY_JSON_PATH)
                .then()
                .spec(responseSpecification())
                .body("data[1].id", is(2));
    }

    public static ResponseSpecification responseSpecification() {
        return new ResponseSpecBuilder()
                .expectStatusCode(SC_OK)
                .expectContentType(ContentType.JSON)
                .build();
    }
}
