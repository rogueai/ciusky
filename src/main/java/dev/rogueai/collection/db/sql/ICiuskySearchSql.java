package dev.rogueai.collection.db.sql;

import dev.rogueai.collection.db.dto.CiuskySearchEntity;
import dev.rogueai.collection.service.model.TagFilter;

import java.util.List;

public interface ICiuskySearchSql {

    List<CiuskySearchEntity> search(String text, List<Long> types, List<TagFilter> tags);

}
