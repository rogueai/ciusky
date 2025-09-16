package dev.rogueai.collection.service;

import dev.rogueai.collection.db.dto.BookEntity;
import dev.rogueai.collection.db.dto.CiuskyEntity;
import dev.rogueai.collection.db.dto.CiuskyImageEntity;
import dev.rogueai.collection.db.dto.TagEntity;
import dev.rogueai.collection.db.sql.ICiuskySql;
import dev.rogueai.collection.db.sql.IImageSql;
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
    IImageSql imageSql;

    @Autowired
    ITagSql tagSql;

    public Ciusky get(Long id) {

        CiuskyEntity entity = ciuskySql.getCiusky(id);
        Ciusky model = objectMapper.toCiusky(entity);

        List<TagEntity> tagsEntity = tagSql.getTags(id);
        model.setTags(objectMapper.toTagList(tagsEntity));

        List<CiuskyImageEntity> imagesEntity = imageSql.getImages(id);
        model.setImages(objectMapper.toCiuskyImageList(imagesEntity));

        return model;
    }

    @Transactional
    public void save(Ciusky model) {
        if (model.getId() == null)  {
            insert(model);
        }
        else {
            update(model);
        }
    }

    private void insert(Ciusky model) {
        CiuskyEntity entity = objectMapper.fromCiusky(model);
        ciuskySql.insertCiusky(entity);
        model.setId(entity.id);
        if (model.getType() == 2L && model instanceof Book) {
            BookEntity bookEntity = objectMapper.fromBook((Book) model);
            ciuskySql.insertBook(bookEntity);
        }
    }

    private void update(Ciusky model) {
        CiuskyEntity entity = objectMapper.fromCiusky(model);
        ciuskySql.updateCiusky(entity);
        model.setId(entity.id);
        if (model.getType() == 2L && model instanceof Book) {
            BookEntity bookEntity = objectMapper.fromBook((Book) model);
            ciuskySql.updateBook(bookEntity);
        }
    }

    @Transactional
    public void saveAll(List<Ciusky> models) {
        for (Ciusky model : models) {
            save(model);
            for (Tag tag : model.getTags()) {
                tagSql.insert(model.getId(), objectMapper.fromTag(tag));
            }
        }
    }

}
