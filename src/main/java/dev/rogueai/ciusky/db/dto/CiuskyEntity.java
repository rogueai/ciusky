package dev.rogueai.ciusky.db.dto;

import java.math.BigDecimal;
import java.util.Date;

public class CiuskyEntity {

    public long id;

    public String title;

    public String description;

    public long type;

    public int quality;

    public String purchasePlace;

    public Date purchaseDate;

    public BigDecimal paidPrice;

    public BigDecimal marketPrice;

    public String notes;

}
