package dev.rogueai.ciusky.service.model;

public class OptionEdit {

    private Long id;

    private String descr;

    private int referenceCount;

    private boolean nativ;

    private boolean deleted;

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

    public int getReferenceCount() {
        return referenceCount;
    }

    public void setReferenceCount(int referenceCount) {
        this.referenceCount = referenceCount;
    }

    public boolean isNativ() {
        return nativ;
    }

    public void setNativ(boolean nativ) {
        this.nativ = nativ;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
