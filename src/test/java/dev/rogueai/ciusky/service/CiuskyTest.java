package dev.rogueai.ciusky.service;

import dev.rogueai.ciusky.TestConfig;
import dev.rogueai.ciusky.db.MyBatisConfig;
import dev.rogueai.ciusky.service.model.Ciusky;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ContextConfiguration(classes = { TestConfig.class, MyBatisConfig.class})
public class CiuskyTest {

    @Autowired
    private CiuskyService ciuskyService;

    @Test
    void insert() throws IOException {

        List<Ciusky> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            Ciusky ciusky = new Ciusky();
            ciusky.setTitle(Randomizer.string(200));
            ciusky.setType(1L);
            ciusky.setQuality(Randomizer.any(0, 1, 2, 3, 4, 5));
            ciusky.setPaidPrice(Randomizer.bigDecimal());
            ciusky.setMarketPrice(Randomizer.bigDecimal());
            list.add(ciusky);
        }
        ciuskyService.saveAll(list);

    }
}
