package dev.rogueai.ciusky.service;

import dev.rogueai.ciusky.service.model.Option;
import dev.rogueai.ciusky.service.model.OptionEdit;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OptionService extends AbstractService {

    public List<Option> types() {
        return optionSql.types();
    }

    public List<OptionEdit> typesForEdit() {
        return optionSql.getTypesForEdit();
    }

    public void insertType(String descr) {
        Option option = new Option(null, StringUtils.trimToNull(descr));
        optionSql.insertType(option);
    }

    public void updateType(Long id, String descr) {
        Option option = new Option(id, StringUtils.trimToNull(descr));
        optionSql.updateType(option);
    }

    public void deleteType(Long id) {
        optionSql.deleteType(id);
    }

    @Transactional
    public void saveTypes(List<OptionEdit> options) {
        for (OptionEdit option : options) {
            if (option.isNativ()) {
                continue;
            }
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
