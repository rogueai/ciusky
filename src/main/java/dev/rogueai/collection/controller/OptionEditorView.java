package dev.rogueai.collection.controller;

import dev.rogueai.collection.service.model.OptionEdit;

import java.util.List;

public class OptionEditorView {

    private List<OptionEdit> options;

    private boolean dirty;

    public OptionEditorView() {

    }

    public OptionEditorView(List<OptionEdit> options) {
        this.options = options;
    }

    public List<OptionEdit> getOptions() {
        return options;
    }

    public void setOptions(List<OptionEdit> options) {
        this.options = options;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

}
