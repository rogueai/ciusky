package dev.rogueai.ciusky.db.sql;

import dev.rogueai.ciusky.db.dto.CiuskySearchEntity;
import dev.rogueai.ciusky.service.model.TagFilter;

import java.util.List;

public interface ICiuskySearchSql {

    List<CiuskySearchEntity> search(String text, List<Long> types, List<TagFilter> tags);

}
