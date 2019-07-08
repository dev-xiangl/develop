package jp.isols.common.utils;

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.TimeZone;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 日付・時間ユーティリティクラス
 */
public class DateTimeUtils {

    @SuppressWarnings("unused")
    private static final Logger logger = LogManager.getLogger(DateTimeUtils.class);

    /** 協定世界時 */
    public static final TimeZone TZ_UTC = TimeZone.getTimeZone("UTC");

    /** 日本標準時 */
    public static final TimeZone TZ_JST = TimeZone.getTimeZone("JST");

    /** 協定世界時のタイムゾーンID */
    public static final ZoneId ZONEID_UTC = ZoneId.of(TZ_UTC.getID(), ZoneId.SHORT_IDS);

    /** 日本標準時のタイムゾーンID */
    public static final ZoneId ZONEID_JST = ZoneId.of(TZ_JST.getID(), ZoneId.SHORT_IDS);

    /** 日付の区切り文字 */
    public static final String DATE_SPLIT = "/";

    /** 最大年月日 */
    public static final String DATE_MAX = "2999" + DATE_SPLIT + "12" + DATE_SPLIT + "31";

    /** 最小年月日 */
    public static final String DATE_MIN = "1970" + DATE_SPLIT + "01" + DATE_SPLIT + "01";

    /** 年月日フォーマット */
    public static final String DATE_FORMAT = "uuuu" + DATE_SPLIT + "[]M" + DATE_SPLIT + "[]d";

    /** 年月日フォーマット */
    public static final String DATE_FORMAT_FIXED = "uuuu" + DATE_SPLIT + "MM" + DATE_SPLIT + "dd";

    /** 時分フォーマット */
    public static final String TIME_FORMAT = "[]H:[]m";

    /** 時分フォーマット */
    public static final String TIME_FORMAT_FIXED = "HH:mm";

    /** 時分秒フォーマット */
    public static final String TIME_FORMAT_FULL = TIME_FORMAT + ":[]s";

    /** 時分秒フォーマット */
    public static final String TIME_FORMAT_FULL_FIXED = TIME_FORMAT_FIXED + ":ss";

    /** 時分秒(マイクロ)フォーマット */
    public static final String TIME_FORMAT_FULLTIME = TIME_FORMAT_FULL + ".[]S";

    /** 年月日時分フォーマット */
    public static final String DATETIME_FORMAT = DATE_FORMAT + " " + TIME_FORMAT;

    /** 年月日時分フォーマット */
    public static final String DATETIME_FORMAT_FIXED = DATE_FORMAT_FIXED + " " + TIME_FORMAT_FIXED;

    /** 年月日時分秒フォーマット */
    public static final String DATETIME_FORMAT_FULL = DATE_FORMAT + " " + TIME_FORMAT_FULL;

    /** 年月日時分秒フォーマット */
    public static final String DATETIME_FORMAT_FULL_FIXED = DATE_FORMAT_FIXED + " " + TIME_FORMAT_FULL_FIXED;

    /** 年月日時分秒(マイクロ)フォーマット */
    public static final String DATETIME_FORMAT_FULLTIME = DATE_FORMAT + " " + TIME_FORMAT_FULLTIME;

    private DateTimeUtils() {
        throw new IllegalAccessError("Constants class.");
    }

    /**
     * 日付フォーマットかを判断する。
     *
     * @param target 対象文字列
     * @param format 判定するフォーマット
     * @return 判断結果
     * @retval true 日付のフォーマット
     * @retval false 日付でないフォーマット
     */
    public static boolean isDateFormat(final String target, final String format) {
        if (StringUtils.isEmpty(target) || StringUtils.isEmpty(format)) {
            return false;
        }

        boolean checkDateResult = false;
        boolean checkDateTimeResult = false;

        try {
             LocalDate.parse(target, DateTimeFormatter.ofPattern(format).withResolverStyle(ResolverStyle.STRICT));
             checkDateResult = true;
        } catch (Exception e) {
             checkDateResult = false;
        }

        try {
             LocalDateTime.parse(target, DateTimeFormatter.ofPattern(format).withResolverStyle(ResolverStyle.STRICT));
             checkDateTimeResult = true;
        } catch (Exception e) {
             checkDateTimeResult = false;
        }

        return checkDateResult == true || checkDateTimeResult == true ? true : false;
    }

