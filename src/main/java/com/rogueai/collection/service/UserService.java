package com.rogueai.collection.service;

import com.rogueai.collection.db.dto.PersonEntity;
import com.rogueai.collection.db.sql.IPersonSql;
import com.rogueai.collection.service.mapper.UserMapper;
import com.rogueai.collection.service.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserMapper userMapper;

    private final IPersonSql personMapper;

    public UserService(UserMapper userMapper, IPersonSql personMapper) {
        this.userMapper = userMapper;
        this.personMapper = personMapper;
    }

    public List<Person> getAll() {
        List<PersonEntity> list = personMapper.list();
        return userMapper.toPersonList(list);
    }

    public Person get(Long id) {
        PersonEntity entity = personMapper.getPerson(id);
        return userMapper.toPerson(entity);
    }

    @Transactional
    public int insert(PersonEntity dto) {
        return personMapper.insert(dto);
    }

    @Transactional
    public int update(PersonEntity dto) {
        return personMapper.update(dto);
    }

    @Transactional
    public boolean delete(Long id) {
        int rows = personMapper.delete(id);
        return rows != 0;
    }

}
