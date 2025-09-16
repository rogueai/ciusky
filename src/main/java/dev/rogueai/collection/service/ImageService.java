package dev.rogueai.collection.service;

import dev.rogueai.collection.db.dto.CiuskyImageEntity;
import dev.rogueai.collection.db.sql.IImageSql;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageService {

    private static final int THUMBNAIL_SIZE = 100;

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

    public void addResource(Long ciuskyId, String name, byte[] image) throws IOException {
        String uuid = UUID.randomUUID().toString();
        CiuskyImageEntity cie = new CiuskyImageEntity(ciuskyId, uuid, name);
        storeImage(cie, image);
        storeImageThumbnail(cie, image);
        imageSql.insert(cie);
    }

    public byte[] getResource(String uuid, boolean thumbnail) throws IOException {
        CiuskyImageEntity cie = imageSql.getByUuid(uuid);
        if (cie == null) {
            return defaultImage;
        }
        Path path = getPath(cie, thumbnail);
        if (Files.notExists(path)) {
            return defaultImage;
        }
        return Files.readAllBytes(path);
    }

    private Path getPath(CiuskyImageEntity cie, boolean thumbnail) {
        if (!thumbnail) {
            return Paths.get(String.format("%s/%s/%s-%s", imageDir.toString(), cie.id, cie.uuid, cie.name));
        }
        return Paths.get(String.format("%s/%s/thumbnail-%s-%s", imageDir.toString(), cie.id, cie.uuid, cie.name));
    }

    private void storeImage(CiuskyImageEntity cie, byte[] image) throws IOException {
        Path path = getPath(cie, false);
        Files.createDirectories(path.getParent());
        Files.write(path, image);
    }

    private void storeImageThumbnail(CiuskyImageEntity cie, byte[] image) throws IOException {
        Path path = getPath(cie, true);
        Files.createDirectories(path.getParent());
        ImageIO.write(createThumbnail(image), "png", path.toFile());
    }

    private BufferedImage createThumbnail(byte[] image) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(image);
        BufferedImage original = ImageIO.read(bais);
        BufferedImage thumbnail = new BufferedImage(THUMBNAIL_SIZE, THUMBNAIL_SIZE, BufferedImage.TYPE_INT_RGB);
        thumbnail.createGraphics().drawImage(original.getScaledInstance(THUMBNAIL_SIZE, THUMBNAIL_SIZE, Image.SCALE_SMOOTH), 0, 0, null);
        return thumbnail;
    }

}


