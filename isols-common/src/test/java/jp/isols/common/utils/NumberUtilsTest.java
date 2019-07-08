package jp.isols.common.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NumberUtilsTest {

	@BeforeEach
    public void beforeEach() {
    }

	@Test
    public void isNumberTest() throws Exception {
        assertEquals(NumberUtils.isNumber(null), false);
        assertEquals(NumberUtils.isNumber(""), false);
        assertEquals(NumberUtils.isNumber("  "), false);

        assertEquals(NumberUtils.isNumber("ABCDEFGHIJKLMNOPQRSTUVWXYZ"), false);
        assertEquals(NumberUtils.isNumber("AAA"), false);
        assertEquals(NumberUtils.isNumber("abcdefghijklmnopqrstuvwxyz"), false);
        assertEquals(NumberUtils.isNumber("aaa"), false);
        assertEquals(NumberUtils.isNumber("   aaa"), false);
        assertEquals(NumberUtils.isNumber("aaa   "), false);

        assertEquals(NumberUtils.isNumber("012 aaa"), false);
        assertEquals(NumberUtils.isNumber("aaa 012"), false);
        assertEquals(NumberUtils.isNumber("012AAA"), false);
        assertEquals(NumberUtils.isNumber("AAA012"), false);
        assertEquals(NumberUtils.isNumber("012 AAA"), false);
        assertEquals(NumberUtils.isNumber("AAA 012"), false);

        assertEquals(NumberUtils.isInteger("1"), true);
        assertEquals(NumberUtils.isInteger("123"), true);
        assertEquals(NumberUtils.isInteger("012"), true);
        assertEquals(NumberUtils.isInteger("0123456789"), true);
        assertEquals(NumberUtils.isInteger("  012"), false);
        assertEquals(NumberUtils.isInteger("  0123456789"), false);
        assertEquals(NumberUtils.isInteger("012  "), false);
        assertEquals(NumberUtils.isInteger("0123456789  "), false);

        assertEquals(NumberUtils.isInteger("+1"), true);
        assertEquals(NumberUtils.isInteger("+123"), true);
        assertEquals(NumberUtils.isInteger("+012"), true);
        assertEquals(NumberUtils.isInteger("+0123456789"), true);
        assertEquals(NumberUtils.isInteger("+"), false);
        assertEquals(NumberUtils.isInteger("+  012"), false);
        assertEquals(NumberUtils.isInteger("+  0123456789"), false);
        assertEquals(NumberUtils.isInteger("+012  "), false);
        assertEquals(NumberUtils.isInteger("+0123456789  "), false);

        assertEquals(NumberUtils.isInteger("-1"), true);
        assertEquals(NumberUtils.isInteger("-123"), true);
        assertEquals(NumberUtils.isInteger("-012"), true);
        assertEquals(NumberUtils.isInteger("-0123456789"), true);
        assertEquals(NumberUtils.isInteger("-"), false);
        assertEquals(NumberUtils.isInteger("-  012"), false);
        assertEquals(NumberUtils.isInteger("-  0123456789"), false);
        assertEquals(NumberUtils.isInteger("-012  "), false);
        assertEquals(NumberUtils.isInteger("-0123456789  "), false);

        assertEquals(NumberUtils.isDecimal("1.2"), true);
        assertEquals(NumberUtils.isDecimal("0.123"), true);
        assertEquals(NumberUtils.isDecimal("01.2"), true);
        assertEquals(NumberUtils.isDecimal("01234.56789"), true);
        assertEquals(NumberUtils.isDecimal(".56789"), false);
        assertEquals(NumberUtils.isDecimal("  01.2"), false);
        assertEquals(NumberUtils.isDecimal("  01234.56789"), false);
        assertEquals(NumberUtils.isDecimal("01.2  "), false);
        assertEquals(NumberUtils.isDecimal("01234.56789  "), false);

        assertEquals(NumberUtils.isDecimal("+1.2"), true);
        assertEquals(NumberUtils.isDecimal("+0.123"), true);
        assertEquals(NumberUtils.isDecimal("+01.2"), true);
        assertEquals(NumberUtils.isDecimal("+01234.56789"), true);
        assertEquals(NumberUtils.isDecimal("+.56789"), false);
        assertEquals(NumberUtils.isDecimal("+"), false);
        assertEquals(NumberUtils.isDecimal("+  01.2"), false);
        assertEquals(NumberUtils.isDecimal("+  01234.56789"), false);
        assertEquals(NumberUtils.isDecimal("+01.2  "), false);
        assertEquals(NumberUtils.isDecimal("+01234.56789  "), false);

        assertEquals(NumberUtils.isDecimal("-1.2"), true);
        assertEquals(NumberUtils.isDecimal("-0.123"), true);
        assertEquals(NumberUtils.isDecimal("-01.2"), true);
        assertEquals(NumberUtils.isDecimal("-01234.56789"), true);
        assertEquals(NumberUtils.isDecimal("-.56789"), false);
        assertEquals(NumberUtils.isDecimal("-"), false);
        assertEquals(NumberUtils.isDecimal("-  01.2"), false);
        assertEquals(NumberUtils.isDecimal("-  01234.56789"), false);
        assertEquals(NumberUtils.isDecimal("-01.2  "), false);
        assertEquals(NumberUtils.isDecimal("-01234.56789  "), false);
	}

	@Test
    public void isIntegerTest() throws Exception {
        assertEquals(NumberUtils.isInteger(null), false);
        assertEquals(NumberUtils.isInteger(""), false);
        assertEquals(NumberUtils.isInteger("  "), false);

        assertEquals(NumberUtils.isInteger("ABCDEFGHIJKLMNOPQRSTUVWXYZ"), false);
        assertEquals(NumberUtils.isInteger("AAA"), false);
        assertEquals(NumberUtils.isInteger("abcdefghijklmnopqrstuvwxyz"), false);
        assertEquals(NumberUtils.isInteger("aaa"), false);
        assertEquals(NumberUtils.isInteger("   aaa"), false);
        assertEquals(NumberUtils.isInteger("aaa   "), false);

        assertEquals(NumberUtils.isInteger("1"), true);
        assertEquals(NumberUtils.isInteger("123"), true);
        assertEquals(NumberUtils.isInteger("012"), true);
        assertEquals(NumberUtils.isInteger("0123456789"), true);
        assertEquals(NumberUtils.isInteger("  012"), false);
        assertEquals(NumberUtils.isInteger("  0123456789"), false);
        assertEquals(NumberUtils.isInteger("012  "), false);
        assertEquals(NumberUtils.isInteger("0123456789  "), false);

        assertEquals(NumberUtils.isInteger("+1"), true);
        assertEquals(NumberUtils.isInteger("+123"), true);
        assertEquals(NumberUtils.isInteger("+012"), true);
        assertEquals(NumberUtils.isInteger("+0123456789"), true);
        assertEquals(NumberUtils.isInteger("+"), false);
        assertEquals(NumberUtils.isInteger("+  012"), false);
        assertEquals(NumberUtils.isInteger("+  0123456789"), false);
        assertEquals(NumberUtils.isInteger("+012  "), false);
        assertEquals(NumberUtils.isInteger("+0123456789  "), false);

        assertEquals(NumberUtils.isInteger("-1"), true);
        assertEquals(NumberUtils.isInteger("-123"), true);
        assertEquals(NumberUtils.isInteger("-012"), true);
        assertEquals(NumberUtils.isInteger("-0123456789"), true);
        assertEquals(NumberUtils.isInteger("-"), false);
        assertEquals(NumberUtils.isInteger("-  012"), false);
        assertEquals(NumberUtils.isInteger("-  0123456789"), false);
        assertEquals(NumberUtils.isInteger("-012  "), false);
        assertEquals(NumberUtils.isInteger("-0123456789  "), false);

        assertEquals(NumberUtils.isInteger("012 aaa"), false);
        assertEquals(NumberUtils.isInteger("aaa 012"), false);
        assertEquals(NumberUtils.isInteger("012AAA"), false);
        assertEquals(NumberUtils.isInteger("AAA012"), false);
        assertEquals(NumberUtils.isInteger("012 AAA"), false);
        assertEquals(NumberUtils.isInteger("AAA 012"), false);
	}

	@Test
    public void isDecimalTest() throws Exception {
        assertEquals(NumberUtils.isDecimal(null), false);
        assertEquals(NumberUtils.isDecimal(""), false);
        assertEquals(NumberUtils.isDecimal("  "), false);

        assertEquals(NumberUtils.isDecimal("ABCDEFGHIJKLMNOPQRSTUVWXYZ"), false);
        assertEquals(NumberUtils.isDecimal("AAA"), false);
        assertEquals(NumberUtils.isDecimal("abcdefghijklmnopqrstuvwxyz"), false);
        assertEquals(NumberUtils.isDecimal("aaa"), false);
        assertEquals(NumberUtils.isDecimal("   aaa"), false);
        assertEquals(NumberUtils.isDecimal("aaa   "), false);

        assertEquals(NumberUtils.isDecimal("1.2"), true);
        assertEquals(NumberUtils.isDecimal("0.123"), true);
        assertEquals(NumberUtils.isDecimal("01.2"), true);
        assertEquals(NumberUtils.isDecimal("01234.56789"), true);
        assertEquals(NumberUtils.isDecimal(".56789"), false);
        assertEquals(NumberUtils.isDecimal("  01.2"), false);
        assertEquals(NumberUtils.isDecimal("  01234.56789"), false);
        assertEquals(NumberUtils.isDecimal("01.2  "), false);
        assertEquals(NumberUtils.isDecimal("01234.56789  "), false);

        assertEquals(NumberUtils.isDecimal("+1.2"), true);
        assertEquals(NumberUtils.isDecimal("+0.123"), true);
        assertEquals(NumberUtils.isDecimal("+01.2"), true);
        assertEquals(NumberUtils.isDecimal("+01234.56789"), true);
        assertEquals(NumberUtils.isDecimal("+.56789"), false);
        assertEquals(NumberUtils.isDecimal("+"), false);
        assertEquals(NumberUtils.isDecimal("+  01.2"), false);
        assertEquals(NumberUtils.isDecimal("+  01234.56789"), false);
        assertEquals(NumberUtils.isDecimal("+01.2  "), false);
        assertEquals(NumberUtils.isDecimal("+01234.56789  "), false);

        assertEquals(NumberUtils.isDecimal("-1.2"), true);
        assertEquals(NumberUtils.isDecimal("-0.123"), true);
        assertEquals(NumberUtils.isDecimal("-01.2"), true);
        assertEquals(NumberUtils.isDecimal("-01234.56789"), true);
        assertEquals(NumberUtils.isDecimal("-.56789"), false);
        assertEquals(NumberUtils.isDecimal("-"), false);
        assertEquals(NumberUtils.isDecimal("-  01.2"), false);
        assertEquals(NumberUtils.isDecimal("-  01234.56789"), false);
        assertEquals(NumberUtils.isDecimal("-01.2  "), false);
        assertEquals(NumberUtils.isDecimal("-01234.56789  "), false);

        assertEquals(NumberUtils.isDecimal("012 aaa"), false);
        assertEquals(NumberUtils.isDecimal("aaa 012"), false);
        assertEquals(NumberUtils.isDecimal("012AAA"), false);
        assertEquals(NumberUtils.isDecimal("AAA012"), false);
        assertEquals(NumberUtils.isDecimal("012 AAA"), false);
        assertEquals(NumberUtils.isDecimal("AAA 012"), false);
	}

	@Test
    public void getDigitLengthTest() throws Exception {
        assertEquals(NumberUtils.getDigitLength(BigInteger.valueOf(1)), 1);
        assertEquals(NumberUtils.getDigitLength(BigInteger.valueOf(100)), 3);
        assertEquals(NumberUtils.getDigitLength(BigInteger.valueOf(-1)), 1);
        assertEquals(NumberUtils.getDigitLength(BigInteger.valueOf(-100)), 3);

        assertEquals(NumberUtils.getDigitLength(1), 1);
        assertEquals(NumberUtils.getDigitLength(100), 3);
        assertEquals(NumberUtils.getDigitLength(-1), 1);
        assertEquals(NumberUtils.getDigitLength(-100), 3);
	}

	@Test
    public void maxTest() throws Exception {
        assertEquals(NumberUtils.max(BigInteger.valueOf(1)), BigInteger.valueOf(1));
        assertEquals(NumberUtils.max(BigInteger.valueOf(0),BigInteger.valueOf(1)), BigInteger.valueOf(1));
        assertEquals(NumberUtils.max(BigInteger.valueOf(-1),BigInteger.valueOf(0)), BigInteger.valueOf(0));
        assertEquals(NumberUtils.max(BigInteger.valueOf(-1),BigInteger.valueOf(0),BigInteger.valueOf(1)), BigInteger.valueOf(1));
        assertEquals(NumberUtils.max(BigInteger.valueOf(1),BigInteger.valueOf(2),BigInteger.valueOf(3)), BigInteger.valueOf(3));
        assertEquals(NumberUtils.max(BigInteger.valueOf(-1),BigInteger.valueOf(-2),BigInteger.valueOf(-3)), BigInteger.valueOf(-1));

        assertEquals(NumberUtils.max(1), Integer.valueOf(1));
        assertEquals(NumberUtils.max(0,1), Integer.valueOf(1));
        assertEquals(NumberUtils.max(-1,0), Integer.valueOf(0));
        assertEquals(NumberUtils.max(-1,0,1), Integer.valueOf(1));
        assertEquals(NumberUtils.max(1,2,3), Integer.valueOf(3));
        assertEquals(NumberUtils.max(-1,-2,-3), Integer.valueOf(-1));
	}

	@Test
    public void minTest() throws Exception {
        assertEquals(NumberUtils.min(BigInteger.valueOf(1)), BigInteger.valueOf(1));
        assertEquals(NumberUtils.min(BigInteger.valueOf(0),BigInteger.valueOf(1)), BigInteger.valueOf(0));
        assertEquals(NumberUtils.min(BigInteger.valueOf(-1),BigInteger.valueOf(0)), BigInteger.valueOf(-1));
        assertEquals(NumberUtils.min(BigInteger.valueOf(-1),BigInteger.valueOf(0),BigInteger.valueOf(1)), BigInteger.valueOf(-1));
        assertEquals(NumberUtils.min(BigInteger.valueOf(1),BigInteger.valueOf(2),BigInteger.valueOf(3)), BigInteger.valueOf(1));
        assertEquals(NumberUtils.min(BigInteger.valueOf(-1),BigInteger.valueOf(-2),BigInteger.valueOf(-3)), BigInteger.valueOf(-3));

        assertEquals(NumberUtils.min(1), Integer.valueOf(1));
        assertEquals(NumberUtils.min(0,1), Integer.valueOf(0));
        assertEquals(NumberUtils.min(-1,0), Integer.valueOf(-1));
        assertEquals(NumberUtils.min(-1,0,1), Integer.valueOf(-1));
        assertEquals(NumberUtils.min(1,2,3), Integer.valueOf(1));
        assertEquals(NumberUtils.min(-1,-2,-3), Integer.valueOf(-3));
	}

	@Test
    public void toFormatStringTest() throws Exception {
        assertEquals(NumberUtils.toFormatString(BigInteger.ZERO), "0");
        assertEquals(NumberUtils.toFormatString(BigInteger.valueOf(100)), "100");
        assertEquals(NumberUtils.toFormatString(BigInteger.valueOf(10000)), "10,000");
        assertEquals(NumberUtils.toFormatString(BigInteger.valueOf(10000000)), "10,000,000");
        assertEquals(NumberUtils.toFormatString(BigInteger.valueOf(-100)), "-100");
        assertEquals(NumberUtils.toFormatString(BigInteger.valueOf(-10000)), "-10,000");
        assertEquals(NumberUtils.toFormatString(BigInteger.valueOf(-10000000)), "-10,000,000");

        assertEquals(NumberUtils.toFormatString(0), "0");
        assertEquals(NumberUtils.toFormatString(100), "100");
        assertEquals(NumberUtils.toFormatString(10000), "10,000");
        assertEquals(NumberUtils.toFormatString(10000000), "10,000,000");
        assertEquals(NumberUtils.toFormatString(-100), "-100");
        assertEquals(NumberUtils.toFormatString(-10000), "-10,000");
        assertEquals(NumberUtils.toFormatString(-10000000), "-10,000,000");
	}
}
