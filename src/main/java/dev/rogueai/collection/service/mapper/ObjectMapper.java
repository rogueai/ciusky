package dev.rogueai.collection.service.mapper;

import dev.rogueai.collection.db.dto.CiuskyEntity;
import dev.rogueai.collection.service.model.Ciusky;
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

}
