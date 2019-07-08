package jp.isols.common.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

public class JsonUtilsTest {

    @SuppressWarnings("unused")
    private static final Logger logger = LogManager.getLogger(JsonUtilsTest.class);

	@BeforeEach
    public void beforeEach() {
    }

    @Test
    public void constractorTest() {
        assertThrows(IllegalAccessError.class, () -> {
            try {
                Constructor<?> constructor = JsonUtils.class.getDeclaredConstructor();
                constructor.setAccessible(true);
                constructor.newInstance();
            } catch (InvocationTargetException e) {
                throw e.getCause();
            }
        });
    }

    @Test
    public void toStringTest() {
        String jsonString;

        { /* 配列なしオブジェクト */
            final TestJsonObject obj = new TestJsonObject();
            obj.setString("test");
            obj.setInteger(1);
            obj.setDecimal(new BigDecimal("123.456"));

            jsonString = JsonUtils.toString(obj);
            final String expected =
                "{\"string\":\"test\",\"integer\":1,\"decimal\":123.456}";

            assertNotNull(jsonString);
            assertEquals(expected, jsonString);
        }

        { /* 配列ありオブジェクト */
            final TestJsonArrayObject obj = new TestJsonArrayObject();
            obj.setString("test");
            obj.setInteger(1);
            obj.setDecimal(new BigDecimal("123.456"));
            obj.setStringList(null);
            obj.setIntegerList(null);
            obj.setDecimalList(null);

            jsonString = JsonUtils.toString(obj);
            final String expected =
                "{\"string\":\"test\",\"integer\":1,\"decimal\":123.456,\"stringList\":null,\"integerList\":null,\"decimalList\":null}";

            assertNotNull(jsonString);
            assertEquals(expected, jsonString);
        }

        { /* 配列ありオブジェクト */
            final TestJsonArrayObject obj = new TestJsonArrayObject();
            obj.setString("test");
            obj.setInteger(1);
            obj.setDecimal(new BigDecimal("123.456"));
            obj.setStringList(Arrays.asList("item1", "item2", "item3"));
            obj.setIntegerList(Arrays.asList(111, 222, 333));
            obj.setDecimalList(Arrays.asList(new BigDecimal("123.456"), new BigDecimal("789.012"), new BigDecimal("-123.456"), new BigDecimal("-789.012")));

            jsonString = JsonUtils.toString(obj);
            final String expected =
                "{\"string\":\"test\",\"integer\":1,\"decimal\":123.456," + 
                "\"stringList\":[\"item1\",\"item2\",\"item3\"]," +
                "\"integerList\":[111,222,333]," +
                "\"decimalList\":[123.456,789.012,-123.456,-789.012]}";

            assertNotNull(jsonString);
            assertEquals(expected, jsonString);
        }
    }

    @Test
    public void toOMapTest() {
        Map<String, Object> map;

        { /* null */
            map = JsonUtils.toMap(null);

            assertNull(map);
        }

        { /* 文字列 */
	        final String jsonString = "{\"string\":\"test\",\"integer\":1,\"decimal\":123.456}";

            map = JsonUtils.toMap(jsonString);

            assertNotNull(map);
            assertEquals(map.get("string"), "test");
            assertEquals(map.get("integer"), Integer.valueOf(1));
            assertEquals(new BigDecimal(map.get("decimal").toString()), new BigDecimal("123.456"));
        }

        { /* 配列 */
	        final String jsonString = "[ {\"string\":\"test\",\"integer\":1,\"decimal\":123.456} ]";

            map = JsonUtils.toMap(jsonString);

            assertNull(map);
        }

    }

    @Test
    public void toObjectTest() {
    	{ /* 文字列 */
	        final String jsonString = "{\"string\":\"test\",\"integer\":1,\"decimal\":123.456}";
	
	        TestJsonObject result = JsonUtils.toObject(jsonString, TestJsonObject.class);
	
	        assertNotNull(result);
	        assertEquals("test", result.getString());
	        assertEquals(Integer.valueOf(1), result.getInteger());
	        assertEquals(new BigDecimal("123.456"), result.getDecimal());
    	}

    	{ /* 配列 */
	        final String jsonString = "[ {\"string\":\"test\",\"integer\":1,\"decimal\":123.456} ]";
	
	        @SuppressWarnings("unchecked")
			List<Map<String, Object>> result = JsonUtils.toObject(jsonString, List.class);
	
	        assertNotNull(result);
	        assertEquals("test", result.get(0).get("string"));
	        assertEquals(Integer.valueOf(1), result.get(0).get("integer"));
	        assertEquals(new BigDecimal("123.456"), BigDecimal.valueOf((Double)result.get(0).get("decimal")));
    	}
    }

