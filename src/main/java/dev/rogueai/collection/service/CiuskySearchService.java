package dev.rogueai.collection.service;

import dev.rogueai.collection.db.dto.CiuskySearchEntity;
import dev.rogueai.collection.service.model.CiuskyFilter;
import dev.rogueai.collection.service.model.CiuskySearch;
import dev.rogueai.collection.service.model.TagFilter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@Service
public class CiuskySearchService extends AbstractService {

    private static final Log logger = LogFactory.getLog(CiuskySearchService.class);

    public List<CiuskySearch> findAll(CiuskyFilter filter) {
        String text = StringUtils.trim(filter.getText());
        List<TagFilter> tags = parseTags(StringUtils.trim(filter.getTags()));
        List<Long> types = filter.getTypes();

        List<CiuskySearchEntity> entities = searchSql.search(text, types, tags);
        logger.info("Search with tags: " + tags);

        return mapper.toCiuskySearchList(entities);
    }

    private List<TagFilter> parseTags(String tags) {
        List<TagFilter> filters = new ArrayList<>();

        if (tags.isBlank()) {
            return filters;
        }

        // bazinga:html con
        StringTokenizer stringTokenizer = new StringTokenizer(tags, " \t\n\r\f");
        while (stringTokenizer.hasMoreTokens()) {
            String token = stringTokenizer.nextToken();
            if (StringUtils.isAlphanumeric(token) || token.contains(":")) {
                StringTokenizer tagTokenizer = new StringTokenizer(token, ":");
                if (tagTokenizer.countTokens() > 0 && tagTokenizer.countTokens() <= 2) {
                    String key = tagTokenizer.nextToken();
                    if (tagTokenizer.hasMoreTokens()) {
                        String value = tagTokenizer.nextToken();
                        filters.add(new TagFilter(key, value));
                    } else {
                        filters.add(new TagFilter(key));
                    }
                } else {
                    throw new IllegalArgumentException("Invalid Tag: " + token);
                }
            } else {
                throw new IllegalArgumentException("Invalid Tag: " + token);
            }
        }

        return filters;
    }

}

// package package:completo package:comp pack package:comp

