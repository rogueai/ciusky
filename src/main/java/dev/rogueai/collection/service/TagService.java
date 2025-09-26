package dev.rogueai.collection.service;

import dev.rogueai.collection.service.model.Tag;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService extends AbstractService {

    /**
     * Return the list of keys that starts with `partialKey`.
     * <br>
     * If `partialKey` is null no filter is applied
     *
     * @param partialKey nullable
     * @return the list of keys
     */
    public List<String> getKeys(String partialKey) {
        String trimmedPartialKey = StringUtils.trim(partialKey);
        return tagSql.searchTagKeys(trimmedPartialKey);
    }

    /**
     * Return the list of values for the `key` that starts with `partialValue`.
     * <br>
     * If `partialValue` is null all values for that `key` are returned.
     *
     * @param key          the key to search
     * @param partialValue nullable
     * @return the list of values
     */
    public List<String> getValues(@NotNull String key, String partialValue) {
        String trimmedKey = StringUtils.trim(key);
        String trimmedPartialValue = StringUtils.trim(partialValue);
        return tagSql.searchTagValues(trimmedKey, trimmedPartialValue);
    }

    public boolean isUnique(@NotNull List<Tag> tags, @NotNull Tag tag) {
        return tags.stream().noneMatch(t -> t.equals(tag));
    }
}
