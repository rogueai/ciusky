package com.rogueai.collection.db.sql;

import com.rogueai.collection.db.dto.CiuskyEntity;
import com.rogueai.collection.db.dto.TagEntity;
import com.rogueai.collection.db.dto.CiuskyTypeEntity;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface ICiuskySql {

    @Select("SELECT * FROM CIUSKY")
    @Results(value = { //
            @Result(property = "id", column = "ID"), //
            @Result(property = "type", column = "TYPE_OPTION", //
                    one = @One(select = "getType", fetchType = FetchType.DEFAULT)), //
            @Result(property = "tags", javaType = List.class, column = "ID", //
                    many = @Many(select = "getTags", fetchType = FetchType.DEFAULT)) })
    List<CiuskyEntity> list();

    @Select("SELECT * FROM CIUSKY WHERE ID = #{id}")
    @Results(value = { //
            @Result(property = "id", column = "id"), //
            @Result(property = "tags", javaType = List.class, column = "id", //
                    many = @Many(select = "getTags", fetchType = FetchType.DEFAULT)) })
    CiuskyEntity getPerson(Long id);

    @Insert("INSERT INTO CIUSKY (TITLE, DESCRIPTION, TYPE_OPTION, QUALITY, PAID_PRICE, MARKET_PRICE) " + "VALUES (#{title}, #{description}, #{typeOption}, #{quality}, #{paidPrice}, #{marketPrice})")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    int insert(CiuskyEntity person);

    @Delete("DELETE CIUSKY WHERE ID = #{id}")
    int delete(Long id);

    @Select("SELECT ID, DESCR FROM TYPE_OPTION WHERE ID = #{id}")
    List<CiuskyTypeEntity> getType(Long id);

    @Select("SELECT `KEY`, `VALUE` FROM CIUSKY_TAG WHERE ID = #{id}")
    List<TagEntity> getTags(Long id);



}
