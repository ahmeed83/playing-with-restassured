import io.restassured.RestAssured;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.is;

public class ConfigBasicRestAssuredTest {

    @BeforeSuite
    public void setup() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api/users";
        RestAssured.rootPath = "data";
    }

    @Test
    public void test1() {
        RestAssured.get().then().body("id[0]", is(1));
    }

    @Test
    public void test2() {
        RestAssured.get().then().body("id[1]", is(2));
    }
}
