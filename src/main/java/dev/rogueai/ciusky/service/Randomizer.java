package dev.rogueai.ciusky.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.time.DateUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.SplittableRandom;
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
    public static <T> T any(T... args) {
        return args[RandomUtils.secure().randomInt(0, args.length)];
    }

    /**
     * Genera un random uuid
     *
     * @return random uuid
     */
    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * Genera una data futura random
     *
     * @return random date
     */
    public static Date date() {
        return DateUtils.addDays(new Date(), RandomUtils.secure().randomInt(0, 365 * 10));
    }

    /**
     * Generate a random alphanumeric string.
     *
     * @param count number of characters
     * @return random string
     */
    public static String string(int count) {
        return RandomStringUtils.secure().nextAlphabetic(count);
    }

    /**
     * Generate a random BigDecimal.
     *
     * @return random BigDecimal
     */
    public static BigDecimal bigDecimal() {
        return BigDecimal.valueOf(RandomUtils.secure().randomFloat(0, 1000));
    }


    public static byte [] image(final int width, final int height) throws IOException {
        final BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        final long bytesPerPixel = 4L;
        final int[] pixelData = new SplittableRandom().ints(bytesPerPixel * width * height, 0, 256).toArray();
        result.getRaster().setPixels(0, 0, width, height, pixelData);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(result, "png", baos);
        return baos.toByteArray();
    }
}
