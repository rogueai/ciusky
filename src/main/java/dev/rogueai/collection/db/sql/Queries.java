package dev.rogueai.collection.db.sql;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.jdbc.SQL;

public class Queries {

    public static String searchCiusky(@Param("text") final String text, @Param("type") final Long type) {
        return new SQL() {{
            SELECT("c.ID, c.TITLE, c.DESCRIPTION, t.ID AS TYPE_ID, t.DESCR AS TYPE_DESCRIPTION, c.QUALITY, c.PURCHASE_PLACE, c.PURCHASE_DATE, c.PAID_PRICE, c.MARKET_PRICE, c.NOTES");
            FROM("CIUSKY c");
            INNER_JOIN("TYPE_OPTION t ON t.ID = c.TYPE_OPTION");

            if (StringUtils.isNotBlank(text)) {
               WHERE("REGEXP_LIKE(c.TITLE, #{text}, 'i')");
               OR().WHERE("REGEXP_LIKE(c.DESCRIPTION, #{text}, 'i')");
            }

            if (type != null) {
                WHERE("t.ID = #{type}");
            }

            //ORDER_BY("C.ID");
        }}.toString();
    }
}
