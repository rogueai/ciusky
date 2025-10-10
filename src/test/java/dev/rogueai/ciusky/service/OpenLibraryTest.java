package dev.rogueai.ciusky.service;

import dev.rogueai.ciusky.TestConfig;
import dev.rogueai.ciusky.db.MyBatisConfig;
import dev.rogueai.ciusky.service.ext.Root;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.Assert;

import java.io.IOException;

@SpringBootTest
@ContextConfiguration(classes = { TestConfig.class, MyBatisConfig.class })
public class OpenLibraryTest {

    @Autowired
    private OpenLibrary openLibrary;

    @Test
    void types() throws IOException {

        Throttled<Root> root = openLibrary.searchByTitle("il signore degli anelli", 1);
        Assert.notNull(root, "root is null");
        root = openLibrary.searchByTitle("il signore degli anelli", 1);
        Assert.isTrue(root.timeToWaitPercent > 0, "Throttle is broken");

    }
}
