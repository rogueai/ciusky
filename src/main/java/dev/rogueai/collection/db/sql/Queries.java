package dev.rogueai.collection.db.sql;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.Iterator;
import java.util.List;

public class Queries {

    public String searchCiusky(@Param("text") final String text, @Param("types") final List<Long> types) {
        return new SQL() {{
            SELECT("c.ID, c.TITLE, c.DESCRIPTION, t.ID AS TYPE_ID, t.DESCR AS TYPE_DESCRIPTION, c.QUALITY, c.PURCHASE_PLACE, c.PURCHASE_DATE, c.PAID_PRICE, c.MARKET_PRICE, c.NOTES");
            FROM("CIUSKY c");
            INNER_JOIN("TYPE_OPTION t ON t.ID = c.TYPE_OPTION");
            if (StringUtils.isNotBlank(text)) {
                WHERE("(REGEXP_LIKE(c.TITLE, #{text}, 'i') OR REGEXP_LIKE(c.DESCRIPTION, #{text}, 'i'))");
            }
            if (types != null && !types.isEmpty()) {
                String condition = " ( ";
                for (Iterator<Long> iter = types.iterator(); iter.hasNext(); ) {
                    condition += " ( t.ID = " + iter.next() + " )";
                    if (iter.hasNext()) {
                        condition += " OR ";
                    }
                }
                condition += " ) ";
                WHERE(condition);
            }
        }}.toString();
    }

    public String searchTagKeys(@Param("partialKey") final String partialKey) {
        return new SQL() {{
            SELECT_DISTINCT("`KEY`");
            FROM("CIUSKY_TAG");
            if (StringUtils.isNotBlank(partialKey)) {
                WHERE("REGEXP_LIKE(`KEY`, CONCAT('^', #{partialKey}), 'i')");
            }
        }}.toString();
    }

    public String searchTagValues(@Param("key") final String key, @Param("partialValue") final String partialValue) {
        return new SQL() {{
            SELECT_DISTINCT("`VALUE`");
            FROM("CIUSKY_TAG");
            WHERE("`KEY` = #{key}");
            if (StringUtils.isNotBlank(partialValue)) {
                WHERE("REGEXP_LIKE(`VALUE`, CONCAT('^', #{partialValue}), 'i')");
            }
        }}.toString();
    }
}
