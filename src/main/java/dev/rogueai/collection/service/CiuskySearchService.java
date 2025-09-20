package dev.rogueai.collection.service;

import dev.rogueai.collection.db.dto.CiuskySearchEntity;
import dev.rogueai.collection.service.model.CiuskyFilter;
import dev.rogueai.collection.service.model.CiuskySearch;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CiuskySearchService extends AbstractService {

    public List<CiuskySearch> findAll(CiuskyFilter filter) {
        String text = StringUtils.trim(filter.getText());
        List<Long> types = filter.getTypes();

        List<CiuskySearchEntity> entities = searchSql.filter(text, types);
        return mapper.toCiuskySearchList(entities);
    }

}