    /**
     * 年月日フォーマットかを判断する。
     *
     * @param target 対象文字列
     * @return 判断結果
     * @retval true 年月日のフォーマット
     * @retval false 年月日でないフォーマット
     */
    public static boolean isDateFormat(final String target) {
        return isDateFormat(target, DATE_FORMAT);
    }

    /**
     * 年月日時分フォーマットかを判断する。
     *
     * @param target 対象文字列
     * @return 判断結果
     * @retval true 年月日時分のフォーマット
     * @retval false 年月日時分でないフォーマット
     */
    public static boolean isDateTimeFormat(final String target) {
        return isDateFormat(target, DATETIME_FORMAT);
    }

    /**
     * 今日の日付かを判断する。
     *
     * @param target 対象文字列
     * @return 判断結果
     * @retval true 今日の日付
     * @retval false 今日でない日付
     */
    public static boolean isToday(final String target) {
        LocalDate now = LocalDate.now(ZONEID_JST);

        String str = target.replaceAll(" .*", "");
        if (!isDateFormat(str)) {
            return false;
        }

        LocalDate localDate = parseToLocalDate(str, DATE_FORMAT);
        if (localDate == null) {
            return false;
        }

        return now.isEqual(localDate);
    }

    /**
     * LocalDateに変換する。
     *
     * @param target 対象文字列
     * @param format 変換するフォーマット
     * @return 変換されたLocalDateクラスのインスタンス
     */
    public static LocalDate parseToLocalDate(final String target, final String format) {
        if (StringUtils.isEmpty(target) || StringUtils.isEmpty(format)) {
            return null;
        }

        LocalDate localDate = null;
        try {
             localDate = LocalDate.parse(target, DateTimeFormatter.ofPattern(format).withResolverStyle(ResolverStyle.STRICT));
        } catch (DateTimeParseException e) {
            return null;
        }

        return localDate;
    }

    /**
     * LocalDateに変換する。
     *
     * @param target 対象文字列
     * @return 変換されたLocalDateクラスのインスタンス
     */
    public static LocalDate parseToLocalDate(final String target) {
        return parseToLocalDate(target, DATE_FORMAT);
    }

    /**
     * LocalDateTimeに変換する。
     *
     * @param target 対象文字列
     * @param format 変換するフォーマット
     * @return 変換されたLocalDateTimeクラスのインスタンス
     */
    public static LocalDateTime parseToLocalDateTime(final String target, final String format) {
        if (StringUtils.isEmpty(target) || StringUtils.isEmpty(format)) {
            return null;
        }

        LocalDateTime localDateTime = null;
        try {
             localDateTime = LocalDateTime.parse(target, DateTimeFormatter.ofPattern(format).withResolverStyle(ResolverStyle.STRICT));
        } catch (DateTimeParseException e) {
            return null;
        }

        return localDateTime;
    }

    /**
     * LocalDateTimeに変換する。
     *
     * @param target 対象文字列
     * @return 変換されたLocalDateTimeクラスのインスタンス
     */
    public static LocalDateTime parseToLocalDateTime(final String target) {
        return parseToLocalDateTime(target, DATETIME_FORMAT);
    }

    /**
     * LocalTimeに変換する。
     *
     * @param target 対象文字列
     * @param format 変換するフォーマット
     * @return 変換されたLocalTimeクラスのインスタンス
     */
    public static LocalTime parseToLocalTime(final String target, final String format) {
        if (StringUtils.isEmpty(target) || StringUtils.isEmpty(format)) {
            return null;
        }

        LocalTime localTime = null;
        try {
             localTime = LocalTime.parse(target, DateTimeFormatter.ofPattern(format).withResolverStyle(ResolverStyle.STRICT));
        } catch (DateTimeParseException e) {
            return null;
        }

        return localTime;
    }

