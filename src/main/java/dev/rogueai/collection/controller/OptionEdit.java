package dev.rogueai.collection.controller;

public class OptionEdit {

    private Long id;

    private String descr;

    private boolean deleted;

    public OptionEdit() {

    }

    public OptionEdit(Long id, String descr) {
        this.id = id;
        this.descr = descr;
        this.deleted = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
