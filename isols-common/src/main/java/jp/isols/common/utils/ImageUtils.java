package jp.isols.common.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 画像ユーティリティクラス
 */
public class ImageUtils {

    @SuppressWarnings("unused")
    private static final Logger logger = LogManager.getLogger(ImageUtils.class);

    private ImageUtils() {
        throw new IllegalAccessError("Constants class.");
    }

    /**
     * ファイル形式の名称を取得する。
     */
    public static String getFormatName(final byte[] target) {
        try {
            final ByteArrayInputStream bais = new ByteArrayInputStream(target);
            final ImageInputStream iis = ImageIO.createImageInputStream(bais);
            final Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
            final ImageReader reader = iter.next();
            return reader.getFormatName();
        } catch (Exception e) {
        }

        return "";
    }

    /**
     * ファイル形式の名称を取得する。
     */
    public static String getFormatName(final String target) {
        try {
            return getFormatName(Files.readAllBytes(Paths.get(target)));
        } catch (Exception e) {
        }

        return "";
    }

    /**
     * Base64から画像ファイルを保存する。
     *
     * @param imageUri
     * @param targetDir
     * @param extension
     * @return
     * @throws IOException
     */
    public static String saveBase64(final String imageUri, final String targetDir, final String extension) throws IOException {
        byte[] imageBytes = Base64.decodeBase64(imageUri);
        ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
        BufferedImage image = ImageIO.read(bis);

        FileDirUtils.createDirectory(targetDir);
        final String fileName = FileDirUtils.createFile(targetDir, extension);
        final String imagePath = targetDir + File.separator + fileName;

        OutputStream out = new FileOutputStream(imagePath);
        ImageIO.write(image, extension, out);

        return fileName;
    }

}
