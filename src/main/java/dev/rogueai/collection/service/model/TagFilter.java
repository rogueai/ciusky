package dev.rogueai.collection.service.model;

public class TagFilter {

    private String key;

    private String value;

    public TagFilter(String key) {
        this.key = key;
    }

    public TagFilter(String key, String value) {
        this.key = key;
        this.value = value;
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

    @Override
    public String toString() {
        return "TagFilter{" + "key='" + key + '\'' + ", value='" + value + '\'' + '}';
    }
}
