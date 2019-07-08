package jp.isols.common.utils;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DateTimeUtilsTest {

    @BeforeEach
    public void beforeEach() {
    }

    @Test
    public void isDateFormatTest() throws Exception {
        assertEquals(DateTimeUtils.isDateFormat("2000/1/2"), true);
        assertEquals(DateTimeUtils.isDateFormat("2000/11/22"), true);
        assertEquals(DateTimeUtils.isDateFormat("yyyy/mm/dd"), false);

        assertEquals(DateTimeUtils.isDateFormat("2000/11/22 0:0:0", DateTimeUtils.DATETIME_FORMAT_FULL), true);
        assertEquals(DateTimeUtils.isDateFormat("2000/11/22 00:00:00", DateTimeUtils.DATETIME_FORMAT_FULL), true);
        assertEquals(DateTimeUtils.isDateFormat("2000/11/22 1:2:3", DateTimeUtils.DATETIME_FORMAT_FULL), true);
        assertEquals(DateTimeUtils.isDateFormat("2000/11/22 12:34:56", DateTimeUtils.DATETIME_FORMAT_FULL), true);
        assertEquals(DateTimeUtils.isDateFormat("2000/11/22 23:59:59", DateTimeUtils.DATETIME_FORMAT_FULL), true);
        assertEquals(DateTimeUtils.isDateFormat("2000/11/22 24:00:00", DateTimeUtils.DATETIME_FORMAT_FULL), false);
        assertEquals(DateTimeUtils.isDateFormat("yyyy/mm/dd hh:mm:ss", DateTimeUtils.DATETIME_FORMAT_FULL), false);
    }

    @Test
    public void isDateTimeFormatTest() throws Exception {
        assertEquals(DateTimeUtils.isDateTimeFormat("2000/1/2"), false);
        assertEquals(DateTimeUtils.isDateTimeFormat("2000/11/22"), false);
        assertEquals(DateTimeUtils.isDateTimeFormat("yyyy/mm/dd"), false);

        assertEquals(DateTimeUtils.isDateTimeFormat("2000/11/22 12:34"), true);
        assertEquals(DateTimeUtils.isDateTimeFormat("yyyy/mm/dd hh:mm"), false);
    }

    @Test
    public void isTodayTest() throws Exception {
        LocalDate nowDate = LocalDate.now();
        LocalDateTime nowDateTime = LocalDateTime.now();

        assertEquals(DateTimeUtils.isToday(DateTimeUtils.toString(nowDate)), true);
        assertEquals(DateTimeUtils.isToday(nowDate.toString()), false);
        assertEquals(DateTimeUtils.isToday("yyyy/mm/dd"), false);

        assertEquals(DateTimeUtils.isToday(DateTimeUtils.toString(nowDateTime)), true);
        assertEquals(DateTimeUtils.isToday(nowDateTime.toString()), false);
        assertEquals(DateTimeUtils.isToday("yyyy/mm/dd hh:mm:ss"), false);
    }

    @Test
    public void parseToLocalDateTest() throws Exception {
        { /* null */
            Object obj = DateTimeUtils.parseToLocalDate(null);
            assertNull(obj);
        }

        { /* 空文字 */
            Object obj = DateTimeUtils.parseToLocalDate("");
            assertNull(obj);
        }

        { /* 形式不正 */
            Object obj = DateTimeUtils.parseToLocalDate("2000///11/22");
            assertNull(obj);
        }

        { /* 存在しない日付(月) */
            Object obj = DateTimeUtils.parseToLocalDate("2000/19/22");
            assertNull(obj);
        }

        { /* 存在しない日付(日) */
            Object obj = DateTimeUtils.parseToLocalDate("2000/11/99");
            assertNull(obj);
        }

        /* 共通変数 */
        Object obj = null;

        obj = DateTimeUtils.parseToLocalDate("2000/1/2");
        assertNotNull(obj);
        assertTrue(obj instanceof LocalDate);

        obj = DateTimeUtils.parseToLocalDate("2000/01/02");
        assertNotNull(obj);
        assertTrue(obj instanceof LocalDate);

        obj = DateTimeUtils.parseToLocalDate("2000/11/22");
        assertNotNull(obj);
        assertTrue(obj instanceof LocalDate);

        obj = DateTimeUtils.parseToLocalDate("2000/11/22", "uuuu/MM/dd");
        assertNotNull(obj);
        assertTrue(obj instanceof LocalDate);

        obj = DateTimeUtils.parseToLocalDate("2000-11-22", "uuuu-MM-dd");
        assertNotNull(obj);
        assertTrue(obj instanceof LocalDate);
    }

    @Test
    public void parseToLocalDateTimeTest() throws Exception {
        { /* null */
            Object obj = DateTimeUtils.parseToLocalDateTime(null);
            assertNull(obj);
        }

        { /* 空文字 */
            Object obj = DateTimeUtils.parseToLocalDateTime("");
            assertNull(obj);
        }

        { /* 年月日のみ */
            Object obj = DateTimeUtils.parseToLocalDateTime("2000/11/22");
            assertNull(obj);
        }

        { /* 形式不正 */
            Object obj = DateTimeUtils.parseToLocalDateTime("2000///11/22");
            assertNull(obj);
        }

        { /* 形式不正 */
            Object obj = DateTimeUtils.parseToLocalDateTime("2000/11/22 12:::34");
            assertNull(obj);
        }

        { /* 存在しない日付(月) */
            Object obj = DateTimeUtils.parseToLocalDateTime("2000/19/22");
            assertNull(obj);
        }

        { /* 存在しない日付(日) */
            Object obj = DateTimeUtils.parseToLocalDateTime("2000/11/99");
            assertNull(obj);
        }

        { /* 存在しない時間 */
            Object obj = DateTimeUtils.parseToLocalDateTime("2000/11/22 24:00");
            assertNull(obj);
        }

        { /* 存在しない時間(時) */
            Object obj = DateTimeUtils.parseToLocalDateTime("2000/11/22 99:00");
            assertNull(obj);
        }

        { /* 存在しない時間(分) */
            Object obj = DateTimeUtils.parseToLocalDateTime("2000/11/22 11:99");
            assertNull(obj);
        }

        /* 共通変数 */
        Object obj = null;

        obj = DateTimeUtils.parseToLocalDateTime("2000/1/2 0:0");
        assertNotNull(obj);
        assertTrue(obj instanceof LocalDateTime);

        obj = DateTimeUtils.parseToLocalDateTime("2000/1/2 00:00");
        assertNotNull(obj);
        assertTrue(obj instanceof LocalDateTime);

        obj = DateTimeUtils.parseToLocalDateTime("2000/1/2 1:1");
        assertNotNull(obj);
        assertTrue(obj instanceof LocalDateTime);

        obj = DateTimeUtils.parseToLocalDateTime("2000/01/02 01:01");
        assertNotNull(obj);
        assertTrue(obj instanceof LocalDateTime);

        obj = DateTimeUtils.parseToLocalDateTime("2000/11/22 12:34");
        assertNotNull(obj);
        assertTrue(obj instanceof LocalDateTime);

        obj = DateTimeUtils.parseToLocalDateTime("2000/11/22 23:59");
        assertNotNull(obj);
        assertTrue(obj instanceof LocalDateTime);

        obj = DateTimeUtils.parseToLocalDateTime("2000/11/22 0:0", "uuuu/MM/dd H:m");
        assertNotNull(obj);
        assertTrue(obj instanceof LocalDateTime);

        obj = DateTimeUtils.parseToLocalDateTime("2000/11/22 1:1", "uuuu/MM/dd H:m");
        assertNotNull(obj);
        assertTrue(obj instanceof LocalDateTime);

        obj = DateTimeUtils.parseToLocalDateTime("2000-11-22 1:1", "uuuu-MM-dd H:m");
        assertNotNull(obj);
        assertTrue(obj instanceof LocalDateTime);

        obj = DateTimeUtils.parseToLocalDateTime("2000-11-22 23:59", "uuuu-MM-dd HH:mm");
        assertNotNull(obj);
        assertTrue(obj instanceof LocalDateTime);
    }

    @Test
    public void parseToLocalTimeTest() throws Exception {
        { /* null */
            Object obj = DateTimeUtils.parseToLocalTime(null);
            assertNull(obj);
        }

        { /* 空文字 */
            Object obj = DateTimeUtils.parseToLocalTime("");
            assertNull(obj);
        }

        { /* 年月日のみ */
            Object obj = DateTimeUtils.parseToLocalTime("2000/11/22");
            assertNull(obj);
        }

        { /* 年月日を含む */
            Object obj = DateTimeUtils.parseToLocalTime("2000/11/22 12:34");
            assertNull(obj);
        }

        { /* 形式不正 */
            Object obj = DateTimeUtils.parseToLocalTime("12:::34");
            assertNull(obj);
        }

        { /* 存在しない時間 */
            Object obj = DateTimeUtils.parseToLocalTime("24:00");
            assertNull(obj);
        }

        { /* 存在しない時間(時) */
            Object obj = DateTimeUtils.parseToLocalTime("99:00");
            assertNull(obj);
        }

        { /* 存在しない時間(分) */
            Object obj = DateTimeUtils.parseToLocalTime("11:99");
            assertNull(obj);
        }

        /* 共通変数 */
        Object obj = null;

        obj = DateTimeUtils.parseToLocalTime("0:0");
        assertNotNull(obj);
        assertTrue(obj instanceof LocalTime);

        obj = DateTimeUtils.parseToLocalTime("00:00");
        assertNotNull(obj);
        assertTrue(obj instanceof LocalTime);

        obj = DateTimeUtils.parseToLocalTime("1:1");
        assertNotNull(obj);
        assertTrue(obj instanceof LocalTime);

        obj = DateTimeUtils.parseToLocalTime("01:01");
        assertNotNull(obj);
        assertTrue(obj instanceof LocalTime);

        obj = DateTimeUtils.parseToLocalTime("12:34");
        assertNotNull(obj);
        assertTrue(obj instanceof LocalTime);

        obj = DateTimeUtils.parseToLocalTime("23:59");
        assertNotNull(obj);
        assertTrue(obj instanceof LocalTime);

        obj = DateTimeUtils.parseToLocalTime("0:0", "H:m");
        assertNotNull(obj);
        assertTrue(obj instanceof LocalTime);

        obj = DateTimeUtils.parseToLocalTime("1:1", "H:m");
        assertNotNull(obj);
        assertTrue(obj instanceof LocalTime);

        obj = DateTimeUtils.parseToLocalTime("00:00", "HH:mm");
        assertNotNull(obj);
        assertTrue(obj instanceof LocalTime);

        obj = DateTimeUtils.parseToLocalTime("23:59", "HH:mm");
        assertNotNull(obj);
        assertTrue(obj instanceof LocalTime);
    }

    @Test
    public void parseToDateTest() throws Exception {
        { /* null */
            Object obj = DateTimeUtils.parseToDate(null, null);
            assertNull(obj);
        }

        { /* 空文字 */
            Object obj = DateTimeUtils.parseToDate("", "");
            assertNull(obj);
        }

        { /* 形式不正 */
            Object obj = DateTimeUtils.parseToDate("2000///11/22", DateTimeUtils.DATE_FORMAT);
            assertNull(obj);
        }

        { /* 存在しない日付(月) */
            Object obj = DateTimeUtils.parseToDate("2000/19/22", DateTimeUtils.DATE_FORMAT);
            assertNull(obj);
        }

        { /* 存在しない日付(日) */
            Object obj = DateTimeUtils.parseToDate("2000/11/99", DateTimeUtils.DATE_FORMAT);
            assertNull(obj);
        }

        { /* 形式不正 */
            Object obj = DateTimeUtils.parseToDate("2000/11/22 12:::34", DateTimeUtils.DATETIME_FORMAT);
            assertNull(obj);
        }

        { /* 存在しない時間 */
            Object obj = DateTimeUtils.parseToDate("2000/11/22 24:00", DateTimeUtils.DATETIME_FORMAT);
            assertNull(obj);
        }

        { /* 存在しない時間(時) */
            Object obj = DateTimeUtils.parseToDate("2000/11/22 99:00", DateTimeUtils.DATETIME_FORMAT);
            assertNull(obj);
        }

        { /* 存在しない時間(分) */
            Object obj = DateTimeUtils.parseToDate("2000/11/22 11:99", DateTimeUtils.DATETIME_FORMAT);
            assertNull(obj);
        }

        /* 共通変数 */
        Object obj = null;

        obj = DateTimeUtils.parseToDate("2000/1/2", DateTimeUtils.DATE_FORMAT);;
        assertNotNull(obj);
        assertTrue(obj instanceof Date);

        obj = DateTimeUtils.parseToDate("2000/01/02", DateTimeUtils.DATE_FORMAT);
        assertNotNull(obj);
        assertTrue(obj instanceof Date);

        obj = DateTimeUtils.parseToDate("2000/11/22", DateTimeUtils.DATE_FORMAT);
        assertNotNull(obj);
        assertTrue(obj instanceof Date);

        obj = DateTimeUtils.parseToDate("2000/1/2 0:0", DateTimeUtils.DATETIME_FORMAT);
        assertNotNull(obj);
        assertTrue(obj instanceof Date);

        obj = DateTimeUtils.parseToDate("2000/1/2 00:00", DateTimeUtils.DATETIME_FORMAT);
        assertNotNull(obj);
        assertTrue(obj instanceof Date);

        obj = DateTimeUtils.parseToDate("2000/1/2 1:1", DateTimeUtils.DATETIME_FORMAT);
        assertNotNull(obj);
        assertTrue(obj instanceof Date);

        obj = DateTimeUtils.parseToDate("2000/01/02 01:01", DateTimeUtils.DATETIME_FORMAT);
        assertNotNull(obj);
        assertTrue(obj instanceof Date);

        obj = DateTimeUtils.parseToDate("2000/11/22 12:34", DateTimeUtils.DATETIME_FORMAT);
        assertNotNull(obj);
        assertTrue(obj instanceof Date);

        obj = DateTimeUtils.parseToDate("2000/11/22 23:59", DateTimeUtils.DATETIME_FORMAT);
        assertNotNull(obj);
        assertTrue(obj instanceof Date);
    }

    @Test
    public void parseToTimestampTest() throws Exception {
        { /* null */
            Object obj = DateTimeUtils.parseToTimestamp(null, null);
            assertNull(obj);
        }

        { /* 空文字 */
            Object obj = DateTimeUtils.parseToTimestamp("", "");
            assertNull(obj);
        }

        { /* 年月日のみ */
            Object obj = DateTimeUtils.parseToLocalTime("2000/11/22");
            assertNull(obj);
        }

        { /* 形式不正 */
            Object obj = DateTimeUtils.parseToTimestamp("2000///11/22", DateTimeUtils.DATE_FORMAT);
            assertNull(obj);
        }

        { /* 存在しない日付(月) */
            Object obj = DateTimeUtils.parseToTimestamp("2000/19/22", DateTimeUtils.DATE_FORMAT);
            assertNull(obj);
        }

        { /* 存在しない日付(日) */
            Object obj = DateTimeUtils.parseToTimestamp("2000/11/99", DateTimeUtils.DATE_FORMAT);
            assertNull(obj);
        }

        { /* 形式不正 */
            Object obj = DateTimeUtils.parseToTimestamp("2000/11/22 12:::34", DateTimeUtils.DATETIME_FORMAT);
            assertNull(obj);
        }

        { /* 存在しない時間 */
            Object obj = DateTimeUtils.parseToTimestamp("2000/11/22 24:00", DateTimeUtils.DATETIME_FORMAT);
            assertNull(obj);
        }

        { /* 存在しない時間(時) */
            Object obj = DateTimeUtils.parseToTimestamp("2000/11/22 99:00", DateTimeUtils.DATETIME_FORMAT);
            assertNull(obj);
        }

        { /* 存在しない時間(分) */
            Object obj = DateTimeUtils.parseToTimestamp("2000/11/22 11:99", DateTimeUtils.DATETIME_FORMAT);
            assertNull(obj);
        }

        /* 共通変数 */
        Object obj = null;

        obj = DateTimeUtils.parseToTimestamp("2000/1/2 0:0", DateTimeUtils.DATETIME_FORMAT);
        assertNotNull(obj);
        assertTrue(obj instanceof Timestamp);

        obj = DateTimeUtils.parseToTimestamp("2000/1/2 00:00", DateTimeUtils.DATETIME_FORMAT);
        assertNotNull(obj);
        assertTrue(obj instanceof Timestamp);

        obj = DateTimeUtils.parseToTimestamp("2000/1/2 1:1", DateTimeUtils.DATETIME_FORMAT);
        assertNotNull(obj);
        assertTrue(obj instanceof Timestamp);

        obj = DateTimeUtils.parseToTimestamp("2000/01/02 01:01", DateTimeUtils.DATETIME_FORMAT);
        assertNotNull(obj);
        assertTrue(obj instanceof Timestamp);

        obj = DateTimeUtils.parseToTimestamp("2000/11/22 12:34", DateTimeUtils.DATETIME_FORMAT);
        assertNotNull(obj);
        assertTrue(obj instanceof Timestamp);

        obj = DateTimeUtils.parseToTimestamp("2000/1/2 23:59", DateTimeUtils.DATETIME_FORMAT);
        assertNotNull(obj);
        assertTrue(obj instanceof Timestamp);
    }

    @Test
    public void toStringTest() throws Exception {

		{ /* LocalDate */
			LocalDate target;

			/* null */
			target = null;
			assertNull(DateTimeUtils.toString(target, DateTimeUtils.DATE_FORMAT));
			assertNull(DateTimeUtils.toString(target));

			/* フォーマット不正 */
			target = DateTimeUtils.parseToLocalDate("2000/01/02");
			assertNotNull(target);
			assertNull(DateTimeUtils.toString(target, DateTimeUtils.DATETIME_FORMAT));

			/* フォーマット不正 */
			target = DateTimeUtils.parseToLocalDate("2000/01/02");
			assertNotNull(target);
			assertNull(DateTimeUtils.toString(target, DateTimeUtils.TIME_FORMAT));

			target = DateTimeUtils.parseToLocalDate("2000/1/2");
			assertNotNull(target);
			assertEquals(DateTimeUtils.toString(target, DateTimeUtils.DATE_FORMAT), "2000/1/2");

			target = DateTimeUtils.parseToLocalDate("2000/1/2");
			assertNotNull(target);
			assertEquals(DateTimeUtils.toString(target, DateTimeUtils.DATE_FORMAT_FIXED), "2000/01/02");

			target = DateTimeUtils.parseToLocalDate("2000/01/02");
			assertNotNull(target);
			assertEquals(DateTimeUtils.toString(target, DateTimeUtils.DATE_FORMAT), "2000/1/2");

			target = DateTimeUtils.parseToLocalDate("2000/01/02");
			assertNotNull(target);
			assertEquals(DateTimeUtils.toString(target, DateTimeUtils.DATE_FORMAT_FIXED), "2000/01/02");

			target = DateTimeUtils.parseToLocalDate("2000/1/2");
			assertNotNull(target);
			assertEquals(DateTimeUtils.toString(target), "2000/01/02");

			target = DateTimeUtils.parseToLocalDate("2000/01/02");
			assertNotNull(target);
			assertEquals(DateTimeUtils.toString(target), "2000/01/02");

			target = DateTimeUtils.parseToLocalDate("2000/11/22");
			assertNotNull(target);
			assertEquals(DateTimeUtils.toString(target), "2000/11/22");
		}

		{ /* LocalDateTime */
			LocalDateTime target;

			/* null */
			target = null;
			assertNull(DateTimeUtils.toString(target, DateTimeUtils.DATETIME_FORMAT_FULL));
			assertNull(DateTimeUtils.toString(target));

			target = DateTimeUtils.parseToLocalDateTime("2000/1/2 12:34");
			assertNotNull(target);
			assertEquals(DateTimeUtils.toString(target, DateTimeUtils.DATE_FORMAT), "2000/1/2");

			target = DateTimeUtils.parseToLocalDateTime("2000/1/2 12:34");
			assertNotNull(target);
			assertEquals(DateTimeUtils.toString(target, DateTimeUtils.DATE_FORMAT_FIXED), "2000/01/02");

			target = DateTimeUtils.parseToLocalDateTime("2000/1/2 3:4");
			assertNotNull(target);
			assertEquals(DateTimeUtils.toString(target, DateTimeUtils.DATETIME_FORMAT), "2000/1/2 3:4");

			target = DateTimeUtils.parseToLocalDateTime("2000/1/2 3:4");
			assertNotNull(target);
			assertEquals(DateTimeUtils.toString(target, DateTimeUtils.DATETIME_FORMAT_FIXED), "2000/01/02 03:04");

			target = DateTimeUtils.parseToLocalDateTime("2000/1/2 3:4");
			assertNotNull(target);
			assertEquals(DateTimeUtils.toString(target, DateTimeUtils.TIME_FORMAT), "3:4");

			target = DateTimeUtils.parseToLocalDateTime("2000/1/2 3:4");
			assertNotNull(target);
			assertEquals(DateTimeUtils.toString(target, DateTimeUtils.TIME_FORMAT_FIXED), "03:04");

			target = DateTimeUtils.parseToLocalDateTime("2000/1/2 12:34");
			assertNotNull(target);
			assertEquals(DateTimeUtils.toString(target), "2000/01/02 12:34");

			target = DateTimeUtils.parseToLocalDateTime("2000/1/2 12:34");
			assertNotNull(target);
			assertEquals(DateTimeUtils.toString(target), "2000/01/02 12:34");

			target = DateTimeUtils.parseToLocalDateTime("2000/1/2 3:4");
			assertNotNull(target);
			assertEquals(DateTimeUtils.toString(target), "2000/01/02 03:04");

			target = DateTimeUtils.parseToLocalDateTime("2000/11/22 12:34");
			assertNotNull(target);
			assertEquals(DateTimeUtils.toString(target), "2000/11/22 12:34");
		}
    }

    @Test
    public void toFullStringTest() throws Exception {
		LocalDateTime target;

		/* null */
		target = null;
		assertNull(DateTimeUtils.toFullString(target));

		target = DateTimeUtils.parseToLocalDateTime("2000/1/2 12:34");
		assertNotNull(target);
		assertEquals(DateTimeUtils.toFullString(target), "2000/01/02 12:34:00");

		target = DateTimeUtils.parseToLocalDateTime("2000/1/2 12:34:56", DateTimeUtils.DATETIME_FORMAT_FULL);
		assertNotNull(target);
		assertEquals(DateTimeUtils.toFullString(target), "2000/01/02 12:34:56");

		target = DateTimeUtils.parseToLocalDateTime("2000/1/2 3:4:5", DateTimeUtils.DATETIME_FORMAT_FULL);
		assertNotNull(target);
		assertEquals(DateTimeUtils.toFullString(target), "2000/01/02 03:04:05");

		target = DateTimeUtils.parseToLocalDateTime("2000/11/22 12:34:56", DateTimeUtils.DATETIME_FORMAT_FULL);
		assertNotNull(target);
		assertEquals(DateTimeUtils.toFullString(target), "2000/11/22 12:34:56");
	}

    @Test
    public void toFormatStringByMinuteTest() throws Exception {
        assertEquals(DateTimeUtils.toFormatStringByMinute(-1), null);

        assertEquals(DateTimeUtils.toFormatStringByMinute(0), "00:00");
        assertEquals(DateTimeUtils.toFormatStringByMinute(1), "00:01");
        assertEquals(DateTimeUtils.toFormatStringByMinute(59), "00:59");
        assertEquals(DateTimeUtils.toFormatStringByMinute(60), "01:00");
        assertEquals(DateTimeUtils.toFormatStringByMinute(720), "12:00");
        assertEquals(DateTimeUtils.toFormatStringByMinute(1080), "18:00");

        assertEquals(DateTimeUtils.toFormatStringByMinute(1440), "00:00");
        assertEquals(DateTimeUtils.toFormatStringByMinute(1500), "01:00");

        assertEquals(DateTimeUtils.toFormatStringByMinute(2880), "00:00");
        assertEquals(DateTimeUtils.toFormatStringByMinute(3000), "02:00");
    }

    @Test
    public void toLocalDateTest() throws Exception {
        Object obj;

        /* null */
        obj = DateTimeUtils.toLocalDate(null);
        assertNull(obj);

        obj = DateTimeUtils.toLocalDate(new Date(0));
        assertNotNull(obj);
        assertTrue(obj instanceof LocalDate);

        obj = DateTimeUtils.toLocalDate(new Timestamp(0));
        assertNotNull(obj);
        assertTrue(obj instanceof LocalDate);
    }

    @Test
    public void toLocalDateTimeTest() throws Exception {
        { /* LocalDate */
            LocalDate target;
            LocalDateTime actual;

            /* null */
            target = null;
            actual = DateTimeUtils.toLocalDateTime(target);
            assertNull(actual);

            target = DateTimeUtils.parseToLocalDate("2000/01/02");
            actual = DateTimeUtils.toLocalDateTime(target);
            assertNotNull(actual);
            assertEquals(actual.getYear(), 2000);
            assertEquals(actual.getMonthValue(), 1);
            assertEquals(actual.getDayOfMonth(), 2);
            assertEquals(actual.getHour(), 0);
            assertEquals(actual.getMinute(), 0);
            assertEquals(actual.getSecond(), 0);
        }

        { /* Date */
            Date target;
            LocalDateTime actual;

            /* null */
            target = null;
            actual = DateTimeUtils.toLocalDateTime(target);
            assertNull(actual);

            target = DateTimeUtils.parseToDate("2000/01/02", DateTimeUtils.DATE_FORMAT);
            actual = DateTimeUtils.toLocalDateTime(target);
            assertNotNull(actual);
            assertEquals(actual.getYear(), 2000);
            assertEquals(actual.getMonthValue(), 1);
            assertEquals(actual.getDayOfMonth(), 2);
            assertEquals(actual.getHour(), 0);
            assertEquals(actual.getMinute(), 0);
            assertEquals(actual.getSecond(), 0);

            target = DateTimeUtils.parseToDate("2000/01/02 12:34:56", DateTimeUtils.DATETIME_FORMAT_FULL);
            actual = DateTimeUtils.toLocalDateTime(target);
            assertNotNull(actual);
            assertEquals(actual.getYear(), 2000);
            assertEquals(actual.getMonthValue(), 1);
            assertEquals(actual.getDayOfMonth(), 2);
            assertEquals(actual.getHour(), 12);
            assertEquals(actual.getMinute(), 34);
            assertEquals(actual.getSecond(), 56);
        }

        { /* Timestamp */
            Timestamp target;
            LocalDateTime actual;

            /* null */
            target = null;
            actual = DateTimeUtils.toLocalDateTime(target);
            assertNull(actual);

            target = DateTimeUtils.parseToTimestamp("2000/01/02 12:34:56", DateTimeUtils.DATETIME_FORMAT_FULL);
            actual = DateTimeUtils.toLocalDateTime(target);
            assertNotNull(actual);
            assertEquals(actual.getYear(), 2000);
            assertEquals(actual.getMonthValue(), 1);
            assertEquals(actual.getDayOfMonth(), 2);
            assertEquals(actual.getHour(), 12);
            assertEquals(actual.getMinute(), 34);
            assertEquals(actual.getSecond(), 56);
        }
    }

    @Test
    public void toDateTest() throws Exception {
        { /* LocalDate */
            LocalDate target;
            Date actual;

            /* null */
            target = null;
            actual = DateTimeUtils.toDate(target);
            assertNull(actual);

            target = DateTimeUtils.parseToLocalDate("2000/01/02");
            actual = DateTimeUtils.toDate(target);
            assertNotNull(actual);
        }

        { /* LocalDateTime */
            LocalDateTime target;
            Date actual;

            /* null */
            target = null;
            actual = DateTimeUtils.toDate(target);
            assertNull(actual);

            target = DateTimeUtils.parseToLocalDateTime("2000/1/2 12:34");
            actual = DateTimeUtils.toDate(target);
            assertNotNull(actual);
        }
    }

    @Test
    public void toTimestampTest() throws Exception {
        { /* LocalDateTime */
            LocalDateTime target;
            Timestamp actual;

            /* null */
            target = null;
            actual = DateTimeUtils.toTimestamp(target);
            assertNull(actual);

            target = DateTimeUtils.parseToLocalDateTime("2000/1/2 12:34");
            actual = DateTimeUtils.toTimestamp(target);
            assertNotNull(actual);
        }
    }

    @Test
    public void getLastDayOfMonthTest() throws Exception {
        /* null */
        assertNull(DateTimeUtils.getLastDayOfMonth(null));

        {
            LocalDate target = DateTimeUtils.parseToLocalDate("2017/12/01", DateTimeUtils.DATE_FORMAT);
            LocalDate expect = DateTimeUtils.parseToLocalDate("2017/12/31", DateTimeUtils.DATE_FORMAT);
            assertEquals(DateTimeUtils.getLastDayOfMonth(target), expect);
        }
        {
            LocalDate target = DateTimeUtils.parseToLocalDate("2018/01/01", DateTimeUtils.DATE_FORMAT);
            LocalDate expect = DateTimeUtils.parseToLocalDate("2018/01/31", DateTimeUtils.DATE_FORMAT);
            assertEquals(DateTimeUtils.getLastDayOfMonth(target), expect);
        }
        {
            LocalDate target = DateTimeUtils.parseToLocalDate("2018/02/01", DateTimeUtils.DATE_FORMAT);
            LocalDate expect = DateTimeUtils.parseToLocalDate("2018/02/28", DateTimeUtils.DATE_FORMAT);
            assertEquals(DateTimeUtils.getLastDayOfMonth(target), expect);
        }
        {
            LocalDate target = DateTimeUtils.parseToLocalDate("2016/02/01", DateTimeUtils.DATE_FORMAT);
            LocalDate expect = DateTimeUtils.parseToLocalDate("2016/02/29", DateTimeUtils.DATE_FORMAT);
            assertEquals(DateTimeUtils.getLastDayOfMonth(target), expect);
        }
        {
            LocalDate target = DateTimeUtils.parseToLocalDate("2000/02/01", DateTimeUtils.DATE_FORMAT);
            LocalDate expect = DateTimeUtils.parseToLocalDate("2000/02/29", DateTimeUtils.DATE_FORMAT);
            assertEquals(DateTimeUtils.getLastDayOfMonth(target), expect);
        }
        {
            LocalDate target = DateTimeUtils.parseToLocalDate("2018/04/01", DateTimeUtils.DATE_FORMAT);
            LocalDate expect = DateTimeUtils.parseToLocalDate("2018/04/30", DateTimeUtils.DATE_FORMAT);
            assertEquals(DateTimeUtils.getLastDayOfMonth(target), expect);
        }
    }

    @Test
    public void getDayOfWeekTest() throws Exception {
        /* null */
        assertNull(DateTimeUtils.getDayOfWeek(null));

        { /* 日曜日 */
            LocalDate target = DateTimeUtils.parseToLocalDate("2018/01/07", DateTimeUtils.DATE_FORMAT);
            assertEquals(DateTimeUtils.getDayOfWeek(target), "日");
        }
        { /* 月曜日 */
            LocalDate target = DateTimeUtils.parseToLocalDate("2018/01/08", DateTimeUtils.DATE_FORMAT);
            assertEquals(DateTimeUtils.getDayOfWeek(target), "月");
        }
        { /* 火曜日 */
            LocalDate target = DateTimeUtils.parseToLocalDate("2018/01/09", DateTimeUtils.DATE_FORMAT);
            assertEquals(DateTimeUtils.getDayOfWeek(target), "火");
        }
        { /* 水曜日 */
            LocalDate target = DateTimeUtils.parseToLocalDate("2018/01/10", DateTimeUtils.DATE_FORMAT);
            assertEquals(DateTimeUtils.getDayOfWeek(target), "水");
        }
        { /* 木曜日 */
            LocalDate target = DateTimeUtils.parseToLocalDate("2018/01/11", DateTimeUtils.DATE_FORMAT);
            assertEquals(DateTimeUtils.getDayOfWeek(target), "木");
        }
        { /* 金曜日 */
            LocalDate target = DateTimeUtils.parseToLocalDate("2018/01/12", DateTimeUtils.DATE_FORMAT);
            assertEquals(DateTimeUtils.getDayOfWeek(target), "金");
        }
        { /* 土曜日 */
            LocalDate target = DateTimeUtils.parseToLocalDate("2018/01/13", DateTimeUtils.DATE_FORMAT);
            assertEquals(DateTimeUtils.getDayOfWeek(target), "土");
        }
    }
}
