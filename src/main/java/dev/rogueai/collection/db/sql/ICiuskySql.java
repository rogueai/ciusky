package dev.rogueai.collection.db.sql;

import dev.rogueai.collection.db.dto.BookEntity;
import dev.rogueai.collection.db.dto.CiuskyEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

public interface ICiuskySql {

    @Select("c.ID, c.TITLE, c.DESCRIPTION, t.ID AS TYPE_ID, t.DESCR AS TYPE_DESCRIPTION, c.QUALITY, c.PURCHASE_PLACE, c.PURCHASE_DATE, c.PAID_PRICE, c.MARKET_PRICE, c.NOTES FROM CIUSKY c WHERE c.ID = #{id}"

    )
    CiuskyEntity getCiusky(Long id);

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
