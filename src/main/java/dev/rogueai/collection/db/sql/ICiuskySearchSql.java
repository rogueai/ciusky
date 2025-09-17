package dev.rogueai.collection.db.sql;

import dev.rogueai.collection.db.dto.CiuskySearchEntity;
import dev.rogueai.collection.db.dto.TagEntity;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface ICiuskySearchSql {

    @SelectProvider(type = Queries.class, method = "searchCiusky")
    @Results(value = { //
            @Result(property = "id", column = "ID"), //
            @Result(property = "tags", javaType = List.class, column = "ID", //
                    many = @Many(select = "getTags", fetchType = FetchType.EAGER)), //
            @Result(property = "uuidImages", javaType = List.class, column = "ID", //
                    many = @Many(select = "getImages", fetchType = FetchType.EAGER)) //
    })
    List<CiuskySearchEntity> filter(String text, Long type);

    @Select("SELECT `KEY`, `VALUE` FROM CIUSKY_TAG WHERE ID = #{id}")
    List<TagEntity> getTags(Long id);

    @Select("SELECT `UUID` FROM CIUSKY_IMG WHERE ID = #{id}")
    List<String> getImages(Long id);
}
