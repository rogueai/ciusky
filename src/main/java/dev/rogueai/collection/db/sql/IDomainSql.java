package dev.rogueai.collection.db.sql;

import dev.rogueai.collection.service.model.Domain;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IDomainSql {

    @Select("SELECT ID, DESCR FROM TYPE_OPTION")
    List<Domain> allTypes();

}
