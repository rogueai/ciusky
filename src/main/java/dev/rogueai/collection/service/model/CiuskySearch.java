package dev.rogueai.collection.service.model;

import dev.rogueai.collection.db.dto.TagEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class CiuskySearch {

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

    public List<Tag> tags;

    public List<String> uuidImages;
}
