package dev.rogueai.collection.db.sql;

import dev.rogueai.collection.service.model.Option;
import dev.rogueai.collection.service.model.OptionEdit;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface IOptionSql {

    @Select("SELECT ID, DESCR FROM TYPE_OPTION")
    List<Option> types();

    @Select("SELECT ID, DESCR, NATIV FROM TYPE_OPTION")
    @Results(value = { //
            @Result(property = "id", column = "ID"), //
            @Result(property = "referenceCount", column = "ID", //
                    one = @One(select = "getReferenceCount", fetchType = FetchType.EAGER)), //
    })
    List<OptionEdit> getTypesForEdit();

    @Update("INSERT INTO TYPE_OPTION (DESCR) VALUES ( #{descr} )")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    void insertType(Option option);

    @Update("UPDATE TYPE_OPTION SET DESCR = #{descr} WHERE ID = #{id}")
    void updateType(Option option);

    @Select("SELECT COUNT(*) FROM CIUSKY WHERE TYPE_OPTION = #{id}")
    int getReferenceCount(Long id);
}
