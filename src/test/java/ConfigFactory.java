import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.config.FailureConfig;
import io.restassured.config.LogConfig;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RedirectConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.listener.ResponseValidationFailureListener;
import io.restassured.path.json.mapper.factory.Jackson2ObjectMapperFactory;

public class ConfigFactory {

    public static RestAssuredConfig defaultConfig() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api/users";
        RestAssured.rootPath = "data";

        ResponseValidationFailureListener failureListener = (reqSpec, respSpec, response) -> System.out.printf(
                "There is a failure: response status was %s and body contained %s", response.getStatusCode(),
                response.body().asPrettyString());

        return RestAssured.config()
                .logConfig(LogConfig.logConfig().enablePrettyPrinting(true))
                .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails())
                .failureConfig(FailureConfig.failureConfig().failureListeners(failureListener))
                .redirect(RedirectConfig.redirectConfig().followRedirects(false).maxRedirects(1))
                .objectMapperConfig(ObjectMapperConfig.objectMapperConfig().jackson2ObjectMapperFactory(getMapper()));
    }

    private static Jackson2ObjectMapperFactory getMapper() {
        return ((type, s) -> {
            ObjectMapper obj = new ObjectMapper();
            obj.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return obj;
        });
    }
}
