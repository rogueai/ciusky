package dev.rogueai.collection.service;

import dev.rogueai.collection.db.sql.ICiuskySearchSql;
import dev.rogueai.collection.db.sql.ICiuskySql;
import dev.rogueai.collection.db.sql.IImageSql;
import dev.rogueai.collection.db.sql.IOptionSql;
import dev.rogueai.collection.db.sql.ITagSql;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractService {

    @Autowired
    protected IMapper mapper;

    @Autowired
    protected ICiuskySearchSql searchSql;

    @Autowired
    protected ICiuskySql ciuskySql;

    @Autowired
    protected IImageSql imageSql;

    @Autowired
    protected IOptionSql optionSql;

    @Autowired
    protected ITagSql tagSql;
}
