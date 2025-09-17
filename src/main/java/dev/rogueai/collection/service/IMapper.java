package dev.rogueai.collection.service;

import dev.rogueai.collection.db.dto.BookEntity;
import dev.rogueai.collection.db.dto.CiuskyEntity;
import dev.rogueai.collection.db.dto.CiuskyImageEntity;
import dev.rogueai.collection.db.dto.CiuskySearchEntity;
import dev.rogueai.collection.db.dto.TagEntity;
import dev.rogueai.collection.service.model.Book;
import dev.rogueai.collection.service.model.Ciusky;
import dev.rogueai.collection.service.model.CiuskyImage;
import dev.rogueai.collection.service.model.CiuskySearch;
import dev.rogueai.collection.service.model.Tag;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IMapper {

    CiuskySearch toCiuskySearch(CiuskySearchEntity entity);

    List<CiuskySearch> toCiuskySearchList(List<CiuskySearchEntity> entityList);

    Ciusky toCiusky(CiuskyEntity entity);

    CiuskyEntity fromCiusky(Ciusky model);

    BookEntity fromBook(Book model);

    @Mapping(target = "id", source = "ciuskyEntity.id")
    Book toBook(CiuskyEntity ciuskyEntity, BookEntity bookEntity);

    Tag toTag(TagEntity entity);

    List<Tag> toTagList(List<TagEntity> entity);

    TagEntity fromTag(Tag model);

    CiuskyImage toCiuskyImage(CiuskyImageEntity entity);

    List<CiuskyImage> toCiuskyImageList(List<CiuskyImageEntity> entity);

}
