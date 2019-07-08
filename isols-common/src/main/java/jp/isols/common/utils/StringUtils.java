package jp.isols.common.utils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.google.common.base.CaseFormat;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

/**
 * 文字列ユーティリティクラス
 */
public class StringUtils {

    /** 暗号アルゴリズム */
    private static final Map<String, String> ENCRYPT_ALGORITHM_MAP =
        Maps.newHashMap(new ImmutableMap.Builder<String, String>()
            .put("AES", "AES/CBC/PKCS5Padding")
            .put("Blowfish", "Blowfish/CBC/PKCS5Padding")
            .build()
        );

    /** 暗号化キー(256bit = 32byte) */
    private static final byte[] ENCRYPT_KEY = {
        (byte)0xA7, (byte)0x77, (byte)0xCF, (byte)0x75, (byte)0x60, (byte)0x18, (byte)0xBC, (byte)0x59,
        (byte)0x86, (byte)0x36, (byte)0x16, (byte)0x46, (byte)0xDD, (byte)0x2C, (byte)0xF6, (byte)0xA7,
        (byte)0xAE, (byte)0x58, (byte)0xC5, (byte)0xCE, (byte)0x5E, (byte)0x58, (byte)0x1E, (byte)0x79,
        (byte)0xAB, (byte)0x31, (byte)0x78, (byte)0x74, (byte)0x2B, (byte)0x7C, (byte)0xA9, (byte)0x4C,
    };

    /** 初期化ベクトル(128bit = 16byte) */
    private static final byte[] ENCRYPT_IV  = {
        (byte)0x98, (byte)0xD6, (byte)0x17, (byte)0x4C, (byte)0xB6, (byte)0x2E, (byte)0xC8, (byte)0x87,
        (byte)0x86, (byte)0xB9, (byte)0x17, (byte)0x17, (byte)0x45, (byte)0x16, (byte)0x23, (byte)0x5C,
    };

    /** 文字コード: Shift_JIS */
    public static final Charset SHIFT_JIS = Charset.forName("Shift_JIS");

    /** 文字コード: MS932 */
    public static final Charset MS932 = Charset.forName("MS932");

    /** 文字コード: EUC-JP */
    public static final Charset EUC_JP = Charset.forName("EUC-JP");

    /** 文字コード: ISO-2022-JP  */
    public static final Charset ISO_2022_JP = Charset.forName("ISO2022JP");

    /** 文字コード: UTF8 */
    public static final Charset UTF_8 = StandardCharsets.UTF_8;

    /** 全角半角: 数字 */
    private static final Map<String, String> FULL_HALF_NUMBER =
        Maps.newHashMap(new ImmutableMap.Builder<String, String>()
            .put("０", "0")
            .put("１", "1")
            .put("２", "2")
            .put("３", "3")
            .put("４", "4")
            .put("５", "5")
            .put("６", "6")
            .put("７", "7")
            .put("８", "8")
            .put("９", "9")
            .build()
        );

    /** 全角半角: 大文字 */
    private static final Map<String, String> FULL_HALF_UPPER =
        Maps.newHashMap(new ImmutableMap.Builder<String, String>()
            .put("Ａ", "A")
            .put("Ｂ", "B")
            .put("Ｃ", "C")
            .put("Ｄ", "D")
            .put("Ｅ", "E")
            .put("Ｆ", "F")
            .put("Ｇ", "G")
            .put("Ｈ", "H")
            .put("Ｉ", "I")
            .put("Ｊ", "J")
            .put("Ｋ", "K")
            .put("Ｌ", "L")
            .put("Ｍ", "M")
            .put("Ｎ", "N")
            .put("Ｏ", "O")
            .put("Ｐ", "P")
            .put("Ｑ", "Q")
            .put("Ｒ", "R")
            .put("Ｓ", "S")
            .put("Ｔ", "T")
            .put("Ｕ", "U")
            .put("Ｖ", "V")
            .put("Ｗ", "W")
            .put("Ｘ", "X")
            .put("Ｙ", "Y")
            .put("Ｚ", "Z")
            .build()
        );

