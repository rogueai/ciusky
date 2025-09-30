package dev.rogueai.collection.controller;

import dev.rogueai.collection.service.model.Option;

import java.util.ArrayList;
import java.util.List;

public class OptionView {

    private List<Option> options;

    public OptionView() {
        this(new ArrayList<>());
    }

    public OptionView(List<Option> options) {
        this.options = options;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }
}
