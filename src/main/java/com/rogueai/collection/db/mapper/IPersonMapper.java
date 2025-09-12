package com.rogueai.collection.db.mapper;


import com.rogueai.collection.db.dto.PersonDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface IPersonMapper {

    @Select("SELECT * FROM PERSON")
    List<PersonDto> list();

    @Select("SELECT * FROM PERSON WHERE ID = #{id}")
    PersonDto getPerson(@Param("id") Long id);

    @Select("SELECT * FROM PERSON WHERE SUPERVISOR = #{supervisor}")
    List<PersonDto> listPersons(@Param("supervisor") boolean supervisor);

    @Insert("INSERT INTO PERSON (NAME, CONTACT_NUMBER, EMAIL, SUPERVISOR) VALUES (#{name}, #{contactNumber}, #{email}, #{supervisor})")
    int insert(PersonDto person);

    @Update("UPDATE PERSON SET NAME = #{name}, CONTACT_NUMBER = #{contactNumber}, EMAIL = #{email}, SUPERVISOR = #{supervisor} WHERE ID = #{id}")
    int update(PersonDto person);

    @Delete("DELETE PERSON WHERE ID = #{id}")
    int delete(Long id);

}