    /** 全角半角: 小文字 */
    private static final Map<String, String> FULL_HALF_LOWER =
        Maps.newHashMap(new ImmutableMap.Builder<String, String>()
            .put("ａ", "a")
            .put("ｂ", "b")
            .put("ｃ", "c")
            .put("ｄ", "d")
            .put("ｅ", "e")
            .put("ｆ", "f")
            .put("ｇ", "g")
            .put("ｈ", "h")
            .put("ｉ", "i")
            .put("ｊ", "j")
            .put("ｋ", "k")
            .put("ｌ", "l")
            .put("ｍ", "m")
            .put("ｎ", "n")
            .put("ｏ", "o")
            .put("ｐ", "p")
            .put("ｑ", "q")
            .put("ｒ", "r")
            .put("ｓ", "s")
            .put("ｔ", "t")
            .put("ｕ", "u")
            .put("ｖ", "v")
            .put("ｗ", "w")
            .put("ｘ", "x")
            .put("ｙ", "y")
            .put("ｚ", "z")
            .build()
        );

    /** 全角半角: 記号 */
    private static final Map<String, String> FULL_HALF_SYMBOL =
        Maps.newHashMap(new ImmutableMap.Builder<String, String>()
            .put("！", "!")
            .put("”",  "\"")
            .put("＃", "#")
            .put("＄", "$")
            .put("￥", "\\")
            .put("％", "%")
            .put("＆", "&")
            .put("’",  "'")
            .put("（", "(")
            .put("）", ")")
            .put("＊", "*")
            .put("＋", "+")
            .put("，", ",")
            .put("－", "-")
            .put("．", ".")
            .put("／", "/")
            .put("：", ":")
            .put("；", ";")
            .put("＜", "<")
            .put("＝", "=")
            .put("＞", ">")
            .put("？", "?")
            .put("＠", "@")
            .put("＾", "^")
            .put("＿", "_")
            .put("‘",  "`")
            .put("｛", "{")
            .put("｜", "|")
            .put("｝", "}")
            .put("。", "｡")
            .put("「", "｢")
            .put("」", "｣")
            .put("、", "､")
            .put("・", "･")
            .put("　", " ")
            .build()
        );

    private StringUtils() {
        throw new IllegalAccessError("Constants class.");
    }

    /**
     * NULLか空文字かを判断する。
     *
     * @param target 対象文字列
     * @return 判断結果
     * @retval true NULLか空文字である
     * @retval false NULLか空文字でない
     */
    public static boolean isEmpty(final String target) {
        return target == null || target.length() == 0;
    }

    /**
     * 全角スペースか半角スペースかを判断する。
     *
     * @param target 対象文字列
     * @return 判断結果
     * @retval true 全角スペースか半角スペースである
     * @retval false 全角スペースか半角スペースでない
     */
    public static boolean isBlank(final String target) {
        String str = target == null ? null : target.replaceAll("[　 ]", "");
        return isEmpty(str);
    }

    /**
     * ASCIIのみかを判断する。
     *
     * @param target 対象文字列
     * @return 判断結果
     * @retval true ASCIIのみである
     * @retval false ASCIIのみでない
     */
    public static boolean isAscii(final String target) {
        return isEmpty(target) ? false : StandardCharsets.US_ASCII.newEncoder().canEncode(target);
    }

    /**
     * 英字のみかを判断する。
     *
     * @param target 対象文字列
     * @return 判断結果
     * @retval true 英字のみである
     * @retval false 英字のみでない
     */
    public static boolean isAlpha(final String target) {
        return isEmpty(target) ? false : target.matches("^[a-zA-Z]+$");
    }

    /**
     * 半角英数字のみかを判断する。
     *
     * @param target 対象文字列
     * @return 判断結果
     * @retval true 半角英数字のみである
     * @retval false 半角英数字のみでない
     */
    public static boolean isHalfAlphaNumeric(final String target) {
        return isEmpty(target) ? false : target.matches("^[0-9a-zA-Z]+$");
    }

    /**
     * ひらがなのみかを判断する。
     *
     * @param target 対象文字列
     * @return 判断結果
     * @retval true ひらがなのみである
     * @retval false ひらがなのみでない
     */
    public static boolean isHiragana(final String target) {
        return isEmpty(target) ? false : target.matches("^[あいうえおかがきぎくぐけげこごさざしじすずせぜそぞただちぢつづてでとどなにぬねのはばぱひびぴふぶぷへべぺほぼぽまみむめもゃやゅゆょよらりるれろわをん]+$");
    }

