package dev.rogueai.collection.service.model;

public class CiuskyFilter {

    private String text;

    private Long type;

    public CiuskyFilter() {
    }

    public CiuskyFilter(String text, Long type) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "CiuskyFilter{" + "text='" + text + '\'' + ", type=" + type + '}';
    }
}