    /**
     * LocalTimeに変換する。
     *
     * @param target 対象文字列
     * @return 変換されたLocalTimeクラスのインスタンス
     */
    public static LocalTime parseToLocalTime(final String target) {
        return parseToLocalTime(target, TIME_FORMAT);
    }

    /**
     * Dateに変換する。
     *
     * @param target 対象文字列
     * @param format 変換するフォーマット
     * @return 変換されたDateクラスのインスタンス
     */
    public static Date parseToDate(final String target, final String format) {
        if (StringUtils.isEmpty(target) || StringUtils.isEmpty(format)) {
            return null;
        }

        if (target.matches("^\\d+{4}\\d+{2}\\d+{2}$") || target.matches("^\\d+.\\d+.\\d+$")) {
            LocalDate localDate = parseToLocalDate(target, format);
            return toDate(localDate);
        } else {
            LocalDateTime localDateTime = parseToLocalDateTime(target, format);
            return toDate(localDateTime);
        }
    }

    /**
     * Timestampに変換する。
     *
     * @param target 対象文字列
     * @param format 変換するフォーマット
     * @return 変換されたTimestampクラスのインスタンス
     */
    public static Timestamp parseToTimestamp(final String target, final String format) {
        if (StringUtils.isEmpty(target) || StringUtils.isEmpty(format)) {
            return null;
        }

        LocalDateTime localDateTime = parseToLocalDateTime(target, format);
        return toTimestamp(localDateTime);
    }

