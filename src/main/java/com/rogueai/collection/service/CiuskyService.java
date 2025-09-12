package com.rogueai.collection.service;

import com.rogueai.collection.db.dto.CiuskyEntity;
import com.rogueai.collection.db.sql.ICiuskySql;
import com.rogueai.collection.service.mapper.ObjectMapper;
import com.rogueai.collection.service.model.Ciusky;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CiuskyService {

    private final ObjectMapper objectMapper;

    private final ICiuskySql ciuskySql;

    public CiuskyService(ObjectMapper objectMapper, ICiuskySql ciuskySql) {
        this.objectMapper = objectMapper;
        this.ciuskySql = ciuskySql;
    }

    public List<Ciusky> getAll() {
        List<CiuskyEntity> list = ciuskySql.list();
        return objectMapper.toCiuskyList(list);
    }

    public Ciusky get(Long id) {
        CiuskyEntity entity = ciuskySql.getPerson(id);
        return objectMapper.toCiusky(entity);
    }

}
