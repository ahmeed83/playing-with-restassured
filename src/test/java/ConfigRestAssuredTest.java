import io.restassured.RestAssured;
import io.restassured.config.FailureConfig;
import io.restassured.config.RedirectConfig;
import io.restassured.listener.ResponseValidationFailureListener;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

public class ConfigRestAssuredTest {

    public static final String PATH = "https://api.github.com/";

    @Test
    public void redirectTest() {
        RestAssured.config =
                RestAssured.config().redirect(RedirectConfig.redirectConfig().followRedirects(false).maxRedirects(1));

        RestAssured.get(PATH + "repos/twitter/bootstrap").then().statusCode(HttpStatus.SC_MOVED_PERMANENTLY);
    }

    @Test
    public void failureTest() {

        ResponseValidationFailureListener failureListener = (reqSpec, respSpec, response) -> System.out.printf(
                "There is a failure: response status was %s and body contained %s", response.getStatusCode(),
                response.body().asPrettyString());

        RestAssured.config =
                RestAssured.config().failureConfig(FailureConfig.failureConfig().failureListeners(failureListener));

        RestAssured.get(PATH + "users/ahmeed83").then().body("some.path", equalTo("something"));
    }
}
