package dev.rogueai.collection;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class Randomizer {

    /**
     * Data una lista di valori ne ritorna uno random.
     *
     * @param <T>  Tipo di valore: Integer, String, Long, etc
     * @param args la lista dei valori
     * @return valore random
     */
    @SafeVarargs
    public static <T> T random(T... args) {
        return args[RandomUtils.secure().randomInt(0, args.length)];
    }

    /**
     * Genera un random uuid
     *
     * @return random uuid
     */
    public static String randomUuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * Genera una data futura random
     *
     * @return random date
     */
    public static Date randomDate() {
        return DateUtils.addDays(new Date(), RandomUtils.secure().randomInt(0, 365 * 10));
    }

    /**
     * Generate a random alphanumeric string.
     *
     * @param count number of characters
     * @return random string
     */
    public static String randomString(int count) {
        return RandomStringUtils.secure().nextAlphabetic(count);
    }

    /**
     * Generate a random BigDecimal.
     *
     * @return random BigDecimal
     */
    public static BigDecimal random() {
        return BigDecimal.valueOf(RandomUtils.secure().randomFloat(0, 1000));
    }
}