    /**
     * カタカナのみかを判断する。
     *
     * @param target 対象文字列
     * @return 判断結果
     * @retval true カタカナのみである
     * @retval false カタカナのみでない
     */
    public static boolean isKatakana(final String target) {
        return isEmpty(target) ? false : target.matches("^[ァアィイゥウェエォオカガキギクグケゲコゴサザシジスズセゼソゾタダチヂツヅテデトドナニヌネノハバパヒビピフブプヘベペホボポマミムメモャヤュユョヨラリルレロワヲンヴ]+$");
    }

    /**
     * 全角文字(数字)を半角文字(数字)に変換する。
     *
     * @param target 対象文字列
     * @return 変換された文字列
     */
    public static String toHalfOfNumber(final String target) {
        return toHalfString(target, FULL_HALF_NUMBER);
    }

    /**
     * 全角文字(英字)を半角文字(英字)に変換する。
     *
     * @param target 対象文字列
     * @return 変換された文字列
     */
    public static String toHalfOfAlpha(final String target) {
        Map<String, String> map = new HashMap<String, String>();
        map.putAll(FULL_HALF_UPPER);
        map.putAll(FULL_HALF_LOWER);
        return toHalfString(target, map);
    }

    /**
     * 全角文字(英字,大文字)を半角文字(英字,大文字)に変換する。
     *
     * @param target 対象文字列
     * @return 変換された文字列
     */
    public static String toHalfOfAlphaUpper(final String target) {
        return toHalfString(target, FULL_HALF_UPPER);
    }

    /**
     * 全角文字(英字,小文字)を半角文字(英字,小文字)に変換する。
     *
     * @param target 対象文字列
     * @return 変換された文字列
     */
    public static String toHalfOfAlphaLower(final String target) {
        return toHalfString(target, FULL_HALF_LOWER);
    }

    /**
     * 全角文字(記号)を半角文字(記号)に変換する。
     *
     * @param target 対象文字列
     * @return 変換された文字列
     */
    public static String toHalfOfSymbol(final String target) {
        return toHalfString(target, FULL_HALF_SYMBOL);
    }

    /**
     * 全角文字(ASCII)を半角文字(ASCII)に変換する。
     *
     * @param target 対象文字列
     * @return 変換された文字列
     */
    public static String toHalfOfAscii(final String target) {
        Map<String, String> map = new HashMap<String, String>();
        map.putAll(FULL_HALF_NUMBER);
        map.putAll(FULL_HALF_UPPER);
        map.putAll(FULL_HALF_LOWER);
        map.putAll(FULL_HALF_SYMBOL);
        return toHalfString(target, map);
    }

    /**
     * 半角文字に変換する。
     *
     * @param target 対象文字列
     * @param convertMap 文字列リストのセット
     * @return 変換された文字列
     */
    private static String toHalfString(final String target, final Map<String, String> convertMap) {
        if (isEmpty(target)) {
            return target;
        }

        String result = target;
        for (Map.Entry<String, String> map : convertMap.entrySet()) {
            result = result.replace(map.getKey(), map.getValue());
        }
        return result;
    }

    /**
     * 半角文字(数字)を全角文字(数字)に変換する。
     *
     * @param target 対象文字列
     * @return 変換された文字列
     */
    public static String toFullOfNumber(final String target) {
        return toFullString(target, FULL_HALF_NUMBER);
    }

    /**
     * 半角文字(英字)を全角文字(英字)に変換する。
     *
     * @param target 対象文字列
     * @return 変換された文字列
     */
    public static String toFullOfAlpha(final String target) {
        Map<String, String> map = new HashMap<String, String>();
        map.putAll(FULL_HALF_UPPER);
        map.putAll(FULL_HALF_LOWER);
        return toFullString(target, map);
    }

    /**
     * 半角文字(英字,大文字)を全角文字(英字,大文字)に変換する。
     *
     * @param target 対象文字列
     * @return 変換された文字列
     */
    public static String toFullOfAlphaUpper(final String target) {
        return toFullString(target, FULL_HALF_UPPER);
    }

    /**
     * 半角文字(英字,小文字)を全角文字(英字,小文字)に変換する。
     *
     * @param target 対象文字列
     * @return 変換された文字列
     */
    public static String toFullOfAlphaLower(final String target) {
        return toFullString(target, FULL_HALF_LOWER);
    }

