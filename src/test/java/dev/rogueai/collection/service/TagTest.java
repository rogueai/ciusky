package dev.rogueai.collection.service;

import dev.rogueai.collection.TestConfig;
import dev.rogueai.collection.db.MyBatisConfig;
import dev.rogueai.collection.service.model.Ciusky;
import dev.rogueai.collection.service.model.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ContextConfiguration(classes = { TestConfig.class, MyBatisConfig.class })
public class TagTest {

    @Autowired
    private CiuskyService ciuskyService;

    @Autowired
    private TagService tagService;

    @Test
    void tags() throws IOException {

        Ciusky ciusky = new Ciusky();
        ciusky.setTitle(Randomizer.string(200));
        ciusky.setType(1L);

        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("cantina", "vino"));
        tags.add(new Tag("console", "ps1"));
        tags.add(new Tag("console", "ps2"));
        tags.add(new Tag("other", "boxed"));
        ciusky.setTags(tags);
        ciuskyService.save(ciusky);

        List<String> keys = tagService.getKeys(null);
        Assert.isTrue(keys.size() == 3, "Wrong number of keys");

        keys = tagService.getKeys("c");
        Assert.isTrue(keys.size() == 2, "Wrong number of keys");

        keys = tagService.getKeys("ca");
        Assert.isTrue(keys.size() == 1, "Wrong number of keys");

        keys = tagService.getKeys("test");
        Assert.isTrue(keys.isEmpty(), "Wrong number of keys");

        List<String> values = tagService.getValues("cantina", null);
        Assert.isTrue(values.size() == 1, "Wrong number of values");

        values = tagService.getValues("console", null);
        Assert.isTrue(values.size() == 2, "Wrong number of values");

        values = tagService.getValues("console", "p");
        Assert.isTrue(values.size() == 2, "Wrong number of values");

        values = tagService.getValues("console", "ps1");
        Assert.isTrue(values.size() == 1, "Wrong number of values");

    }
}
