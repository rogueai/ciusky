package dev.rogueai.collection.service.mapper;

import dev.rogueai.collection.db.dto.BookEntity;
import dev.rogueai.collection.db.dto.CiuskyEntity;
import dev.rogueai.collection.db.dto.CiuskySearchEntity;
import dev.rogueai.collection.db.dto.TagEntity;
import dev.rogueai.collection.service.model.Book;
import dev.rogueai.collection.service.model.Ciusky;
import dev.rogueai.collection.service.model.CiuskySearch;
import dev.rogueai.collection.service.model.Tag;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ObjectMapper {

    CiuskySearch toCiuskySearch(CiuskySearchEntity entity);

    List<CiuskySearch> toCiuskySearchList(List<CiuskySearchEntity> entityList);

    Ciusky toCiusky(CiuskyEntity entity);

    CiuskyEntity fromCiusky(Ciusky model);

    BookEntity fromBook(Book model);

    List<Ciusky> toCiuskyList(List<CiuskyEntity> entityList);

    List<CiuskyEntity> fromCiuskyList(List<Ciusky> modelList);

    Tag toTag(TagEntity entity);

    TagEntity fromTag(Tag model);

}
