package dev.rogueai.collection.db.sql;

import org.apache.ibatis.jdbc.SQL;

public class Queries {

    public String searchCiusky() {
        return new SQL() {{
            SELECT("c.ID, c.TITLE, c.DESCRIPTION, t.ID AS TYPE_ID, t.DESCR AS TYPE_DESCRIPTION, c.QUALITY, c.PURCHASE_PLACE, c.PURCHASE_DATE, c.PAID_PRICE, c.MARKET_PRICE, c.NOTES");
            FROM("CIUSKY c");
            INNER_JOIN("TYPE_OPTION t ON t.ID = c.TYPE_OPTION");
            //ORDER_BY("C.ID");
        }}.toString();
    }
}
