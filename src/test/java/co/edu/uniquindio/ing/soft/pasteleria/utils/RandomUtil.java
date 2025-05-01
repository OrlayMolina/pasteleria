package co.edu.uniquindio.ing.soft.pasteleria.utils;

import java.util.List;
import java.util.Random;

public class RandomUtil {

    public static String generateRandomNumericId(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        sb.append(random.nextInt(9) + 1);
        for (int i = 1; i < length; i++) {
            sb.append(random.nextInt(10));
        }

        return sb.toString();
    }

    public static <T> T getRandomElement(List<T> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("La lista no puede ser nula o vacía");
        }
        Random random = new Random();
        int index = random.nextInt(list.size());
        return list.get(index);
    }
}
