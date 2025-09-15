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

    @Autowired
    private CiuskyService ciuskyService;

    @Test
    void insert() throws IOException {

        List<Ciusky> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            Ciusky ciusky = new Ciusky();
            ciusky.title = Randomizer.randomString(200);
            ciusky.typeOption = 4;
            ciusky.quality = Randomizer.random(0, 1, 2, 3, 4, 5);
            ciusky.paidPrice = Randomizer.random();
            ciusky.marketPrice = Randomizer.random();
            list.add(ciusky);
        }
        ciuskyService.saveAll(list);

    }
}
