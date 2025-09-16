package dev.rogueai.collection.service;

import dev.rogueai.collection.TestConfig;
import dev.rogueai.collection.db.MyBatisConfig;
import dev.rogueai.collection.service.model.Option;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.List;

@SpringBootTest
@ContextConfiguration(classes = { TestConfig.class, MyBatisConfig.class})
public class DomainTest {

    @Autowired
    private OptionService domainService;

    @Test
    void types() throws IOException {

        List<Option> list = domainService.types();
        Assert.notEmpty(list, "Domain list is empty?");
    }
}
