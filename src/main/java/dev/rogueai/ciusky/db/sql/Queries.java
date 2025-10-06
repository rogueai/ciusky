package dev.rogueai.ciusky.db.sql;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class Queries {

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
            WHERE("`KEY` = LOWER(#{key})");
            if (StringUtils.isNotBlank(partialValue)) {
                WHERE("REGEXP_LIKE(`VALUE`, CONCAT('^', #{partialValue}), 'i')");
            }
        }}.toString();
    }
}
