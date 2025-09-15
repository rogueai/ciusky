package com.rogueai.collection.service;

import com.rogueai.collection.Randomizer;
import com.rogueai.collection.TestConfig;
import com.rogueai.collection.db.DbConfig;
import com.rogueai.collection.service.model.Ciusky;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.Assert;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@SpringBootTest
@ContextConfiguration(classes = { TestConfig.class, DbConfig.class})
public class CiuskyTest {

    @Value("classpath:/ps1-games-list.txt")
    Resource resource;

    @Autowired
    private DriverManagerDataSource dataSource;

    @Autowired
    private CiuskyService ciuskyService;

    @Test
    void insert() throws IOException {

        Assert.isTrue(resource.exists(), "File not found.");

        List<String> lines =  Files.readAllLines(resource.getFile().toPath());

        List<Ciusky> list = new ArrayList<>();
        for(String line : lines) {
            StringTokenizer tokenizer = new StringTokenizer(line, "\t");

            Ciusky ciusky = new Ciusky();
            ciusky.title = tokenizer.nextToken();
            ciusky.typeOption = 4;
            ciusky.quality = Randomizer.random(0, 1, 2, 3, 4, 5);
            ciusky.paidPrice = Randomizer.random();
            ciusky.marketPrice = Randomizer.random();
            list.add(ciusky);
            System.out.println(ciusky);
        }
        ciuskyService.saveAll(list);

    }
}
