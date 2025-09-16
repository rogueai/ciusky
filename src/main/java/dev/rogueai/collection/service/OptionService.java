package dev.rogueai.collection.service;

import dev.rogueai.collection.db.sql.IOption;
import dev.rogueai.collection.service.model.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptionService {

    @Autowired
    IOption optionSql;

    public List<Option> types() {
        return optionSql.allOptions();
    }

}
