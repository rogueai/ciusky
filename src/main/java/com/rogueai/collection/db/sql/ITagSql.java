package com.rogueai.collection.db.sql;



import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ITagSql {
    /*
    @Select("SELECT * FROM TAG_OPTION")
    List<TagEntity> all();

    @Select("SELECT * FROM TAG_OPTION WHERE REGEXP_LIKE(TAG, #{tag}, 'i')")
    List<TagEntity> searchTag(@Param("tag") String tag);

    @Select("SELECT * FROM TAG_OPTION WHERE ID = #{id}")
    TagEntity get(@Param("id") Long id);

    @Insert("INSERT INTO TAG_OPTION (TAG) VALUES (#{tag})")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    int insert(TagEntity entity);

    @Update("UPDATE TAG_OPTION SET TAG = #{tag}, WHERE ID = #{id}")
    int update(TagEntity entity);

    @Delete("DELETE TAG_OPTION WHERE ID = #{id}")
    int delete(Long id);
    */

}
