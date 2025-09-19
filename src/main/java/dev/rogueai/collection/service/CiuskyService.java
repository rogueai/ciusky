package dev.rogueai.collection.service;

import dev.rogueai.collection.db.dto.BookEntity;
import dev.rogueai.collection.db.dto.CiuskyEntity;
import dev.rogueai.collection.db.dto.CiuskyImageEntity;
import dev.rogueai.collection.db.dto.TagEntity;
import dev.rogueai.collection.service.model.Book;
import dev.rogueai.collection.service.model.Ciusky;
import dev.rogueai.collection.service.model.ECiuskyType;
import dev.rogueai.collection.service.model.Tag;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CiuskyService extends AbstractService {

    public Ciusky get(Long id) throws CiuskyNotFoundException {
        CiuskyEntity entity = ciuskySql.getCiusky(id);
        if (entity == null) {
            throw new CiuskyNotFoundException("Could not find ciusky with id: " + id);
        }

        Ciusky model;
        if (ECiuskyType.from(entity.type) == ECiuskyType.BOOK) {
            BookEntity bookEntity = ciuskySql.getBook(id);
            model = mapper.toBook(entity, bookEntity);
        } else {
            model = mapper.toCiusky(entity);
        }

        List<TagEntity> tagsEntity = tagSql.getTags(id);
        model.setTags(mapper.toTagList(tagsEntity));

        List<CiuskyImageEntity> imagesEntity = imageSql.getImages(id);
        model.setImages(mapper.toCiuskyImageList(imagesEntity));

        return model;
    }

    public void delete(Long id) {
        /* TODO: When I delete a Ciùsky from the database, the images on the filesystem are kept, for now.
            I am worried about accidentally removing the entire HOME directory. */
        ciuskySql.delete(id);
    }

    @Transactional
    private void insert(Ciusky model) {
        CiuskyEntity entity = mapper.fromCiusky(model);
        ciuskySql.insertCiusky(entity);
        model.setId(entity.id);
        if (ECiuskyType.from(model.getType()) == ECiuskyType.BOOK && model instanceof Book) {
            BookEntity bookEntity = mapper.fromBook((Book) model);
            ciuskySql.insertBook(bookEntity);
        }
        for (Tag tag : model.getTags()) {
            TagEntity tagEntity = mapper.fromTag(tag);
            tagSql.insert(model.getId(), tagEntity);
        }
    }

    @Transactional
    private void update(Ciusky model) {
        CiuskyEntity entity = mapper.fromCiusky(model);
        ciuskySql.updateCiusky(entity);
        model.setId(entity.id);
        if (model.getType() == 2L && model instanceof Book) {
            BookEntity bookEntity = mapper.fromBook((Book) model);
            ciuskySql.updateBook(bookEntity);
        }

        tagSql.delete(model.getId());
        for (Tag tag : model.getTags()) {
            TagEntity tagEntity = mapper.fromTag(tag);
            tagSql.insert(model.getId(), tagEntity);
        }
    }

    @Transactional
    public void save(Ciusky model) {
        if (model.getId() == null) {
            insert(model);
        } else {
            update(model);
        }
    }

    @Transactional
    public void saveAll(List<Ciusky> models) {
        for (Ciusky model : models) {
            save(model);
        }
    }

}