    @Test
    public void isArrayTest() {
        String jsonString; 
        boolean result = false;

        { /* 配列なしオブジェクト */
            final TestJsonObject obj = new TestJsonObject();
            obj.setString("test");
            obj.setInteger(1);
            obj.setDecimal(new BigDecimal("123.456"));

            jsonString = JsonUtils.toString(obj);

            result = JsonUtils.isArray(jsonString, null);
            assertFalse(result);
            result = JsonUtils.isArray(jsonString, "");
            assertFalse(result);
            result = JsonUtils.isArray(jsonString, "string");
            assertFalse(result);
            result = JsonUtils.isArray(jsonString, "integer");
            assertFalse(result);
            result = JsonUtils.isArray(jsonString, "decimal");
            assertFalse(result);
            result = JsonUtils.isArray(jsonString, "xxx");
            assertFalse(result);
        }

        { /* 配列ありオブジェクト */
            final TestJsonArrayObject obj = new TestJsonArrayObject();
            obj.setString("test");
            obj.setInteger(1);
            obj.setDecimal(new BigDecimal("123.456"));
            obj.setStringList(null);
            obj.setIntegerList(null);
            obj.setDecimalList(null);

            jsonString = JsonUtils.toString(obj);

            result = JsonUtils.isArray(jsonString, null);
            assertFalse(result);
            result = JsonUtils.isArray(jsonString, "");
            assertFalse(result);
            result = JsonUtils.isArray(jsonString, "string");
            assertFalse(result);
            result = JsonUtils.isArray(jsonString, "integer");
            assertFalse(result);
            result = JsonUtils.isArray(jsonString, "decimal");
            assertFalse(result);
            result = JsonUtils.isArray(jsonString, "stringList");
            assertFalse(result);
            result = JsonUtils.isArray(jsonString, "integerList");
            assertFalse(result);
            result = JsonUtils.isArray(jsonString, "decimalList");
            assertFalse(result);
        }

        { /* 配列ありオブジェクト */
            final TestJsonArrayObject obj = new TestJsonArrayObject();
            obj.setString("test");
            obj.setInteger(1);
            obj.setDecimal(new BigDecimal("123.456"));
            obj.setStringList(Arrays.asList("item1", "item2", "item3"));
            obj.setIntegerList(Arrays.asList(111, 222, 333));
            obj.setDecimalList(Arrays.asList(new BigDecimal("123.456"), new BigDecimal("789.012"), new BigDecimal("-123.456"), new BigDecimal("-789.012")));

            jsonString = JsonUtils.toString(obj);

            result = JsonUtils.isArray(jsonString, null);
            assertFalse(result);
            result = JsonUtils.isArray(jsonString, "");
            assertFalse(result);
            result = JsonUtils.isArray(jsonString, "string");
            assertFalse(result);
            result = JsonUtils.isArray(jsonString, "integer");
            assertFalse(result);
            result = JsonUtils.isArray(jsonString, "decimal");
            assertFalse(result);
            result = JsonUtils.isArray(jsonString, "stringList");
            assertTrue(result);
            result = JsonUtils.isArray(jsonString, "integerList");
            assertTrue(result);
            result = JsonUtils.isArray(jsonString, "decimalList");
            assertTrue(result);
        }
    }

    @Test
    public void addNodeTest_キー指定なし() {
        String jsonString; 

        { /* 配列なしオブジェクト */
            final TestJsonObject obj = new TestJsonObject();
            obj.setString("test");
            obj.setInteger(1);
            obj.setDecimal(new BigDecimal("123.456"));

            jsonString = JsonUtils.toString(obj);

            final Map<String, Object> objMap = new LinkedHashMap<>();
            objMap.put("string1", "test");
            objMap.put("integer1", 1);
            objMap.put("decimal1", new BigDecimal("123.456"));

            final String actual = JsonUtils.addNode(jsonString, objMap);
            logger.debug(actual);
        }

        { /* 配列ありオブジェクト */
            final TestJsonArrayObject obj = new TestJsonArrayObject();
            obj.setString("test");
            obj.setInteger(1);
            obj.setDecimal(new BigDecimal("123.456"));
            obj.setStringList(null);
            obj.setIntegerList(null);
            obj.setDecimalList(null);

            jsonString = JsonUtils.toString(obj);

            final Map<String, Object> objMap = new LinkedHashMap<>();
            objMap.put("string1", "test");
            objMap.put("integer1", 1);
            objMap.put("decimal1", new BigDecimal("123.456"));

            final String actual = JsonUtils.addNode(jsonString, objMap);
            logger.debug(actual);
        }

        { /* 配列ありオブジェクト */
            final TestJsonArrayObject obj = new TestJsonArrayObject();
            obj.setString("test");
            obj.setInteger(1);
            obj.setDecimal(new BigDecimal("123.456"));
            obj.setStringList(Arrays.asList("item1", "item2", "item3"));
            obj.setIntegerList(Arrays.asList(111, 222, 333));
            obj.setDecimalList(Arrays.asList(new BigDecimal("123.456"), new BigDecimal("789.012"), new BigDecimal("-123.456"), new BigDecimal("-789.012")));

            jsonString = JsonUtils.toString(obj);

            final Map<String, Object> objMap = new LinkedHashMap<>();
            objMap.put("string1", "test");
            objMap.put("integer1", 1);
            objMap.put("decimal1", new BigDecimal("123.456"));

            final String actual = JsonUtils.addNode(jsonString, objMap);
            logger.debug(actual);
        }
    }

