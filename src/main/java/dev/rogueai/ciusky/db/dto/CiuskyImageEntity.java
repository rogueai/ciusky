package dev.rogueai.ciusky.db.dto;

public class CiuskyImageEntity {

    public Long id;

    public String uuid;

    public String name;

    public CiuskyImageEntity() {}

    public CiuskyImageEntity(Long id, String uuid, String name) {
        this.id = id;
        this.uuid = uuid;
        this.name = name;
    }
}
