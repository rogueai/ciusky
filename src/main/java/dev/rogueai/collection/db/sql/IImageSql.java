package dev.rogueai.collection.db.sql;

import dev.rogueai.collection.db.dto.CiuskyImageEntity;
import dev.rogueai.collection.db.dto.TagEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.UUID;

public interface IImageSql {

    @Insert("INSERT INTO CIUSKY_IMG (ID, `UUID`, `NAME`) VALUES (#{id}, #{uuid}, #{name})")
    int insert(CiuskyImageEntity entity);

    @Select("SELECT * from CIUSKY_IMG WHERE UUID = #{uuid}")
    CiuskyImageEntity getByUuid(String uuid);

}
