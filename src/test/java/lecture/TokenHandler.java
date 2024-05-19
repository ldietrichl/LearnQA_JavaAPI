import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Test;
import io.restassured.path.json.JsonPath;


import java.lang.Thread;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


public class TokenHandler {



    @Test
    public void GetToken(){
        int time = 0;
        String token="";


            JsonPath response1 = RestAssured
                    .given()
                    .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                    .jsonPath();

             token = response1.getString("token");
            System.out.println("Token: " + token);
             time = Integer.valueOf(response1.getString("seconds"));
            System.out.println("Process time: " + time);

            Response response2 = RestAssured
                    .given()
                    .queryParam("token",token)
                    .get("https://playground.learnqa.ru/ajax/api/longtime_job");
                    response2.andReturn();
                    response2.prettyPrint();

                    int timems=time*1000;
            try {
                Thread.sleep(timems);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


            ValidatableResponse response3 = RestAssured
                    .given()
                    .queryParam("token", token)
                    .when()
                    .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                    .then()
                    .assertThat()
                    .body("status",
                            equalTo("Job is ready"))
                    .body("result", notNullValue());;
        System.out.println("Тело ответа: " + response3.extract().body().asString());





    }


}