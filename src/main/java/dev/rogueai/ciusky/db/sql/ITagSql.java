package dev.rogueai.ciusky.db.sql;

import dev.rogueai.ciusky.db.dto.TagEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

public interface ITagSql {

    @Select("SELECT `KEY`, `VALUE` FROM CIUSKY_TAG WHERE ID = #{id}")
    List<TagEntity> getTags(Long id);

    @Insert("INSERT INTO CIUSKY_TAG (ID, `KEY`, `VALUE`) VALUES (#{id}, #{entity.key}, #{entity.value})")
    void insert(Long id, TagEntity entity);

    @Delete("DELETE FROM CIUSKY_TAG WHERE ID = #{id}")
    void delete(Long id);

    @SelectProvider(type = Queries.class, method = "searchTagKeys")
    List<String> searchTagKeys(String partialKey);

    @SelectProvider(type = Queries.class, method = "searchTagValues")
    List<String> searchTagValues(String key, String partialValue);
}