    @Test
    public void addNodeTest_キー指定あり() {
        String jsonString; 

        { /* 配列なしオブジェクト */
            final TestJsonObject obj = new TestJsonObject();
            obj.setString(null);
            obj.setInteger(1);
            obj.setDecimal(new BigDecimal("123.456"));

            jsonString = JsonUtils.toString(obj);

            final List<String> list = new ArrayList<>();
            list.add("test1");
            list.add("test2");

            final String actual = JsonUtils.addNode(jsonString, "string", list);
            logger.debug(actual);
        }

        { /* 配列なしオブジェクト */
            final TestJsonObject obj = new TestJsonObject();
            obj.setString("test");
            obj.setInteger(1);
            obj.setDecimal(new BigDecimal("123.456"));

            jsonString = JsonUtils.toString(obj);

            final List<String> list = new ArrayList<>();
            list.add("test1");
            list.add("test2");

            final String actual = JsonUtils.addNode(jsonString, "string", list);
            logger.debug(actual);
        }

        { /* 配列なしオブジェクト */
            final TestJsonObject obj = new TestJsonObject();
            obj.setString("test");
            obj.setInteger(1);
            obj.setDecimal(new BigDecimal("123.456"));

            jsonString = JsonUtils.toString(obj);

            final List<String> list = new ArrayList<>();
            list.add("test1");
            list.add("test2");

            final String actual = JsonUtils.addNode(jsonString, "newKeyString", list);
            logger.debug(actual);
        }

        { /* 配列ありオブジェクト */
            final TestJsonArrayObject obj = new TestJsonArrayObject();
            obj.setString("test");
            obj.setInteger(1);
            obj.setDecimal(new BigDecimal("123.456"));
            obj.setStringList(null);
            obj.setIntegerList(null);
            obj.setDecimalList(null);

            jsonString = JsonUtils.toString(obj);

            final List<String> list = new ArrayList<>();
            list.add("test1");
            list.add("test2");

            final String actual = JsonUtils.addNode(jsonString, "stringList", list);
            logger.debug(actual);
        }

        { /* 配列ありオブジェクト */
            final TestJsonArrayObject obj = new TestJsonArrayObject();
            obj.setString("test");
            obj.setInteger(1);
            obj.setDecimal(new BigDecimal("123.456"));
            obj.setStringList(Arrays.asList("item1", "item2", "item3"));
            obj.setIntegerList(Arrays.asList(111, 222, 333));
            obj.setDecimalList(Arrays.asList(new BigDecimal("123.456"), new BigDecimal("789.012"), new BigDecimal("-123.456"), new BigDecimal("-789.012")));

            jsonString = JsonUtils.toString(obj);

            final List<String> list = new ArrayList<>();
            list.add("test1");
            list.add("test2");

            final String actual = JsonUtils.addNode(jsonString, "stringList", list);
            logger.debug(actual);
        }
    }

    @Test
    public void teeest() {
        final TestObject obj = new TestObject();
        obj.setMid("1234567890123456789012");
        obj.setAmount("1000000");
        obj.setOrderId("0000010010000120191001120000");

        System.out.println("json string:\n" + JsonUtils.toString(obj));
        System.out.println("json encrypt:\n" + StringUtils.encrypt(JsonUtils.toString(obj)));
    }

    @SuppressWarnings("serial")
    @JsonPropertyOrder({"string", "integer", "decimal"})
    @Data
    private static class TestJsonObject implements Serializable {
        private String string;
        private Integer integer;
        private BigDecimal decimal;
    }

    @SuppressWarnings("serial")
    @JsonPropertyOrder({"string", "integer", "decimal", "stringList", "integerList", "decimalList"})
    @Data
    private static class TestJsonArrayObject implements Serializable {
        private String string;
        private Integer integer;
        private BigDecimal decimal;
        private List<String> stringList;
        private List<Integer> integerList;
        private List<BigDecimal> decimalList;
    }

    @SuppressWarnings("serial")
    @Data
    private static class TestObject implements Serializable {
        private String mid;
        private String amount;
        private String orderId;
    }
}
