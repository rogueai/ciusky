package dev.rogueai.collection;

import dev.rogueai.collection.service.CiuskyService;
import dev.rogueai.collection.service.ImageService;
import dev.rogueai.collection.service.Randomizer;
import dev.rogueai.collection.service.model.Ciusky;
import dev.rogueai.collection.service.model.Tag;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@DependsOn(value = "dataSourceInitializer")
public class AppInitializer {

    @Autowired
    private CiuskyService ciuskyService;

    @Autowired
    private ImageService imageService;

    @PostConstruct
    public void init() throws IOException {
        List<Ciusky> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {

            Ciusky ciusky = new Ciusky();
            ciusky.setTitle(Randomizer.string(10));
            ciusky.setDescription(Randomizer.string(50));
            ciusky.setType(Randomizer.any(1,2,3,4,5,6,7));
            ciusky.setQuality(Randomizer.any(0, 1, 2, 3, 4, 5));

            ArrayList<Tag> tags = new ArrayList<>();

            tags.add(new Tag( //
                    "console", //
                    Randomizer.any("ps1", "ps2", "ps2", "ita", "eng", "pal")));
            tags.add(new Tag( //
                    "region",
                    Randomizer.any("ps1", "ps2", "ps2", "ita", "eng", "pal")));
            tags.add(new Tag( //
                    "language",
                    Randomizer.any("ps1", "ps2", "ps2", "ita", "eng", "pal")));
            ciusky.setTags(tags);
            list.add(ciusky);

        }

        ciuskyService.saveAll(list);

        for (Ciusky ciusky : list) {
            for (int i = 0; i <= Randomizer.any(0, 1, 2, 3, 4); i++) {
                String name = Randomizer.string(10) + ".png";
                byte[] image = Randomizer.image(100, 100);
                imageService.addResource(ciusky.getId(), name, image);
            }
        }

    }
}
