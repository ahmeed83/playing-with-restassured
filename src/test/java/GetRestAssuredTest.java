import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.number.OrderingComparison;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.time.LocalDate.now;
import static java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

public class GetRestAssuredTest {

    public static final String GITHUB_PATH = "https://api.github.com";
    public static final String REQ_RES_PATH = "https://reqres.in/api/users";

    Map<String, String> headers =
            Map.of("Content-Encoding", "gzip", "X-Content-Type-Options", "nosniff", "X-GitHub-Media-Type",
                   "github.v3; format=json", "Access-Control-Allow-Origin", "*");

    @Test
    public void testHeaders() {
        RestAssured.get(GITHUB_PATH)
                .prettyPeek()
                .then()
                .header("etag", not(emptyString()))
                .header("Access-Control-Allow-Origin", notNullValue())
                .header("Content-Length", Integer::parseInt, lessThan(550))
                .header("date", date -> LocalDate.parse(date, RFC_1123_DATE_TIME),
                        OrderingComparison.comparesEqualTo(now()))
                .headers(headers)
                .contentType(ContentType.JSON)
                .statusCode(lessThan(300))
                .statusCode(SC_OK)
                .time(lessThan(1L), TimeUnit.SECONDS);
    }

    @Test
    public void testBody() {
        RestAssured.get(GITHUB_PATH)
                .prettyPeek()
                .then()
                .body("current_user_url", equalTo(GITHUB_PATH + "/user"))
                .body(containsString("emojis_url"))
                .body(containsString("emojis_url"), containsString("https://api.github.com/orgs/{org}/teams"));
    }

    @Test
    public void testBodyUser() {
        RestAssured.get(GITHUB_PATH + "/users/ahmeed83").then()
                // url contains login --> ahmeed83
                //"url": "https://api.github.com/users/ahmeed83",
                //"login": "ahmeed83",
                .body("url", response -> containsString(response.body().jsonPath().get("login")));
    }

//    @Test
    public void testBodyRateLimit() {
        RestAssured.get(GITHUB_PATH + "/rate_limit")
                .then()
                .rootPath("resources.core")
                .body("limit", equalTo(60))
                .rootPath("resources.graphql")
                .body("resource", equalTo("graphql"))
                .noRootPath()
                .body("resources.graphql.limit", equalTo(0));
    }

    @Test
    public void testBodyReqRes() {
        RestAssured.get(REQ_RES_PATH)
                .then()
                .body("page", equalTo(1))
                .rootPath("data[0]")
                .body("email", equalTo("george.bluth@reqres.in"))
                .rootPath("support")
                .body("text", equalTo("To keep ReqRes free, contributions towards server costs are appreciated!"))
                .noRootPath()
                .body("total", equalTo(12));
    }

    @Test
    public void testBodyNames() {
        RestAssured.get(REQ_RES_PATH)
                .then()
                .rootPath("data[0]")
                .body("id", equalTo(1))
                .body("first_name", equalTo("George"))
                .rootPath("data[2]")
                .body("id", equalTo(3))
                .body("first_name", equalTo("Emma"))
                .noRootPath()
                .body("data.first_name", hasItems("George", "Emma", "Tracey"))
                .body("data.first_name", hasItems(endsWith("ma")));
    }
}
