package dev.rogueai.collection.db.sql;

import dev.rogueai.collection.db.dto.BookEntity;
import dev.rogueai.collection.db.dto.CiuskyEntity;
import dev.rogueai.collection.db.dto.CiuskyTypeEntity;
import dev.rogueai.collection.db.dto.TagEntity;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface ICiuskySql {

    @Insert("INSERT INTO CIUSKY (TITLE, DESCRIPTION, TYPE_OPTION, QUALITY, PURCHASE_PLACE, PURCHASE_DATE, PAID_PRICE, MARKET_PRICE, NOTES) " //
            + "VALUES (#{title}, #{description}, #{type}, #{quality}, #{purchasePlace}, #{purchaseDate}, #{paidPrice}, #{marketPrice}, #{notes})")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    void insertCiusky(CiuskyEntity person);

    @Insert("INSERT INTO BOOK (ID, AUTHOR, FORMAT, PUBLISHER, PUBLISH_DATE, ISBN_13, `LANGUAGE`) " //
            + "VALUES (#{id}, #{author}, #{format}, #{publisher}, #{publishDate}, #{isbn13}, #{language})")
    void insertBook(BookEntity person);

    @Delete("DELETE CIUSKY WHERE ID = #{id}")
    int delete(Long id);

}
