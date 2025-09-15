package dev.rogueai.collection.service;

import dev.rogueai.collection.db.dto.CiuskyImageEntity;
import dev.rogueai.collection.db.sql.IImageSql;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.UUID;

@Service
public class ImageService {

    @Autowired
    private Path imageDir;

    @Autowired
    private IImageSql imageSql;

    private byte[] defaultImage;

    @PostConstruct
    public void init() throws IOException {
        try (InputStream resourceAsStream = getClass().getResourceAsStream("/static/images/image-4@2x.jpg")) {
            assert resourceAsStream != null;
            defaultImage = resourceAsStream.readAllBytes();
        }
    }

    public void addResource(Long ciuskyId, String name, byte[] image) {
        String uuid = UUID.randomUUID().toString();
        CiuskyImageEntity cie = new CiuskyImageEntity(ciuskyId, uuid, name);
        Path path = Paths.get(String.format("%s/%s/%s-%s", imageDir.toString(), cie.id, cie.uuid, cie.name));
        try {
            Files.createDirectories(path.getParent());
            Files.write(path, image);
            imageSql.insert(cie);
        } catch (Exception e) {
            try {
                Files.delete(path);
            } catch (IOException ex) {
                //
            }
            throw new RuntimeException(e);
        }
    }

    public byte[] getResource(String uuid, boolean thumbnail) throws IOException {
        CiuskyImageEntity cie = imageSql.getByUuid(uuid);
        if (cie == null) {
            return defaultImage;
        }
        Path path = Paths.get(String.format("%s/%s/%s-%s", imageDir.toString(), cie.id, cie.uuid, cie.name));
        if (Files.notExists(path)) {
            return defaultImage;
        }
        return Files.readAllBytes(path);
    }


}
