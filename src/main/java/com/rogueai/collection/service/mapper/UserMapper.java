package com.rogueai.collection.service.mapper;

import com.rogueai.collection.db.dto.PersonEntity;
import com.rogueai.collection.service.model.Person;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {

    Person toPerson(PersonEntity entity);

    PersonEntity fromPerson(Person model);

    List<Person> toPersonList(List<PersonEntity> entityList);

    List<PersonEntity> fromPersonList(List<Person> modelList);

}
