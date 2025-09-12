package com.rogueai.collection.service.model;

import com.rogueai.collection.db.dto.TagEntity;

import java.math.BigDecimal;
import java.util.List;

public class Ciusky {

    public long id;

    public String title;

    public String description;

    public long typeOption;

    public int quality;

    public BigDecimal paidPrice;

    public BigDecimal marketPrice;

    public List<Tag> tags;

    @Override
    public String toString() {
        return "Ciusky{" + "id=" + id + ", title='" + title + '\'' + ", description='" + description + '\'' + ", typeOption=" + typeOption + ", quality=" + quality + ", paidPrice=" + paidPrice + ", marketPrice=" + marketPrice + ", tags=" + tags + '}';
    }
}
