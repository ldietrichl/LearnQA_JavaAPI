package lecture;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class Ex7_long_redirect {
    @Test
    public void SecondMessage() {

        Response response = RestAssured
                .given()
                .redirects()
                .follow(false)
                .when()
                .get("https://playground.learnqa.ru/api/long_redirect")
                .andReturn();

        String locatiuonHeader  = response.getHeader("Location");
        System.out.println(locatiuonHeader);
        int statusCode = response.getStatusCode();
        System.out.println(statusCode);
    }
}