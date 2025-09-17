package dev.rogueai.collection.service.mapper;

import dev.rogueai.collection.db.dto.*;
import dev.rogueai.collection.service.model.*;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ObjectMapper {

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

    CiuskyImage toCiuskyImage (CiuskyImageEntity entity);

    List<CiuskyImage> toCiuskyImageList(List<CiuskyImageEntity> entity);


}
