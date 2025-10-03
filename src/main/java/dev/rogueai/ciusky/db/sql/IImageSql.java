package dev.rogueai.ciusky.db.sql;

import dev.rogueai.ciusky.db.dto.CiuskyImageEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IImageSql {

    @Select("SELECT ID, UUID, NAME FROM CIUSKY_IMG WHERE ID = #{id}")
    List<CiuskyImageEntity> getImages(Long id);

    @Insert("INSERT INTO CIUSKY_IMG (ID, `UUID`, `NAME`) VALUES (#{id}, #{uuid}, #{name})")
    void insert(CiuskyImageEntity entity);

    @Delete("DELETE CIUSKY_IMG WHERE ID = #{ciuskyId} AND `UUID` = #{uuid}")
    void delete(Long ciuskyId, String uuid);

    @Select("SELECT * from CIUSKY_IMG WHERE UUID = #{uuid}")
    CiuskyImageEntity getByUuid(String uuid);

}
