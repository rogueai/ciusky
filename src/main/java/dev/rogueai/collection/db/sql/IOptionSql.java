package dev.rogueai.collection.db.sql;

import dev.rogueai.collection.service.model.Option;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IOptionSql {

    @Select("SELECT ID, DESCR FROM TYPE_OPTION")
    List<Option> allOptions();

}
