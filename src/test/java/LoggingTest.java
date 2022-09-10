import io.restassured.RestAssured;
import org.testng.annotations.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.is;

public class LoggingTest {

    public static final String DUMMY_JSON_PATH = "https://reqres.in/api/users";

    @Test
    public void logTest() {
        RestAssured
                .given()
                    .log().headers()
                    .log().body()
                .when()
                    .get(DUMMY_JSON_PATH)
                .then()
//                    .log().body()
//                    .log().headers()
                    .log().ifStatusCodeIsEqualTo(SC_OK)
                    .body("data[0].id", is(1));
    }
}
