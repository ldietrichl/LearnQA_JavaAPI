package lecture;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class RedirectHandler {
    public static void followRedirects(String startUrl, int maxRedirects) {
        String currentUrl = "https://playground.learnqa.ru/api/long_redirect";
        int redirectsFollowed = 0;

        while (redirectsFollowed < 100) {
            Response response = RestAssured.given()
                    .redirects()
                    .follow(false)
                    .urlEncodingEnabled(false) // Disable URL encoding to prevent encoding of special characters
                    .when()
                    .get(currentUrl)
                    .then()
                    .extract().response();

            System.out.println("Requesting: " + currentUrl);
            System.out.println("Count Redirections: " + redirectsFollowed);

            int statusCode = response.getStatusCode();
            if (statusCode == 200) {
                System.out.println("Final URL reached: " + currentUrl);
                break;
            }

            if (statusCode == 301 || statusCode == 302) {
                String newUrl = response.getHeader("Location");
                if (newUrl != null) {
                    currentUrl = newUrl;
                    System.out.println("Redirecting to: " + currentUrl);
                    redirectsFollowed++;
                } else {
                    System.out.println("No 'Location' header found in redirect response.");
                    break;
                }
            } else {
                System.out.println("Unexpected status code: " + statusCode);
                break;
            }
        }
    }
}
