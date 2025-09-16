package dev.rogueai.collection.service;

import dev.rogueai.collection.db.dto.BookEntity;
import dev.rogueai.collection.db.dto.CiuskyEntity;
import dev.rogueai.collection.db.sql.ICiuskySql;
import dev.rogueai.collection.db.sql.ITagSql;
import dev.rogueai.collection.service.mapper.ObjectMapper;
import dev.rogueai.collection.service.model.Book;
import dev.rogueai.collection.service.model.Ciusky;
import dev.rogueai.collection.service.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CiuskyService {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ICiuskySql ciuskySql;

    @Autowired
    ITagSql tagSql;

    public Ciusky get(Long id) {
        return objectMapper.toCiusky(ciuskySql.getCiusky(id));
    }

    @Transactional
    public void save(Ciusky model) {
        CiuskyEntity entity = objectMapper.fromCiusky(model);
        ciuskySql.insertCiusky(entity);
        model.id = entity.id;

        if (model.type == 2L && model instanceof Book) {
            BookEntity bookEntity = objectMapper.fromBook((Book) model);
            ciuskySql.insertBook(bookEntity);
        }
    }

    @Transactional
    public void saveAll(List<Ciusky> models) {
        for (Ciusky model : models) {
            save(model);
            for (Tag tag : model.tags) {
                tagSql.insert(model.id, objectMapper.fromTag(tag));
            }
        }
    }

}
