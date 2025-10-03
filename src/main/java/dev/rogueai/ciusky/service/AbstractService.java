package dev.rogueai.ciusky.service;

import dev.rogueai.ciusky.db.sql.ICiuskySearchSql;
import dev.rogueai.ciusky.db.sql.ICiuskySql;
import dev.rogueai.ciusky.db.sql.IImageSql;
import dev.rogueai.ciusky.db.sql.IOptionSql;
import dev.rogueai.ciusky.db.sql.ITagSql;
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
