import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class Ex5 {
    @Test
    public void SecondMessage() {

        JsonPath response = RestAssured
                .given()
                .get("https://playground.learnqa.ru/api/get_json_homework")
                .jsonPath();

        String answer = response.getString("messages.message[1]");
        System.out.println(answer);

    }
}