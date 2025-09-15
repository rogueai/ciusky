package dev.rogueai.collection.service.model;

public class Tag {

    public String key;

    public String value;

    public Tag() {

    }

    public Tag(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Tag{" + "key='" + key + '\'' + ", value='" + value + '\'' + '}';
    }
}
