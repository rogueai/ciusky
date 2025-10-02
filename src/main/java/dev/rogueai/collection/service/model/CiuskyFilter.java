package dev.rogueai.collection.service.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CiuskyFilter {

    private String text;

    private List<Long> types;

    private String tags;

    public CiuskyFilter() {
    }

    public CiuskyFilter(String text, String tags, List<Option> types) {
        this.text = text;
        this.tags = tags;
        if (types != null) {
            this.types = types.stream().map(Option::getId).collect(Collectors.toList());
        } else {
            this.types = new ArrayList<>();
        }

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public List<Long> getTypes() {
        return types;
    }

    public void setTypes(List<Long> types) {
        this.types = types;
    }

    @Override
    public String toString() {
        return "CiuskyFilter{" + "text='" + text + '\'' + ", types=" + types + ", tags='" + tags + '\'' + '}';
    }
}