    /**
     * LocalDateを、文字列に変換する。
     *
     * @param target 変換対象
     * @param format 変換するフォーマット
     * @return 変換後の文字列(固定長)
     * @note 可変長にする場合は、DATE_FORMATを指定する。
     */
    public static String toString(final LocalDate target, final String format) {
        if (target == null || StringUtils.isEmpty(format)) {
            return null;
        }

        try {
            return target.format(DateTimeFormatter.ofPattern(format).withResolverStyle(ResolverStyle.STRICT));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * LocalDateを、文字列に変換する。
     *
     * @param target 変換対象
     * @return 変換後の文字列(固定長)
     */
    public static String toString(final LocalDate target) {
        if (target == null) {
            return null;
        }

        return toString(target, DATE_FORMAT_FIXED);
    }

    /**
     * LocalDateTimeを、文字列に変換する。
     *
     * @param target 変換対象
     * @param format 変換するフォーマット
     * @return 変換後の文字列(固定長)
     * @note 可変長にする場合は、DATETIME_FORMATを指定する。
     */
    public static String toString(final LocalDateTime target, final String format) {
        if (target == null || StringUtils.isEmpty(format)) {
            return null;
        }

        try {
            return target.format(DateTimeFormatter.ofPattern(format).withResolverStyle(ResolverStyle.STRICT));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * LocalDateTimeを、文字列に変換する。
     *
     * @param target 変換対象
     * @return 変換後の文字列(固定長)
     * @note '年月日時分'フォーマットで変換を行う。
     */
    public static String toString(final LocalDateTime target) {
        if (target == null) {
            return null;
        }

        return toString(target, DATETIME_FORMAT_FIXED);
    }

    /**
     * LocalDateTimeを、文字列に変換する。
     *
     * @param target 変換対象
     * @return 変換後の文字列(固定長)
     * @note '年月日時分秒'フォーマットで変換を行う。
     */
    public static String toFullString(final LocalDateTime target) {
        return toString(target, DATETIME_FORMAT_FULL_FIXED);
    }

    /**
     * 分を、時分の文字列に変換する。
     *
     * @param target 変換対象
     * @return 変換された時分文字列
     */
    public static String toFormatStringByMinute(final long target) {
        if (target < 0) {
            return null;
        }
        return LocalTime.MIN.plus(
                    Duration.ofMinutes(target)
                ).toString();
    }

    /**
     * LocalDateに変換する。
     *
     * @param target 変換対象
     * @return 変換されたLocalDateクラスのインスタンス
     */
    public static LocalDate toLocalDate(final Date target) {
        if (target == null) {
            return null;
        }

        final LocalDateTime localDateTime = toLocalDateTime(target);
        return localDateTime.toLocalDate();
    }

    /**
     * LocalDateに変換する。
     *
     * @param target 変換対象
     * @return 変換されたLocalDateクラスのインスタンス
     */
    public static LocalDate toLocalDate(final Timestamp target) {
        if (target == null) {
            return null;
        }

        final LocalDateTime localDateTime = toLocalDateTime(target);
        return localDateTime.toLocalDate();
    }

    /**
     * LocalDateTimeに変換する。
     *
     * @param target 変換対象
     * @return 変換されたLocalDateTimeクラスのインスタンス
     */
    public static LocalDateTime toLocalDateTime(final LocalDate target) {
        if (target == null) {
            return null;
        }

        return target.atTime(LocalTime.of(00, 00, 00));
    }

    /**
     * LocalDateTimeに変換する。
     *
     * @param target 変換対象
     * @return 変換されたLocalDateTimeクラスのインスタンス
     */
    public static LocalDateTime toLocalDateTime(final Date target) {
        if (target == null) {
            return null;
        }

        Instant instant = target.toInstant();
        return LocalDateTime.ofInstant(instant, ZONEID_JST);
    }

    /**
     * LocalDateTimeに変換する。
     *
     * @param target 変換対象
     * @return 変換されたLocalDateTimeクラスのインスタンス
     */
    public static LocalDateTime toLocalDateTime(final Timestamp target) {
        if (target == null) {
            return null;
        }

        Instant instant = target.toInstant();
        return LocalDateTime.ofInstant(instant, ZONEID_JST);
    }

    /**
     * Dateに変換する。
     *
     * @param target 変換対象
     * @return 変換されたDateクラスのインスタンス
     */
    public static Date toDate(final LocalDateTime target) {
        if (target == null) {
            return null;
        }

        ZonedDateTime zdt = target.atZone(ZONEID_JST);
        return Date.from(zdt.toInstant());
    }

    /**
     * Dateに変換する。
     *
     * @param target 変換対象
     * @return 変換されたDateクラスのインスタンス
     */
    public static Date toDate(final LocalDate target) {
        if (target == null) {
            return null;
        }

        ZonedDateTime zdt = target.atStartOfDay(ZONEID_JST);
        return Date.from(zdt.toInstant());
    }

    /**
     * Timestampに変換する。
     *
     * @param target 変換対象
     * @return 変換されたTimestampクラスのインスタンス
     */
    public static Timestamp toTimestamp(final LocalDateTime target) {
        if (target == null) {
            return null;
        }

        return Timestamp.valueOf(target);
    }

    /**
     * 月末日を取得する。
     *
     * @param target 取得対象
     * @return 月末日を格納したLocalDateクラスのインスタンス
     */
    public static LocalDate getLastDayOfMonth(final LocalDate target) {
        if (target == null) {
            return null;
        }

        return target.withDayOfMonth(1).with(TemporalAdjusters.lastDayOfMonth());
    }

    /**
     * 曜日を取得する。
     *
     * @param target 取得対象
     * @return 曜日
     */
    public static String getDayOfWeek(final LocalDate target) {
        if (target == null) {
            return null;
        }

        final DayOfWeek dayOfWeek = target.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.MONDAY) {
            return "月";
        } else if (dayOfWeek == DayOfWeek.TUESDAY) {
            return "火";
        } else if (dayOfWeek == DayOfWeek.WEDNESDAY) {
            return "水";
        } else if (dayOfWeek == DayOfWeek.THURSDAY) {
            return "木";
        } else if (dayOfWeek == DayOfWeek.FRIDAY) {
            return "金";
        } else if (dayOfWeek == DayOfWeek.SATURDAY) {
            return "土";
        } else if (dayOfWeek == DayOfWeek.SUNDAY) {
            return "日";
        }

        return null;
    }
}
