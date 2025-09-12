package com.rogueai.collection.service;

import com.rogueai.collection.db.dto.PersonDto;
import com.rogueai.collection.db.mapper.IPersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private IPersonMapper personMapper;

    public List<PersonDto> getAll() {
        return personMapper.list();
    }

    public PersonDto get(Long id) {
        return personMapper.getPerson(id);
    }

    public int insert(PersonDto dto) {
        return personMapper.insert(dto);
    }

    public int update(PersonDto dto) {
        return personMapper.update(dto);
    }

    public boolean delete(Long id) {
        int rows = personMapper.delete(id);
        return rows != 0;
    }

}
