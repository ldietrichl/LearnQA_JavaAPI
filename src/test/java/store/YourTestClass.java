package store;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class YourTestClass {

    @Test
    public void yourTestMethod() throws IOException, CsvValidationException {
        // Укажите путь к вашему CSV-файлу
        String csvFile = "src/test/resources/Passwords.csv";
        CSVReader csvReader = new CSVReader(new FileReader(csvFile));
        String[] data;


        // Чтение данных из CSV и отправка запроса для каждой строки
        while ((data = csvReader.readNext()) != null ) {

            //Map<String, String> data2 = new HashMap<>();
            //data2.put("login","super_admin");
            //data2.put("password", data[0]);


            // Параметры запроса из CSV
            String param1 = data[0]; // Первый столбец
            String param2 = "super_admin";
            System.out.println("user: " +param2);
            System.out.println("pass: "+param1);


            // Отправка запроса с использованием параметров из CSV
            Response response = RestAssured.given()
                    .param("login", param2)
                    .param("password", param1)
                   // .body(data)
                    .post("https://playground.learnqa.ru/ajax/api/get_secret_password_homework");

            Map<String, String> responceCookies = response.getCookies();
            System.out.println(responceCookies);

            Map<String, String> cookies = new HashMap<>();
            if(responceCookies != null){
                cookies.putAll(responceCookies);
            }


            Response responseForCheck = RestAssured
                    .given()
                   // .param("login", param2)
                   // .param("password", param1)
                    .cookies(cookies)
                    .when()
                    .post("https://playground.learnqa.ru/api/check_auth_cookie")
                    .andReturn();

            String respMessage  = responseForCheck.print().toString();
            //System.out.println(respMessage);
            System.out.println("-----------------------------------------------------------");


            if ("You are authorized".equals(respMessage)){
                System.out.println("Password found");
                break;
            }
        }
        csvReader.close();
    }
}
