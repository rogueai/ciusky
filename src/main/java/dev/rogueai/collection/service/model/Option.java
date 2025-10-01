package dev.rogueai.collection.service.model;

public class Option {

    private Long id;

    private String descr;

    public Option() {
    }

    public Option(Long id, String descr) {
        this.id = id;
        this.descr = descr;
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
    
}
