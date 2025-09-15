package dev.rogueai.collection.service;

import dev.rogueai.collection.db.dto.CiuskyEntity;
import dev.rogueai.collection.db.sql.ICiuskySql;
import dev.rogueai.collection.service.mapper.ObjectMapper;
import dev.rogueai.collection.service.model.Ciusky;
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


    public Ciusky save(Ciusky model) {
        CiuskyEntity entity = objectMapper.fromCiusky(model);
        ciuskySql.insert(entity);
        return objectMapper.toCiusky(entity);
    }

    @Transactional
    public void saveAll(List<Ciusky> models) {
        for (Ciusky model : models) {
            CiuskyEntity entity = objectMapper.fromCiusky(model);
            ciuskySql.insert(entity);
        }
    }

}
