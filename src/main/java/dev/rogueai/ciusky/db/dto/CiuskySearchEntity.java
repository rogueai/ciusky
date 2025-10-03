package dev.rogueai.ciusky.db.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class CiuskySearchEntity {

    public long id;

    public String title;

    public String description;

    public long typeId;

    public String typeDescription;

    public int quality;

    public String purchasePlace;

    public Date purchaseDate;

    public BigDecimal paidPrice;

    public BigDecimal marketPrice;

    public String notes;

    public List<TagEntity> tags;

    public List<String> uuidImages;

}