    /**
     * 半角文字(記号)を全角文字(記号)に変換する。
     *
     * @param target 対象文字列
     * @return 変換された文字列
     */
    public static String toFullOfSymbol(final String target) {
        return toFullString(target, FULL_HALF_SYMBOL);
    }

    /**
     * 半角文字(ASCII)を全角文字(ASCII)に変換する。
     *
     * @param target 対象文字列
     * @return 変換された文字列
     */
    public static String toFullOfAscii(final String target) {
        Map<String, String> map = new HashMap<String, String>();
        map.putAll(FULL_HALF_NUMBER);
        map.putAll(FULL_HALF_UPPER);
        map.putAll(FULL_HALF_LOWER);
        map.putAll(FULL_HALF_SYMBOL);
        return toFullString(target, map);
    }

    /**
     * 全角文字に変換する。
     *
     * @param target 対象文字列
     * @param convertMap 文字列リストのセット
     * @return 変換された文字列
     */
    private static String toFullString(final String target, final Map<String, String> convertMap) {
        if (isEmpty(target)) {
            return target;
        }

        String result = target;
        for (Map.Entry<String, String> map : convertMap.entrySet()) {
            result = result.replace(map.getValue(), map.getKey());
        }
        return result;
    }

    /**
     * 半角スペースで埋める。
     *
     * @param target 対象文字列
     * @param number 埋める個数(0 -> 処理なし, 正 -> 先頭, 負 -> 末尾)
     * @return 半角スペースで埋められた文字列
     */
    public static String fillSpace(final String target, final int number) {
        if (number == 0) {
            return target;
        }
        String result = target == null ? "" : target;
        return String.format("%" + (number + (result.length() * (number > 0 ? 1 : -1))) + "s", result);
    }

    /**
     * ゼロで埋める。
     *
     * @param target 対象文字列
     * @param number 埋める個数(0 -> 処理なし, 正 -> 先頭, 負 -> 末尾)
     * @return ゼロで埋められた文字列
     */
    public static String fillZero(final String target, final int number) {
        String result = fillSpace(target, number);
        return result != null ? result.replace(" ", "0") : result;
    }

    /**
     * 文字列を指定した回数分繰り返す。
     *
     * @param target 対象文字列
     * @param count 繰り返す回数(自然数)
     * @return 繰り返しされた文字列
     */
    public static String repeat(final String target, final int count) {
        if (target == null || count < 1) {
            return null;
        }

        return IntStream.range(0, count).mapToObj(i -> target).collect(Collectors.joining());
    }

    /**
     * 文字コードをチェックする。
     *
     * @param target 対象文字列
     * @param charset チェックする文字コード
     * @return 判断結果
     * @retval true 指定した文字コードである
     * @retval false 指定した文字コードでない
     */
    private static boolean checkCharacterCode(final String target, final Charset charset) {
        if (isEmpty(target)) {
            return true;
        }

        return charset.newEncoder().canEncode(target);
    }

    /**
     * 'MS932'かを判断する。
     *
     * @param target 対象文字列
     * @return 判断結果
     * @retval true 文字コードが'MS932'である
     * @retval false 文字コードが'MS932'でない
     */
    public static boolean isMS932(final String target) {
        return checkCharacterCode(target, MS932);
    }

    /**
     * 'UTF-8'かを判断する。
     *
     * @param target 対象文字列
     * @return 判断結果
     * @retval true 文字コードが'UTF-8'である
     * @retval false 文字コードが'UTF-8'でない
     */
    public static boolean isUTF8(final String target) {
        return checkCharacterCode(target, UTF_8);
    }

    /**
     * 文字列を改行コードで分割し、文字列リストを取得する。
     *
     * @param target 対象文字列
     * @return 行ごとに格納された文字列リスト
     */
    public static List<String> splitReturnCode(final String target) {
        if (isEmpty(target)) {
            return null;
        }

        final String[] strs = target.split("(?:\\r\\n|\\r|\\n)");
        return Arrays.asList(strs).stream().collect(Collectors.toList());
    }

