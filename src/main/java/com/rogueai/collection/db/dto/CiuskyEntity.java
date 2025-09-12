package com.rogueai.collection.db.dto;

import java.math.BigDecimal;
import java.util.List;

public class CiuskyEntity {

    public long id;

    public String title;

    public String description;

    public long typeOption;

    public int quality;

    public BigDecimal paidPrice;

    public BigDecimal marketPrice;

    public List<TagEntity> tags;

}
