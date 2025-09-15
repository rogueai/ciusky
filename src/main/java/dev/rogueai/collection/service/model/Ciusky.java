package dev.rogueai.collection.service.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Ciusky {

    public Long id;

    public String title;

    public String description;

    public CiuskyType type;

    public int quality;

    public String purchasePlace;

    public Date purchaseDate;

    public BigDecimal paidPrice;

    public BigDecimal marketPrice;

    public String notes;

    public List<Tag> tags;

}
