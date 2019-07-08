package tdpay.mvc.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tdpay.mvc.config.AppPropertyPlaceholderConfigurer;

/**
 * プロパティユーティリティクラス
 */
public class PropertiesUtils extends org.springframework.util.ResourceUtils {

    @SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(PropertiesUtils.class);

    private static Map<String,String> propertiesMap = null;

    private PropertiesUtils() {
        throw new IllegalAccessError("Constants class.");
    }

    /**
     * プロパティの値を設定する。
     *
     * @param properties Propertiesクラスのインスタンス
     * @note {@link AppPropertyPlaceholderConfigurer} から呼び出される。
     */
    public static void setProperty(final Properties properties) {
        if (properties == null) {
            propertiesMap = null;
            return;
        }

        if (propertiesMap == null) {
            propertiesMap = new HashMap<>();
        } else {
            propertiesMap.clear();
        }

        for (final Object key : properties.keySet()) {
            final String keyStr = key.toString();
            final String value = String.valueOf(properties.get(keyStr));
            propertiesMap.put(keyStr,value);
        }
    }

    /**
     * プロパティの値を取得する。
     *
     * @param name プロパティのキー
     * @return プロパティの値
     */
    public static String getProperty(final String name) {
        if (propertiesMap == null) {
            return null;
        }
        return propertiesMap.get(name);
    }

    /**
     * プロパティの値を取得する。
     *
     * @return 格納されたプロパティの値マップ
     */
    public static Map<String, String> getProperties() {
        return propertiesMap;
    }

}
