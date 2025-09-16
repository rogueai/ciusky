package dev.rogueai.collection.service;

import dev.rogueai.collection.db.sql.IDomainSql;
import dev.rogueai.collection.service.model.Domain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DomainService {

    @Autowired
    IDomainSql domainSql;

    public List<Domain> types() {
        return domainSql.allTypes();
    }

}
