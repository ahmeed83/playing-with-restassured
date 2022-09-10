import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.internal.mapping.Jackson2Mapper;
import org.example.Product;
import org.example.ProductNoAnn;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class ObjectMarshalingTest {

    public static final String DUMMY_JSON_PATH = "https://dummyjson.com/products/1";

    @Test
    public void testOne() {
        Product product = RestAssured.get(DUMMY_JSON_PATH).as(Product.class);

        assertEquals(product.getId(), 1);
        assertEquals(product.getTitle(), "iPhone 9");
        assertEquals(product.getDiscountPercentage(), 12.96);
    }

    @Test
    public void testOneMapper() {

        ProductNoAnn product = RestAssured.get(DUMMY_JSON_PATH).as(ProductNoAnn.class, getMapper());

        assertEquals(product.getId(), 1);
        assertEquals(product.getTitle(), "iPhone 9");
        assertEquals(product.getDiscountPercentage(), 12.96);
    }
    
    private Jackson2Mapper getMapper() {
        return new Jackson2Mapper((type, s) -> {
            ObjectMapper obj = new ObjectMapper();
            obj.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return obj;
        });
    }
}
