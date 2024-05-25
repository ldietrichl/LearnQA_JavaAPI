package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

public class Homework13 {



        @ParameterizedTest
        @ValueSource(strings = {"'Mozilla/5.0 (Linux; U; Android 4.0.2; en-us; Galaxy Nexus Build/ICL53F) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30'",
                "'Mozilla/5.0 (iPad; CPU OS 13_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) CriOS/91.0.4472.77 Mobile/15E148 Safari/604.1'",
                "'Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)'",
                "'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.77 Safari/537.36 Edg/91.0.100.0'",
                "'Mozilla/5.0 (iPad; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1'"})
        public void Homework13(String condition) {
            Map<String, String> queryParams = new HashMap<>();
            queryParams.put("User-Agent", condition);


            RequestSpecification spec = RestAssured.given();
            spec.baseUri("https://playground.learnqa.ru/ajax/api/user_agent_check");
            spec.headers("User-Agent", condition);


            Response responceInitial = spec
                    .get()
                    .andReturn();

            responceInitial.prettyPrint();

            String platformActual = responceInitial.jsonPath().getString("platform");
            String browserActual = responceInitial.jsonPath().getString("browser");
            String deviceActual = responceInitial.jsonPath().getString("device");
            String user_agent = responceInitial.jsonPath().getString("user_agent");

            System.out.println(platformActual);
            System.out.println(browserActual);
            System.out.println(deviceActual);


            if (condition.equals("'Mozilla/5.0 (Linux; U; Android 4.0.2; en-us; Galaxy Nexus Build/ICL53F) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30'")) {
                lib.Assertions.assertJsonBodyValue(responceInitial,"platform","Mobile");
                lib.Assertions.assertJsonBodyValue(responceInitial,"browser","No");
                lib.Assertions.assertJsonBodyValue(responceInitial,"device","Android");
            }
            if (condition.equals("'Mozilla/5.0 (iPad; CPU OS 13_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) CriOS/91.0.4472.77 Mobile/15E148 Safari/604.1'")) {
                lib.Assertions.assertJsonBodyValue(responceInitial,"platform","Mobile");
                lib.Assertions.assertJsonBodyValue(responceInitial,"browser","Chrome");
                lib.Assertions.assertJsonBodyValue(responceInitial,"device","iOS");
            }
            if (condition.equals("'Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)'")) {
                lib.Assertions.assertJsonBodyValue(responceInitial,"platform","Googlebot");
                lib.Assertions.assertJsonBodyValue(responceInitial,"browser","Unknown");
                lib.Assertions.assertJsonBodyValue(responceInitial,"device","Unknown");
            }
            if (condition.equals("'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.77 Safari/537.36 Edg/91.0.100.0'")) {
                lib.Assertions.assertJsonBodyValue(responceInitial,"platform","Web");
                lib.Assertions.assertJsonBodyValue(responceInitial,"browser","Chrome");
                lib.Assertions.assertJsonBodyValue(responceInitial,"device","No");
            }
            if (condition.equals("'Mozilla/5.0 (iPad; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1'")) {
                lib.Assertions.assertJsonBodyValue(responceInitial,"platform","Mobile");
                lib.Assertions.assertJsonBodyValue(responceInitial,"browser","No");
                lib.Assertions.assertJsonBodyValue(responceInitial,"device","iPhone");
            }


        }
    }


