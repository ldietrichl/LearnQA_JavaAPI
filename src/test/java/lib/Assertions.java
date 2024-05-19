package lib;

import io.restassured.response.Response;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Assertions {
    public static void assertJsonByName(Response response, String name, int expectedValue){
        response.then().assertThat().body("$", hasKey(name));

        int value = response.jsonPath().getInt(name);
        assertEquals(expectedValue,value,"JSON value is not equal to expected value");
    }



    public static void assertHeaderLength(Response response, String headerName, int minLenght) {
        String headerValue = response.getHeader(headerName);
        assertTrue(headerValue != null, "Response doesn't have '" + headerName + "' cookie");

        int actualLength = headerValue.length();
        assertTrue(actualLength>=minLenght,
                   "Header length is not equal to the expected value. Current length: "+ actualLength + "Border length: "+minLenght);

    }

    public static void assertAnswerLength(Response response,  int minLenght) {
        String bodyValue = response.getBody().toString();
        assertTrue(bodyValue != null, "Response doesn't have body");

        int actualLength = bodyValue.length();
        assertTrue(actualLength>=minLenght,
                "Body length is not equal to the expected value. Current length: "+ actualLength + ". Border length: "+minLenght);

    }
}
