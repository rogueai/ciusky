package dev.rogueai.collection.util;

import dev.rogueai.collection.service.model.ECiuskyType;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Component
public class TemplateUtils {

    private static final List<String> PALETTE = new ArrayList<>();

    static {
        PALETTE.add("blue");
        PALETTE.add("gray");
        PALETTE.add("red");
        PALETTE.add("green");
        PALETTE.add("yellow");
        PALETTE.add("indigo");
        PALETTE.add("purple");
        PALETTE.add("pink");
    }

    public String getColor(String key) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(key.getBytes(StandardCharsets.UTF_8));
            // convert first 4 bytes of hash to an integer
            int h = ((hash[0] & 0xFF) << 24) | ((hash[1] & 0xFF) << 16) | ((hash[2] & 0xFF) << 8) | (hash[3] & 0xFF);
            h = Math.abs(h);
            return PALETTE.get(h % PALETTE.size());
        } catch (NoSuchAlgorithmException e) {
            return PALETTE.getFirst();
        }
    }

    public String getSaveAction(Long typeId) {
        ECiuskyType type = ECiuskyType.from(typeId != null ? typeId : 0);
        if (type == ECiuskyType.BOOK) {
            return "saveBook";
        }
        return "saveCiusky";
    }

}
