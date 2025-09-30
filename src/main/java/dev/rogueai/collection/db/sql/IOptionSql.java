package dev.rogueai.collection.db.sql;

import dev.rogueai.collection.service.model.Option;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface IOptionSql {

    @Select("SELECT ID, DESCR FROM TYPE_OPTION")
    List<Option> types();

    @Update("UPDATE TYPE_OPTION SET DESCR = #{descr} WHERE ID = #{id}")
    void updateType(Long id, String descr);
}
