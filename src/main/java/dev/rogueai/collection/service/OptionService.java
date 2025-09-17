package dev.rogueai.collection.service;

import dev.rogueai.collection.service.model.Option;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptionService extends AbstractService {

    public List<Option> types() {
        return optionSql.allOptions();
    }

}
