package jp.isols.common.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ImageUtilsTest {

    @SuppressWarnings("unused")
    private static final Logger logger = LogManager.getLogger(ImageUtilsTest.class);

	@BeforeEach
    public void beforeEach() {
    }

    private Path getResourceFile(final String path) {
        URI resourceUri = null;
        try {
            resourceUri = getClass().getClassLoader().getResource(path).toURI();
            return Paths.get(resourceUri);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unused")
	private String getResourceFilePath(final String path) {
        return getResourceFile(path).toString();
    }

    @Test
    public void constractorTest() {
        assertThrows(IllegalAccessError.class, () -> {
            try {
                Constructor<?> constructor = ImageUtils.class.getDeclaredConstructor();
                constructor.setAccessible(true);
                constructor.newInstance();
            } catch (InvocationTargetException e) {
                throw e.getCause();
            }
        });
    }

    @Test
    public void getFormatNameTest() {
    	{ /* 画像ファイル以外 */
			Path testTarget = getResourceFile("testFile-A.txt");
			assertEquals(ImageUtils.getFormatName(testTarget.toAbsolutePath().toString()), "");
    	}
    	{ /* 画像ファイル */
			Path testTarget = getResourceFile("image/test.bmp");
			assertEquals(ImageUtils.getFormatName(testTarget.toAbsolutePath().toString()), "bmp");
    	}
    	{ /* 画像ファイル */
			Path testTarget = getResourceFile("image/test.gif");
			assertEquals(ImageUtils.getFormatName(testTarget.toAbsolutePath().toString()), "gif");
    	}
    	{ /* 画像ファイル */
			Path testTarget = getResourceFile("image/test.jpg");
			assertEquals(ImageUtils.getFormatName(testTarget.toAbsolutePath().toString()), "JPEG");
    	}
    	{ /* 画像ファイル */
			Path testTarget = getResourceFile("image/test.png");
			assertEquals(ImageUtils.getFormatName(testTarget.toAbsolutePath().toString()), "png");
    	}
    	{ /* 画像ファイル */
			Path testTarget = getResourceFile("image/test.tif");
			assertEquals(ImageUtils.getFormatName(testTarget.toAbsolutePath().toString()), "tif");
    	}
    }
}
