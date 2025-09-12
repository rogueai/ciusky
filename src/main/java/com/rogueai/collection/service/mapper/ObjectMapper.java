package com.rogueai.collection.service.mapper;

import com.rogueai.collection.db.dto.CiuskyEntity;
import com.rogueai.collection.db.dto.TagEntity;
import com.rogueai.collection.service.model.Ciusky;
import com.rogueai.collection.service.model.Tag;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ObjectMapper {

    Ciusky toCiusky(CiuskyEntity entity);

    CiuskyEntity fromCiusky(Ciusky model);

    List<Ciusky> toCiuskyList(List<CiuskyEntity> entityList);

    List<CiuskyEntity> fromCiuskyList(List<Ciusky> modelList);

    Tag toTag(TagEntity entity);

    TagEntity fromTag(Tag model);

    List<Tag> toTagList(List<TagEntity> entityList);

    List<TagEntity> fromTagList(List<Tag> modelList);

}
