package lib;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DataGenerator {
    public static String getRandomEmail(){
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
        return "learnqa" + timestamp + "@example.com";

    }

    public static String getInvalidEmail(){
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
        return "learnqa" + timestamp + "example.com";

    }

    public static Map<String, String> getRegistrationData() {
        Map<String,String> data = new HashMap<>();
        data.put("email",DataGenerator.getRandomEmail());
        data.put("password", "123");
        data.put("username", "learnqa");
        data.put("firstName", "learnqa");
        data.put("lastName", "learnqa");

        return data;
    }

    public static Map<String, String> getRegistrationDataStranger() {
        Map<String,String> data = new HashMap<>();
        data.put("email",DataGenerator.getRandomEmail());
        data.put("password", "123");
        data.put("username", "learnq");
        data.put("firstName", "learnq");
        data.put("lastName", "learnq");

        return data;
    }


    public static Map<String, String> getRegistrationData(Map<String, String> nonDefaultValues) {

        Map<String,String> defaultValues = DataGenerator.getRegistrationData();
        Map<String,String> userData = new HashMap<>();
        String[]  keys ={"email","password","username","firstName","lastName"};
        for (String key : keys){
            if (nonDefaultValues.containsKey(key)){
                userData.put(key,nonDefaultValues.get(key));
            } else {
                userData.put(key,defaultValues.get(key));
            }
        }
        return userData;

    }

    public static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            char randomChar = (char) (random.nextInt(26) + 'a'); // Генерация случайного символа в нижнем регистре (a-z)
            sb.append(randomChar);
        }

        return sb.toString();
    }

}
