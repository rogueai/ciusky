package dev.rogueai.collection.db.sql;

import dev.rogueai.collection.db.dto.BookEntity;
import dev.rogueai.collection.db.dto.CiuskyEntity;
import dev.rogueai.collection.db.dto.TagEntity;
import dev.rogueai.collection.service.model.Ciusky;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ICiuskySql {

    @Select("SELECT ID, TITLE, DESCRIPTION, TYPE_OPTION AS TYPE, QUALITY, PURCHASE_PLACE, " + //
            "PURCHASE_DATE, PAID_PRICE, MARKET_PRICE, NOTES FROM CIUSKY WHERE ID = #{id}")
    CiuskyEntity getCiusky(Long id);

    @Select("SELECT ID, AUTHOR, FORMAT, PUBLISHER, PUBLISH_DATE, ISBN_13, `LANGUAGE` FROM BOOK FROM WHERE ID = #{id}")
    BookEntity getBook(Long id);

    @Insert("INSERT INTO CIUSKY (TITLE, DESCRIPTION, TYPE_OPTION, QUALITY, PURCHASE_PLACE, PURCHASE_DATE, PAID_PRICE, MARKET_PRICE, NOTES) " //
            + "VALUES (#{title}, #{description}, #{type}, #{quality}, #{purchasePlace}, #{purchaseDate}, #{paidPrice}, #{marketPrice}, #{notes})")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    void insertCiusky(CiuskyEntity person);

    @Insert("INSERT INTO BOOK (ID, AUTHOR, FORMAT, PUBLISHER, PUBLISH_DATE, ISBN_13, `LANGUAGE`) " //
            + "VALUES (#{id}, #{author}, #{format}, #{publisher}, #{publishDate}, #{isbn13}, #{language})")
    void insertBook(BookEntity person);

    @Update("UPDATE CIUSKY " + //
            "SET TITLE = #{title}, DESCRIPTION = #{description}, TYPE_OPTION =  #{type}, QUALITY = #{quality}, " + //
            "PURCHASE_PLACE = #{purchasePlace}, PURCHASE_DATE = #{purchaseDate}, PAID_PRICE = #{paidPrice}, " + //
            "MARKET_PRICE = #{marketPrice}, NOTES = #{notes} " + //
            "WHERE ID = #{id}")
    void updateCiusky(CiuskyEntity entity);

    @Update("UPDATE BOOK " + //
            "SET AUTHOR = #{author}, FORMAT = #{format}, PUBLISHER = #{publisher}, PUBLISH_DATE = #{publishDate}, " + //
            "ISBN_13 = #{isbn13}, `LANGUAGE` = #{language} " + //
            "WHERE ID = #{id}")
    void updateBook(BookEntity bookEntity);

    @Delete("DELETE CIUSKY WHERE ID = #{id}")
    int delete(Long id);

}
