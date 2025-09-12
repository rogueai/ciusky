package com.rogueai.collection.db.sql;


import com.rogueai.collection.db.dto.PersonEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface IPersonSql {

    @Select("SELECT * FROM PERSON")
    List<PersonEntity> list();

    @Select("SELECT * FROM PERSON WHERE ID = #{id}")
    PersonEntity getPerson(@Param("id") Long id);

    @Select("SELECT * FROM PERSON WHERE SUPERVISOR = #{supervisor}")
    List<PersonEntity> listPersons(@Param("supervisor") boolean supervisor);

    @Insert("INSERT INTO PERSON (NAME, CONTACT_NUMBER, EMAIL, SUPERVISOR) " +
            "VALUES (#{name}, #{contactNumber}, #{email}, #{supervisor})")
    int insert(PersonEntity person);

    @Update("UPDATE PERSON SET " +
            "NAME = #{name}, CONTACT_NUMBER = #{contactNumber}, " +
            "EMAIL = #{email}, SUPERVISOR = #{supervisor} WHERE ID = #{id}")
    int update(PersonEntity person);

    @Delete("DELETE PERSON WHERE ID = #{id}")
    int delete(Long id);

}
