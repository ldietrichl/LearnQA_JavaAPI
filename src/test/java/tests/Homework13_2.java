package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

public class Homework13_2 {

    @ParameterizedTest
    @CsvSource({
            "'Mozilla/5.0 (Linux; U; Android 4.0.2; en-us; Galaxy Nexus Build/ICL53F) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30', 'Mobile', 'No', 'Android'",
            "'Mozilla/5.0 (iPad; CPU OS 13_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) CriOS/91.0.4472.77 Mobile/15E148 Safari/604.1', 'Mobile', 'Chrome', 'iOS'",
            "'Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)', 'Googlebot', 'Unknown', 'Unknown'",
            "'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.77 Safari/537.36 Edg/91.0.100.0', 'Web', 'Chrome', 'No'",
            "'Mozilla/5.0 (iPad; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1', 'Mobile', 'No', 'iPhone'"
    })
    public void testUserAgent(String userAgent, String expectedPlatform, String expectedBrowser, String expectedDevice) {

        Response response = RestAssured
                .given()
                .header("User-Agent", userAgent)
                .get("https://playground.learnqa.ru/ajax/api/user_agent_check")
                .andReturn();


        String platformActual = response.jsonPath().getString("platform");
        String browserActual = response.jsonPath().getString("browser");
        String deviceActual = response.jsonPath().getString("device");


        List<String> errors = new ArrayList<>();
        if (!expectedPlatform.equals(platformActual)) {
            errors.add(String.format("User Agent: %s - Platform expected: [%s], but found: [%s].", userAgent, expectedPlatform, platformActual));
        }
        if (!expectedBrowser.equals(browserActual)) {
            errors.add(String.format("User Agent: %s - Browser expected: [%s], but found: [%s].", userAgent, expectedBrowser, browserActual));
        }
        if (!expectedDevice.equals(deviceActual)) {
            errors.add(String.format("User Agent: %s - Device expected: [%s], but found: [%s].", userAgent, expectedDevice, deviceActual));
        }


        if (!errors.isEmpty()) {
            errors.forEach(System.out::println);
            Assertions.fail("Some assertions failed. Check the output above.");
        }


        Assertions.assertEquals(expectedPlatform, platformActual, "Platform value is incorrect");
        Assertions.assertEquals(expectedBrowser, browserActual, "Browser value is incorrect");
        Assertions.assertEquals(expectedDevice, deviceActual, "Device value is incorrect");
    }
}