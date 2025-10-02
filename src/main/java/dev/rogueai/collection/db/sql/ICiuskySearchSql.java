package dev.rogueai.collection.db.sql;

import dev.rogueai.collection.db.dto.CiuskySearchEntity;

import java.util.List;

public interface ICiuskySearchSql {

    List<CiuskySearchEntity> search(String text, List<Long> types);

}
