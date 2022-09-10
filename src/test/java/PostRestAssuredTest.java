import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import static org.apache.http.HttpStatus.SC_OK;

public class PostRestAssuredTest {

    public static final String GITHUB_PATH = "https://api.github.com/user/repos";
    public static final String TOKEN = "TEST";

    @Test(description = "create a repo")
    public void postTest() {
        RestAssured
                .given()
                    .header("Authorization", "token " + TOKEN)
                    .body("{\"name\":\"deleteme\"}")
                .when()
                    .post(GITHUB_PATH)
                .then()
                    .statusCode(HttpStatus.SC_CREATED);
    }
    
    @Test(description = "update a repo")
    public void patchTest() {
        RestAssured
                .given()
                    .header("Authorization", "token " + TOKEN)
                    .body("{\"name\":\"deleteme-patched\"}")
                .when()
                    .post("https://api.github.com/repos/ahmeed83/deleteme")
                .then()
                    .statusCode(SC_OK);
    }

    @Test(description = "delete a repo")
    public void deleteTest() {
        RestAssured
                .given()
                    .header("Authorization", "token " + TOKEN)
                .when()
                    .delete("https://api.github.com/repos/ahmeed83/deleteme-patched")
                .then()
                    .statusCode(HttpStatus.SC_NO_CONTENT);
    }
}
