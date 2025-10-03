package dev.rogueai.ciusky.service;

import dev.rogueai.ciusky.db.dto.CiuskyImageEntity;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageService extends AbstractService {

    private static final int THUMBNAIL_SIZE = 250;

    private static final boolean MANTAIN_ASPECT_RATIO = false;

    private byte[] defaultImage;

    @Autowired
    private Path imageDir;

    @PostConstruct
    public void init() throws IOException {
        Assert.isTrue(Files.isDirectory(imageDir), "Invalid directory: " + imageDir.toString());
        try (InputStream resourceAsStream = getClass().getResourceAsStream("/static/images/image-4@2x.jpg")) {
            Assert.notNull(resourceAsStream, "Default image non found");
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

    public void delete(Long ciuskyId, String uuid) {
        imageSql.delete(ciuskyId, uuid);
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

        if (MANTAIN_ASPECT_RATIO) {
            Image scaled = original;
            double scale;
            double imw = original.getWidth();
            double imh = original.getHeight();
            if (THUMBNAIL_SIZE / imw < THUMBNAIL_SIZE / imh) {
                scale = THUMBNAIL_SIZE / imw;
                scaled = original.getScaledInstance((int) (scale * imw), (int) (scale * imh), Image.SCALE_SMOOTH);
            } else if (THUMBNAIL_SIZE / imw > THUMBNAIL_SIZE / imh) {
                scale = THUMBNAIL_SIZE / imh;
                scaled = original.getScaledInstance((int) (scale * imw), (int) (scale * imh), Image.SCALE_SMOOTH);
            } else if (THUMBNAIL_SIZE / imw == THUMBNAIL_SIZE / imh) {
                scale = THUMBNAIL_SIZE / imw;
                scaled = original.getScaledInstance((int) (scale * imw), (int) (scale * imh), Image.SCALE_SMOOTH);
            }

            // Create a buffered image with transparency
            BufferedImage thumbnail = new BufferedImage(scaled.getWidth(null), scaled.getHeight(null), BufferedImage.TYPE_INT_ARGB);
            // Draw the image on to the buffered image
            Graphics2D bGr = thumbnail.createGraphics();
            bGr.drawImage(scaled, 0, 0, null);
            bGr.dispose();
            return thumbnail;
        } else {
            BufferedImage thumbnail = new BufferedImage(THUMBNAIL_SIZE, THUMBNAIL_SIZE, BufferedImage.TYPE_INT_RGB);
            thumbnail.createGraphics().drawImage(original.getScaledInstance(THUMBNAIL_SIZE, THUMBNAIL_SIZE, Image.SCALE_SMOOTH), 0, 0, null);
            return thumbnail;
        }
    }

}


