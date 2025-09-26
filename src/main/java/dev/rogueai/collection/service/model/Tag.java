package dev.rogueai.collection.service.model;

import java.util.Objects;

public class Tag {

    private String key;

    private String value;

    public Tag() {

    }

    public Tag(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Tag tag))
            return false;
        return Objects.equals(key, tag.key) && Objects.equals(value, tag.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
