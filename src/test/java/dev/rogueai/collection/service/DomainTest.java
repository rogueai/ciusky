package dev.rogueai.collection.service;

import dev.rogueai.collection.TestConfig;
import dev.rogueai.collection.db.MyBatisConfig;
import dev.rogueai.collection.service.model.Ciusky;
import dev.rogueai.collection.service.model.Domain;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ContextConfiguration(classes = { TestConfig.class, MyBatisConfig.class})
public class DomainTest {

    @Autowired
    private DomainService domainService;

    @Test
    void types() throws IOException {

        List<Domain> list = domainService.types();
        Assert.notEmpty(list, "Domain list is empty?");
    }
}
