package jp.isols.common.utils;

import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 数値ユーティリティクラス
 */
public class NumberUtils {

    private NumberUtils() {
        throw new IllegalAccessError("Constants class.");
    }

    /**
     * 整数、または、小数かを判断する。
     *
     * @param target 対象文字列
     * @return 判断結果
     * @retval true 整数、または、小数である
     * @retval false 整数・小数でない
     */
    public static boolean isNumber(final String target) {
        return isInteger(target) || isDecimal(target) ? true : false;
    }

    /**
     * 整数のみかを判断する。
     *
     * @param target 対象文字列
     * @return 判断結果
     * @retval true 整数である
     * @retval false 整数でない
     */
    public static boolean isInteger(final String target) {
        return StringUtils.isEmpty(target) ? false : target.replaceAll(",", "").matches("^[\\-\\+]?[0-9]+$");
    }

    /**
     * 小数のみかを判断する。
     *
     * @param target 対象文字列
     * @return 判断結果
     * @retval true 小数である
     * @retval false 小数でない
     */
    public static boolean isDecimal(final String target) {
        return StringUtils.isEmpty(target) ? false : target.replaceAll(",", "").matches("^[\\-\\+]?[0-9]+\\.[0-9]+$");
    }

    /**
     * 桁数を取得する。
     *
     * @param target 対象
     * @return 桁数
     */
    public static int getDigitLength(final BigInteger value) {
        final int length = String.valueOf(value).replaceAll(",", "").length();
        if (BigInteger.ZERO.compareTo(value) == 1) {
            return length - 1;
        }
        return length;
    }

    /**
     * 桁数を取得する。
     *
     * @param target 対象
     * @return 桁数
     */
    public static int getDigitLength(final Integer value) {
        return getDigitLength(new BigInteger(String.valueOf(value)));
    }

    /**
     * 最大値を取得する。
     *
     * @param target 対象リスト
     * @return 対象リストの最大値のみ
     */
    public static BigInteger max(BigInteger ... args) {
    	if (args == null || args.length == 0) {
    		return null;
    	}
        BigInteger maxValue = args[0];
        for (final BigInteger value : args) {
        	maxValue = value.max(maxValue);
        }
        return maxValue;
    }

	/**
     * 最大値を取得する。
     *
     * @param target 対象リスト
     * @return 対象リストの最大値のみ
     */
    public static Integer max(Integer ... args) {
    	if (args == null || args.length == 0) {
    		return null;
    	}
    	return Arrays.asList(args).stream().collect(Collectors.reducing(Integer::max)).get();
    }

    /**
     * 最小値を取得する。
     *
     * @param target 対象リスト
     * @return 対象リストの最小値のみ
     */
    public static BigInteger min(BigInteger ... args) {
    	if (args == null || args.length == 0) {
    		return null;
    	}
        BigInteger minValue = args[0];
        for (final BigInteger value : args) {
        	minValue = value.min(minValue);
        }
        return minValue;
    }

	/**
     * 最小値を取得する。
     *
     * @param target 対象リスト
     * @return 対象リストの最小値のみ
     */
    public static Integer min(Integer ... args) {
    	if (args == null || args.length == 0) {
    		return null;
    	}
    	return Arrays.asList(args).stream().collect(Collectors.reducing(Integer::min)).get();
    }

    /**
     * 3桁区切りの文字列を取得する。
     *
     * @param target 対象文字列
     * @return 3桁区切りに変換した文字列
     */
    public static String toFormatString(final BigInteger value) {
        NumberFormat nf = NumberFormat.getInstance();
        return nf.format(value);
    }

    /**
     * 3桁区切りの文字列を取得する。
     *
     * @param target 対象文字列
     * @return 3桁区切りに変換した文字列
     */
    public static String toFormatString(final Integer value) {
        return toFormatString(new BigInteger(String.valueOf(value)));
    }
}
