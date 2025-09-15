package dev.rogueai.collection.service;

import dev.rogueai.collection.TestConfig;
import dev.rogueai.collection.db.DbConfig;
import dev.rogueai.collection.service.model.Ciusky;
import dev.rogueai.collection.service.model.CiuskyType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
            ciusky.title = Randomizer.string(200);
            ciusky.type = new CiuskyType(4L);
            ciusky.quality = Randomizer.any(0, 1, 2, 3, 4, 5);
            ciusky.paidPrice = Randomizer.bigDecimal();
            ciusky.marketPrice = Randomizer.bigDecimal();
            list.add(ciusky);
        }
        ciuskyService.saveAll(list);

    }
}
