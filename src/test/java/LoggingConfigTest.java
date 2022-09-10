import io.restassured.RestAssured;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.is;

public class LoggingConfigTest {
    
    @BeforeSuite
    public void setup() {
        RestAssured.config = ConfigFactory.defaultConfig();
    }

    @Test
    public void logTestConfig() {
        RestAssured
                .given()
                .when()
                .get()
                .then()
                .body("id[0]", is(2));
    }
}
