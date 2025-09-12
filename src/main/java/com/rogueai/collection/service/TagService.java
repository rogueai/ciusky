package com.rogueai.collection.service;


import com.rogueai.collection.db.dto.TagEntity;
import com.rogueai.collection.db.sql.ITagSql;

import com.rogueai.collection.service.mapper.ObjectMapper;
import com.rogueai.collection.service.model.Tag;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    private final ObjectMapper mapper;

    private final ITagSql tagSql;

    public TagService(ObjectMapper mapper, ITagSql tagSql) {
        this.mapper = mapper;
        this.tagSql = tagSql;
    }

    public List<Tag> all() {
        List<TagEntity> list = tagSql.all();
        return mapper.toTagList(list);
    }

    public List<Tag> searchTag(String tag) {
        List<TagEntity> list = tagSql.searchTag(tag);
        return mapper.toTagList(list);
    }

}
