package dev.rogueai.collection.db.sql;

import dev.rogueai.collection.db.dto.CiuskyImageEntity;
import dev.rogueai.collection.db.dto.TagEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.UUID;

public interface IImageSql {

    @Select("SELECT ID, UUID, NAME FROM CIUSKY_IMG WHERE ID = #{id}")
    List<CiuskyImageEntity> getImages(Long id);

    @Insert("INSERT INTO CIUSKY_IMG (ID, `UUID`, `NAME`) VALUES (#{id}, #{uuid}, #{name})")
    void insert(CiuskyImageEntity entity);

    @Select("SELECT * from CIUSKY_IMG WHERE UUID = #{uuid}")
    CiuskyImageEntity getByUuid(String uuid);

}
