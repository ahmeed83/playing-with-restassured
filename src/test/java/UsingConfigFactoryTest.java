import io.restassured.RestAssured;
import org.example.ProductNoAnn;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class UsingConfigFactoryTest {

    public static final String DUMMY_JSON_PATH = "https://dummyjson.com/products/1";

    @BeforeSuite
    public void setup() {
        RestAssured.config = ConfigFactory.defaultConfig();
    }

    @Test
    public void testOneMapper() {

        ProductNoAnn product = RestAssured.get(DUMMY_JSON_PATH).as(ProductNoAnn.class);

        assertEquals(product.getId(), 1);
        assertEquals(product.getTitle(), "iPhone 9");
        assertEquals(product.getDiscountPercentage(), 12.96);
    }
}
