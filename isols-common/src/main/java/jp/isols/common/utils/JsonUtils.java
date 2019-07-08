package jp.isols.common.utils;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * JSONユーティリティクラス
 */
public class JsonUtils {

    @SuppressWarnings("unused")
    private static final Logger logger = LogManager.getLogger(JsonUtils.class);

    private static ObjectMapper mapper = new ObjectMapper();

    private JsonUtils() {
        throw new IllegalAccessError("Constants class.");
    }

    /**
     * 文字列に変換する。
     *
     * @param obj 対象Object
     * @return 変換された文字列
     */
    public static String toString(final Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Mapに変換する。
     *
     * @param json 対象
     * @return 変換されたMapクラスのインスタンス
     */
    public static Map<String, Object> toMap(final String json) {
        if (StringUtils.isBlank(json)) {
            return null;
        }

        try {
            return mapper.readValue(json, new TypeReference<LinkedHashMap<String, Object>>() {});
        } catch (JsonParseException e) {
            return null;
        } catch (JsonMappingException e) {
            return null;
        } catch (IOException e) {
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Objectに変換する。
     *
     * @param json 対象
     * @param clazz 変換するクラス
     * @return 変換されたObjectクラスのインスタンス
     */
    public static <T> T toObject(final String json, final Class<T> clazz) {
        if (StringUtils.isBlank(json)) {
            return null;
        }

        try {
            return mapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Objectに変換する。
     *
     * @param json 対象
     * @param typeRef 変換するクラス
     * @return 変換されたObjectクラスのインスタンス
     */
    public static <T> T toObject(final String json, final TypeReference<T> typeRef) {
        T object = null;

        try {
            object = mapper.readValue(json, typeRef);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return object;
    }

    /**
     * 配列かを判断する。
     *
     * @param json 対象
     * @param key 判断するJSONキー
     * @return 判断結果
     * @retval true JSONキーの値が配列である
     * @retval false JSONキーの値が配列でない
     */
    public static boolean isArray(final String json, final String key) {
        JsonNode jsonNode;
        try {
            jsonNode = mapper.readTree(json).get(key);
        } catch (IOException e) {
            return false;
        }

        return jsonNode == null ? false : jsonNode.isArray();
    }

    /**
     * JSONノードを追加する。
     *
     * @param json 対象
     * @param objMap 追加するキーと値のマップ
     * @return JSON文字列
     */
    public static String addNode(final String json, final Map<String, Object> objMap) {
        JsonNode jsonNode;
		try {
			jsonNode = mapper.readTree(StringUtils.isBlank(json) ? "{}" : json);
		} catch (IOException e) {
            throw new RuntimeException(e);
		}

        objMap.forEach((key, value) -> {
            String quote = "";
            if (value instanceof String) {
                quote = "\"";
            }
            ((ObjectNode)jsonNode).putPOJO(key, quote + value + quote);
        });

		return jsonNode.toString();
    }

    /**
     * JSONノードを追加する。
     *
     * @param json 対象
     * @param jsonKey 対象JSONキー
     * @param list 追加するリスト
     * @return JSON文字列
     * @note 存在する対象JSONキーの場合、元要素を子要素として移動後に、そのキーに対し、追加要素を追加する。<br>
     *       存在しない対象JSONキーの場合、キーを新規作成し、そのキーに対し、追加要素を追加する。
     */
    public static String addNode(final String json, final String jsonKey, final List<? extends Object> list) {
        Map<String, Object> map = toMap(json);
        map.put(jsonKey, list);

		return JsonUtils.toString(map);
    }
}
