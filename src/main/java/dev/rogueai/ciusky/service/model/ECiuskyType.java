package dev.rogueai.ciusky.service.model;

import java.util.HashMap;
import java.util.Map;

public enum ECiuskyType {

    UNKNOWN(0L), //
    CIUSKY(1L),  //
    BOOK(2L);

    private static final Map<Long, ECiuskyType> BY_ID = new HashMap<>();

    static {
        for (ECiuskyType e : values()) {
            BY_ID.put(e.getValue(), e);
        }
    }

    private final Long id;

    ECiuskyType(Long id) {
        this.id = id;
    }

    public static ECiuskyType from(Long id) {
        return BY_ID.getOrDefault(id, UNKNOWN);
    }

    public long getValue() {
        return id;
    }
}
