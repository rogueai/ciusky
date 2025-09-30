package dev.rogueai.collection.service;

import dev.rogueai.collection.controller.OptionEdit;
import dev.rogueai.collection.service.model.Option;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OptionService extends AbstractService {

    public List<Option> types() {
        return optionSql.types();
    }

    public void insertType(String descr) {
        // TODO: the table TYPE_OPTION have no sequence, we must change the database
    }

    public void updateType(Long id, String descr) {
        optionSql.updateType(id, StringUtils.trimToNull(descr));
    }

    public void deleteType(Long id) {
        // TODO: Before deleting we must change the type of all referenced ciuskys to a default value.
    }

    @Transactional
    public void saveTypes(List<OptionEdit> options) {
        for (OptionEdit option : options) {
            if (option.getId() != null) {
                if (option.isDeleted()) {
                    deleteType(option.getId());
                } else {
                    updateType(option.getId(), option.getDescr());
                }
            } else {
                insertType(option.getDescr());
            }
        }
    }

}
