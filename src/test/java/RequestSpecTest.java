import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.example.ProductNoAnn;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class RequestSpecTest {

    public static final String DUMMY_JSON_PATH = "https://dummyjson.com/products/1";
    
    @Test
    public void testOneMapper() {

        ProductNoAnn product = RestAssured
                .given()
                .spec(requestSpecification())
                .when()
                .get()
                .as(ProductNoAnn.class);

        assertEquals(product.getId(), 1);
        assertEquals(product.getTitle(), "iPhone 9");
        assertEquals(product.getDiscountPercentage(), 12.96);
    }


    public static RequestSpecification requestSpecification() {
        return new RequestSpecBuilder()
                .setBaseUri(DUMMY_JSON_PATH)
                .setConfig(ConfigFactory.defaultConfig())
                .build();
    }
}
