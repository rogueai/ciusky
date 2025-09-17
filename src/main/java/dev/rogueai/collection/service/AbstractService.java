package dev.rogueai.collection.service;

import dev.rogueai.collection.db.sql.ICiuskySearchSql;
import dev.rogueai.collection.db.sql.ICiuskySql;
import dev.rogueai.collection.db.sql.IImageSql;
import dev.rogueai.collection.db.sql.IOptionSql;
import dev.rogueai.collection.db.sql.ITagSql;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractService {

    @Autowired
    IMapper mapper;

    @Autowired
    public ICiuskySearchSql searchSql;

    @Autowired
    public ICiuskySql ciuskySql;

    @Autowired
    public IImageSql imageSql;

    @Autowired
    public IOptionSql optionSql;

    @Autowired
    public ITagSql tagSql;
}
