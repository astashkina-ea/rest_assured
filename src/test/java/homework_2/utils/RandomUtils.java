package homework_2.utils;

import com.github.javafaker.Faker;

import java.security.SecureRandom;
import java.util.Locale;

public class RandomUtils {

    private static Faker fakerEn = new Faker(new Locale("en"));

    public static String getRandomEmail() {
        return fakerEn.internet().emailAddress();
    }

    public static String getRandomName() {
        return fakerEn.name().name();
    }

    public static String getRandomJob() {
        return fakerEn.job().title();
    }

    public static String getRandomPassword() {
        return getRandomString(10);
    }

    public static String getRandomString(int len) {
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom rnd = new SecureRandom();

        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }

    public static int getRandomBetweenNumber(int min, int max) {
        return fakerEn.random().nextInt(min, max);
    }
}