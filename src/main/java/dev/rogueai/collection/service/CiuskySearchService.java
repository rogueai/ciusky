package dev.rogueai.collection.service;

import dev.rogueai.collection.db.dto.CiuskySearchEntity;
import dev.rogueai.collection.db.sql.ICiuskySearchSql;
import dev.rogueai.collection.service.mapper.ObjectMapper;
import dev.rogueai.collection.service.model.CiuskySearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CiuskySearchService {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ICiuskySearchSql ciuskySearchSql;

    public List<CiuskySearch> findAll() {
        List<CiuskySearchEntity> entities = ciuskySearchSql.filter();
        return objectMapper.toCiuskySearchList(entities);
    }

}