    /**
     * キャメルケースをスネークケースに変換する。
     *
     * @param target 対象文字列
     * @return 変換された文字列
     */
    public static final String camelToSnake(final String target) {
        if (StringUtils.isEmpty(target)) {
            return target;
        }

        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, target);
    }

    /**
     * スネークケースをキャメルケースに変換する。
     *
     * @param target 対象文字列
     * @return 変換された文字列
     */
    public static final String snakeToCamel(final String target) {
        if (StringUtils.isEmpty(target)) {
            return target;
        }

        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, target);
    }

    /**
     * JSONの大文字プロパティ名を、LOWER_CAMEL_CASEに変換する。
     *
     * @param target 対象文字列
     * @return 変換されたJSON文字列
     */
    public static String convertJsonProperty(final String target) {
        if (isEmpty(target)) {
            return target;
        }

        Map<String, Object> map = JsonUtils.toMap(target);
        Map<String, Object> convertedMap = new LinkedHashMap<>();

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            final String key = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, entry.getKey());
            convertedMap.put(key, entry.getValue());
        }

        return JsonUtils.toString(convertedMap);
    }

    /**
     * RFC2045の記号を、RFC4648の記号に変換する。
     *
     * @param target 対象文字列
     * @return 変換された文字列
     */
    public static String convertRFC2045toRFC4648(final String target) {
        if (isEmpty(target)) {
            return target;
        }

        return target.replaceAll("\\+", "-").replaceAll("/", "_");
    }

    /**
     * RFC4648の記号を、RFC2045の記号に変換する。
     *
     * @param target 対象文字列
     * @return 変換された文字列
     */
    public static String convertRFC4648toRFC2045(final String target) {
        if (isEmpty(target)) {
            return target;
        }

        return target.replaceAll("\\-", "+").replaceAll("_", "/");
    }

    /**
     * 暗号・復号化する。
     *
     * @param mode 暗号・復号
     * @param algorithm アルゴリズム
     * @param target 対象文字列
     * @param encryptKey 暗号鍵
     * @param encryptIv 初期化ベクトル
     * @return 変換された文字列
     */
    private static String crypto(final int mode, final String algorithm, final String target, final byte[] encryptKey, final byte[] encryptIv) {
		try {
			SecretKeySpec key = new SecretKeySpec(encryptKey, algorithm);
			IvParameterSpec iv = new IvParameterSpec(encryptIv);

			Cipher cipher = Cipher.getInstance(ENCRYPT_ALGORITHM_MAP.get(algorithm));
			cipher.init(mode, key, iv);

            if (mode == Cipher.ENCRYPT_MODE) {
                byte[] byteText = target.getBytes("UTF-8");
                byte[] byteResult = cipher.doFinal(byteText);
    			return Base64.getUrlEncoder().encodeToString(byteResult);
            } else if (mode == Cipher.DECRYPT_MODE) {
                byte[] byteText = Base64.getUrlDecoder().decode(convertRFC2045toRFC4648(target));
                try {
                    byte[] byteResult = cipher.doFinal(byteText);
                    return new String(byteResult, "UTF-8");
                } catch(IllegalBlockSizeException e) {
                    return target;
                }
            }
		} catch (Exception e) {
            e.printStackTrace();
		}

		return null;
    }

	/**
	 * 文字列を暗号化する。
	 *
	 * @param target 暗号化する文字列
	 * @return 暗号化された文字列
	 */
	public static String encrypt(final String target) {
        return crypto(Cipher.ENCRYPT_MODE, "AES", target, ENCRYPT_KEY, ENCRYPT_IV);
	}

	/**
	 * 文字列を暗号化する。
	 *
	 * @param target 暗号化する文字列
     * @param encryptKey 暗号鍵
     * @param encryptIv 初期化ベクトル
	 * @return 暗号化された文字列
	 */
	public static String encrypt(final String target, final byte[] encryptKey, final byte[] encryptIv) {
        return crypto(Cipher.ENCRYPT_MODE, "AES", target, encryptKey, encryptIv);
	}

	/**
	 * 暗号化された文字列を復号化する。
	 *
	 * @param target 復号化する文字列
	 * @return 復号化された文字列
	 */
	public static String decrypt(final String target) {
        return crypto(Cipher.DECRYPT_MODE, "AES", target, ENCRYPT_KEY, ENCRYPT_IV);
	}

	/**
	 * 暗号化された文字列を復号化する。
	 *
	 * @param target 復号化する文字列
     * @param encryptKey 暗号鍵
     * @param encryptIv 初期化ベクトル
	 * @return 復号化された文字列
	 */
	public static String decrypt(final String target, final byte[] encryptKey, final byte[] encryptIv) {
        return crypto(Cipher.DECRYPT_MODE, "AES", target, encryptKey, encryptIv);
	}
}
