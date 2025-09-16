package dev.rogueai.collection.db.sql;



import dev.rogueai.collection.db.dto.TagEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ITagSql {

    @Select("SELECT `KEY`, `VALUE` FROM CIUSKY_TAG WHERE ID = #{id}")
    List<TagEntity> getTags(Long id);

    @Insert("INSERT INTO CIUSKY_TAG (ID, `KEY`, `VALUE`) VALUES (#{id}, #{entity.key}, #{entity.value})")
    int insert(Long id, TagEntity entity);

}
