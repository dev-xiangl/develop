package tdpay.mvc.utils;

import java.io.FileNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * リソースユーティリティクラス
 */
public class ResourceUtils extends org.springframework.util.ResourceUtils {

    @SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(ResourceUtils.class);

    private ResourceUtils() {
        throw new IllegalAccessError("Constants class.");
    }

    /**
     * ファイルの場所(パス)を、URL形式の文字列に変換する。
     *
     * @param location ロケーション
     * @return URL形式の文字列
     */
    public static String toURLString(final String location) {
		try {
            ResourceUtils.getURL(ResourceUtils.CLASSPATH_URL_PREFIX + "resources/" + location);
        } catch (FileNotFoundException e) {
            logger.warn(e.getMessage());
            return null;
		}
        return location;
    }

    /**
     * ファイルの場所(パス)を、URL形式の文字列に変換する。
     *
     * @param location ロケーション
     * @param defaultLocation 初期ロケーション
     * @return URL形式の文字列
     * @note locationが存在しない場合、defaultLocationに置き換える。
     */
    public static String toURLString(final String location, final String defaultLocation) {
        final String target = toURLString(location);
        return target != null ? target : toURLString(defaultLocation);
    }

}
