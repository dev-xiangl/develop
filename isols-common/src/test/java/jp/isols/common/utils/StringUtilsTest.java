package jp.isols.common.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StringUtilsTest {

    @BeforeEach
    public void beforeEach() {
    }

    @Test
    public void isEmptyTest() throws Exception {
        assertEquals(StringUtils.isEmpty(null), true);
        assertEquals(StringUtils.isEmpty(""), true);
        assertEquals(StringUtils.isEmpty("  "), false);

        assertEquals(StringUtils.isEmpty("AAA"), false);
    }

    @Test
    public void isBlankTest() throws Exception {
        assertEquals(StringUtils.isBlank(null), true);
        assertEquals(StringUtils.isBlank(""), true);
        assertEquals(StringUtils.isBlank("  "), true);

        assertEquals(StringUtils.isBlank("AAA"), false);
    }

    @Test
    public void isAsciiTest() throws Exception {
        assertEquals(StringUtils.isAscii(null), false);
        assertEquals(StringUtils.isAscii(""), false);
        assertEquals(StringUtils.isAscii("  "), true);

        assertEquals(StringUtils.isAscii("ABCDEFGHIJKLMNOPQRSTUVWXYZ"), true);
        assertEquals(StringUtils.isAscii("AAA"), true);
        assertEquals(StringUtils.isAscii("abcdefghijklmnopqrstuvwxyz"), true);
        assertEquals(StringUtils.isAscii("aaa"), true);
        assertEquals(StringUtils.isAscii("  aaa"), true);
        assertEquals(StringUtils.isAscii("aaa  "), true);

        assertEquals(StringUtils.isAscii("012"), true);
        assertEquals(StringUtils.isAscii("0123456789"), true);
        assertEquals(StringUtils.isAscii("  012"), true);
        assertEquals(StringUtils.isAscii("  0123456789"), true);
        assertEquals(StringUtils.isAscii("012  "), true);
        assertEquals(StringUtils.isAscii("0123456789  "), true);

        assertEquals(StringUtils.isAscii("012aaa"), true);
        assertEquals(StringUtils.isAscii("aaa012"), true);
        assertEquals(StringUtils.isAscii("012 aaa"), true);
        assertEquals(StringUtils.isAscii("aaa 012"), true);
        assertEquals(StringUtils.isAscii("012AAA"), true);
        assertEquals(StringUtils.isAscii("AAA012"), true);
        assertEquals(StringUtils.isAscii("012 AAA"), true);
        assertEquals(StringUtils.isAscii("AAA 012"), true);

        assertEquals(StringUtils.isAscii("　"), false);
        assertEquals(StringUtils.isAscii("０１２３４５６７８９"), false);
        assertEquals(StringUtils.isAscii("ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ"), false);
        assertEquals(StringUtils.isAscii("ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ"), false);
        assertEquals(StringUtils.isAscii("あいうえお"), false);
        assertEquals(StringUtils.isAscii("アイウエオ"), false);
    }

    @Test
    public void isAlphaTest() throws Exception {
        assertEquals(StringUtils.isAlpha(null), false);
        assertEquals(StringUtils.isAlpha(""), false);
        assertEquals(StringUtils.isAlpha("  "), false);

        assertEquals(StringUtils.isAlpha("ABCDEFGHIJKLMNOPQRSTUVWXYZ"), true);
        assertEquals(StringUtils.isAlpha("AAA"), true);
        assertEquals(StringUtils.isAlpha("abcdefghijklmnopqrstuvwxyz"), true);
        assertEquals(StringUtils.isAlpha("aaa"), true);
        assertEquals(StringUtils.isAlpha("  aaa"), false);
        assertEquals(StringUtils.isAlpha("aaa  "), false);

        assertEquals(StringUtils.isAlpha("012"), false);
        assertEquals(StringUtils.isAlpha("0123456789"), false);
        assertEquals(StringUtils.isAlpha("  012"), false);
        assertEquals(StringUtils.isAlpha("  0123456789"), false);
        assertEquals(StringUtils.isAlpha("012  "), false);
        assertEquals(StringUtils.isAlpha("0123456789  "), false);

        assertEquals(StringUtils.isAlpha("012aaa"), false);
        assertEquals(StringUtils.isAlpha("aaa012"), false);
        assertEquals(StringUtils.isAlpha("012 aaa"), false);
        assertEquals(StringUtils.isAlpha("aaa 012"), false);
        assertEquals(StringUtils.isAlpha("012AAA"), false);
        assertEquals(StringUtils.isAlpha("AAA012"), false);
        assertEquals(StringUtils.isAlpha("012 AAA"), false);
        assertEquals(StringUtils.isAlpha("AAA 012"), false);

        assertEquals(StringUtils.isAlpha("　"), false);
        assertEquals(StringUtils.isAlpha("０１２３４５６７８９"), false);
        assertEquals(StringUtils.isAlpha("ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ"), false);
        assertEquals(StringUtils.isAlpha("ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ"), false);
        assertEquals(StringUtils.isAlpha("あいうえお"), false);
        assertEquals(StringUtils.isAlpha("アイウエオ"), false);
    }

    @Test
    public void isHalfAlphaNumericTest() throws Exception {
        assertEquals(StringUtils.isHalfAlphaNumeric(null), false);
        assertEquals(StringUtils.isHalfAlphaNumeric(""), false);
        assertEquals(StringUtils.isHalfAlphaNumeric("  "), false);

        assertEquals(StringUtils.isHalfAlphaNumeric("ABCDEFGHIJKLMNOPQRSTUVWXYZ"), true);
        assertEquals(StringUtils.isHalfAlphaNumeric("AAA"), true);
        assertEquals(StringUtils.isHalfAlphaNumeric("abcdefghijklmnopqrstuvwxyz"), true);
        assertEquals(StringUtils.isHalfAlphaNumeric("aaa"), true);
        assertEquals(StringUtils.isHalfAlphaNumeric("  aaa"), false);
        assertEquals(StringUtils.isHalfAlphaNumeric("aaa  "), false);

        assertEquals(StringUtils.isHalfAlphaNumeric("012"), true);
        assertEquals(StringUtils.isHalfAlphaNumeric("0123456789"), true);
        assertEquals(StringUtils.isHalfAlphaNumeric("  012"), false);
        assertEquals(StringUtils.isHalfAlphaNumeric("  0123456789"), false);
        assertEquals(StringUtils.isHalfAlphaNumeric("012  "), false);
        assertEquals(StringUtils.isHalfAlphaNumeric("0123456789  "), false);

        assertEquals(StringUtils.isHalfAlphaNumeric("012aaa"), true);
        assertEquals(StringUtils.isHalfAlphaNumeric("aaa012"), true);
        assertEquals(StringUtils.isHalfAlphaNumeric("012 aaa"), false);
        assertEquals(StringUtils.isHalfAlphaNumeric("aaa 012"), false);
        assertEquals(StringUtils.isHalfAlphaNumeric("012AAA"), true);
        assertEquals(StringUtils.isHalfAlphaNumeric("AAA012"), true);
        assertEquals(StringUtils.isHalfAlphaNumeric("012 AAA"), false);
        assertEquals(StringUtils.isHalfAlphaNumeric("AAA 012"), false);

        assertEquals(StringUtils.isHalfAlphaNumeric("　"), false);
        assertEquals(StringUtils.isHalfAlphaNumeric("０１２３４５６７８９"), false);
        assertEquals(StringUtils.isHalfAlphaNumeric("ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ"), false);
        assertEquals(StringUtils.isHalfAlphaNumeric("ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ"), false);
        assertEquals(StringUtils.isHalfAlphaNumeric("あいうえお"), false);
        assertEquals(StringUtils.isHalfAlphaNumeric("アイウエオ"), false);
    }

    @Test
    public void isHiraganaTest() throws Exception {
        assertEquals(StringUtils.isHiragana(null), false);
        assertEquals(StringUtils.isHiragana(""), false);
        assertEquals(StringUtils.isHiragana("  "), false);

        assertEquals(StringUtils.isHiragana("ABCDEFGHIJKLMNOPQRSTUVWXYZ"), false);
        assertEquals(StringUtils.isHiragana("AAA"), false);
        assertEquals(StringUtils.isHiragana("abcdefghijklmnopqrstuvwxyz"), false);
        assertEquals(StringUtils.isHiragana("aaa"), false);
        assertEquals(StringUtils.isHiragana("  aaa"), false);
        assertEquals(StringUtils.isHiragana("aaa  "), false);

        assertEquals(StringUtils.isHiragana("012"), false);
        assertEquals(StringUtils.isHiragana("0123456789"), false);
        assertEquals(StringUtils.isHiragana("  012"), false);
        assertEquals(StringUtils.isHiragana("  0123456789"), false);
        assertEquals(StringUtils.isHiragana("012  "), false);
        assertEquals(StringUtils.isHiragana("0123456789  "), false);

        assertEquals(StringUtils.isHiragana("012aaa"), false);
        assertEquals(StringUtils.isHiragana("aaa012"), false);
        assertEquals(StringUtils.isHiragana("012 aaa"), false);
        assertEquals(StringUtils.isHiragana("aaa 012"), false);
        assertEquals(StringUtils.isHiragana("012AAA"), false);
        assertEquals(StringUtils.isHiragana("AAA012"), false);
        assertEquals(StringUtils.isHiragana("012 AAA"), false);
        assertEquals(StringUtils.isHiragana("AAA 012"), false);

        assertEquals(StringUtils.isHiragana("　"), false);
        assertEquals(StringUtils.isHiragana("０１２３４５６７８９"), false);
        assertEquals(StringUtils.isHiragana("ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ"), false);
        assertEquals(StringUtils.isHiragana("ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ"), false);
        assertEquals(StringUtils.isHiragana("あいうえお"), true);
        assertEquals(StringUtils.isHiragana("アイウエオ"), false);
    }

    @Test
    public void isKatakanaTest() throws Exception {
        assertEquals(StringUtils.isKatakana(null), false);
        assertEquals(StringUtils.isKatakana(""), false);
        assertEquals(StringUtils.isKatakana("  "), false);

        assertEquals(StringUtils.isKatakana("ABCDEFGHIJKLMNOPQRSTUVWXYZ"), false);
        assertEquals(StringUtils.isKatakana("AAA"), false);
        assertEquals(StringUtils.isKatakana("abcdefghijklmnopqrstuvwxyz"), false);
        assertEquals(StringUtils.isKatakana("aaa"), false);
        assertEquals(StringUtils.isKatakana("  aaa"), false);
        assertEquals(StringUtils.isKatakana("aaa  "), false);

        assertEquals(StringUtils.isKatakana("012"), false);
        assertEquals(StringUtils.isKatakana("0123456789"), false);
        assertEquals(StringUtils.isKatakana("  012"), false);
        assertEquals(StringUtils.isKatakana("  0123456789"), false);
        assertEquals(StringUtils.isKatakana("012  "), false);
        assertEquals(StringUtils.isKatakana("0123456789  "), false);

        assertEquals(StringUtils.isKatakana("012aaa"), false);
        assertEquals(StringUtils.isKatakana("aaa012"), false);
        assertEquals(StringUtils.isKatakana("012 aaa"), false);
        assertEquals(StringUtils.isKatakana("aaa 012"), false);
        assertEquals(StringUtils.isKatakana("012AAA"), false);
        assertEquals(StringUtils.isKatakana("AAA012"), false);
        assertEquals(StringUtils.isKatakana("012 AAA"), false);
        assertEquals(StringUtils.isKatakana("AAA 012"), false);

        assertEquals(StringUtils.isKatakana("　"), false);
        assertEquals(StringUtils.isKatakana("０１２３４５６７８９"), false);
        assertEquals(StringUtils.isKatakana("ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ"), false);
        assertEquals(StringUtils.isKatakana("ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ"), false);
        assertEquals(StringUtils.isKatakana("あいうえお"), false);
        assertEquals(StringUtils.isKatakana("アイウエオ"), true);
    }

    @Test
    public void toHalfOfNumberTest() throws Exception {
        assertEquals(StringUtils.toHalfOfNumber(null), null);
        assertEquals(StringUtils.toHalfOfNumber(""), "");
        assertEquals(StringUtils.toHalfOfNumber(" "), " ");
        assertEquals(StringUtils.toHalfOfNumber("　"), "　");

        assertEquals(StringUtils.toHalfOfNumber("０"), "0");
        assertEquals(StringUtils.toHalfOfNumber("１"), "1");
        assertEquals(StringUtils.toHalfOfNumber("２"), "2");
        assertEquals(StringUtils.toHalfOfNumber("３"), "3");
        assertEquals(StringUtils.toHalfOfNumber("４"), "4");
        assertEquals(StringUtils.toHalfOfNumber("５"), "5");
        assertEquals(StringUtils.toHalfOfNumber("６"), "6");
        assertEquals(StringUtils.toHalfOfNumber("７"), "7");
        assertEquals(StringUtils.toHalfOfNumber("８"), "8");
        assertEquals(StringUtils.toHalfOfNumber("９"), "9");

        assertEquals(StringUtils.toHalfOfNumber("0"), "0");
        assertEquals(StringUtils.toHalfOfNumber("1"), "1");
        assertEquals(StringUtils.toHalfOfNumber("2"), "2");
        assertEquals(StringUtils.toHalfOfNumber("3"), "3");
        assertEquals(StringUtils.toHalfOfNumber("4"), "4");
        assertEquals(StringUtils.toHalfOfNumber("5"), "5");
        assertEquals(StringUtils.toHalfOfNumber("6"), "6");
        assertEquals(StringUtils.toHalfOfNumber("7"), "7");
        assertEquals(StringUtils.toHalfOfNumber("8"), "8");
        assertEquals(StringUtils.toHalfOfNumber("9"), "9");

        assertEquals(StringUtils.toHalfOfNumber("０１２３４５６７８９"), "0123456789");
        assertEquals(StringUtils.toHalfOfNumber(" ０１２３４５６７８９"), " 0123456789");
        assertEquals(StringUtils.toHalfOfNumber("０１２３４５６７８９ "), "0123456789 ");
        assertEquals(StringUtils.toHalfOfNumber(" ０１２３４５６７８９ "), " 0123456789 ");
        assertEquals(StringUtils.toHalfOfNumber("０１２３４ ５６７８９"), "01234 56789");
        assertEquals(StringUtils.toHalfOfNumber("　０１２３４５６７８９"), "　0123456789");
        assertEquals(StringUtils.toHalfOfNumber("０１２３４５６７８９　"), "0123456789　");
        assertEquals(StringUtils.toHalfOfNumber("　０１２３４５６７８９　"), "　0123456789　");
        assertEquals(StringUtils.toHalfOfNumber("０１２３４　５６７８９"), "01234　56789");

        assertEquals(StringUtils.toHalfOfNumber("0123456789"), "0123456789");
        assertEquals(StringUtils.toHalfOfNumber(" 0123456789"), " 0123456789");
        assertEquals(StringUtils.toHalfOfNumber("0123456789 "), "0123456789 ");
        assertEquals(StringUtils.toHalfOfNumber(" 0123456789 "), " 0123456789 ");
        assertEquals(StringUtils.toHalfOfNumber("01234 56789"), "01234 56789");
        assertEquals(StringUtils.toHalfOfNumber("　0123456789"), "　0123456789");
        assertEquals(StringUtils.toHalfOfNumber("0123456789　"), "0123456789　");
        assertEquals(StringUtils.toHalfOfNumber("　0123456789　"), "　0123456789　");
        assertEquals(StringUtils.toHalfOfNumber("01234　56789"), "01234　56789");

        assertEquals(StringUtils.toHalfOfNumber("００１１２２３３４４５５６６７７８８９９"), "00112233445566778899");
        assertEquals(StringUtils.toHalfOfNumber("0123456789"), "0123456789");
        assertEquals(StringUtils.toHalfOfNumber("あいうえお"), "あいうえお");
        assertEquals(StringUtils.toHalfOfNumber("あいうえお１かきくけこ"), "あいうえお1かきくけこ");
        assertEquals(StringUtils.toHalfOfNumber("あいうえお1かきくけこ"), "あいうえお1かきくけこ");

        assertEquals(StringUtils.toHalfOfNumber("ＡＢＣ"), "ＡＢＣ");
        assertEquals(StringUtils.toHalfOfNumber("abc"), "abc");
    }

    @Test
    public void toHalfOfAlphaTest() throws Exception {
        assertEquals(StringUtils.toHalfOfAlpha(null), null);
        assertEquals(StringUtils.toHalfOfAlpha(""), "");

        assertEquals(StringUtils.toHalfOfAlpha("０１２３４５６７８９"), "０１２３４５６７８９");
        assertEquals(StringUtils.toHalfOfAlpha("ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ"), "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        assertEquals(StringUtils.toHalfOfAlpha("ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ"), "abcdefghijklmnopqrstuvwxyz");
    }

    @Test
    public void toHalfOfAlphaUpperTest() throws Exception {
        assertEquals(StringUtils.toHalfOfAlphaUpper(null), null);
        assertEquals(StringUtils.toHalfOfAlphaUpper(""), "");

        assertEquals(StringUtils.toHalfOfAlphaUpper("０１２３４５６７８９"), "０１２３４５６７８９");
        assertEquals(StringUtils.toHalfOfAlphaUpper("ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ"), "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        assertEquals(StringUtils.toHalfOfAlphaUpper("ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ"), "ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ");
    }

    @Test
    public void toHalfOfAlphaLowerTest() throws Exception {
        assertEquals(StringUtils.toHalfOfAlphaLower(null), null);
        assertEquals(StringUtils.toHalfOfAlphaLower(""), "");

        assertEquals(StringUtils.toHalfOfAlphaLower("０１２３４５６７８９"), "０１２３４５６７８９");
        assertEquals(StringUtils.toHalfOfAlphaLower("ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ"), "ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ");
        assertEquals(StringUtils.toHalfOfAlphaLower("ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ"), "abcdefghijklmnopqrstuvwxyz");
    }

    @Test
    public void toFullOfNumberTest() throws Exception {
        assertEquals(StringUtils.toFullOfNumber(null), null);
        assertEquals(StringUtils.toFullOfNumber(""), "");
        assertEquals(StringUtils.toFullOfNumber(" "), " ");
        assertEquals(StringUtils.toFullOfNumber("　"), "　");

        assertEquals(StringUtils.toFullOfNumber("０"), "０");
        assertEquals(StringUtils.toFullOfNumber("１"), "１");
        assertEquals(StringUtils.toFullOfNumber("２"), "２");
        assertEquals(StringUtils.toFullOfNumber("３"), "３");
        assertEquals(StringUtils.toFullOfNumber("４"), "４");
        assertEquals(StringUtils.toFullOfNumber("５"), "５");
        assertEquals(StringUtils.toFullOfNumber("６"), "６");
        assertEquals(StringUtils.toFullOfNumber("７"), "７");
        assertEquals(StringUtils.toFullOfNumber("８"), "８");
        assertEquals(StringUtils.toFullOfNumber("９"), "９");

        assertEquals(StringUtils.toFullOfNumber("0"), "０");
        assertEquals(StringUtils.toFullOfNumber("1"), "１");
        assertEquals(StringUtils.toFullOfNumber("2"), "２");
        assertEquals(StringUtils.toFullOfNumber("3"), "３");
        assertEquals(StringUtils.toFullOfNumber("4"), "４");
        assertEquals(StringUtils.toFullOfNumber("5"), "５");
        assertEquals(StringUtils.toFullOfNumber("6"), "６");
        assertEquals(StringUtils.toFullOfNumber("7"), "７");
        assertEquals(StringUtils.toFullOfNumber("8"), "８");
        assertEquals(StringUtils.toFullOfNumber("9"), "９");

        assertEquals(StringUtils.toFullOfNumber("０１２３４５６７８９"), "０１２３４５６７８９");
        assertEquals(StringUtils.toFullOfNumber(" ０１２３４５６７８９"), " ０１２３４５６７８９");
        assertEquals(StringUtils.toFullOfNumber("０１２３４５６７８９ "), "０１２３４５６７８９ ");
        assertEquals(StringUtils.toFullOfNumber(" ０１２３４５６７８９ "), " ０１２３４５６７８９ ");
        assertEquals(StringUtils.toFullOfNumber("０１２３４ ５６７８９"), "０１２３４ ５６７８９");
        assertEquals(StringUtils.toFullOfNumber("　０１２３４５６７８９"), "　０１２３４５６７８９");
        assertEquals(StringUtils.toFullOfNumber("０１２３４５６７８９　"), "０１２３４５６７８９　");
        assertEquals(StringUtils.toFullOfNumber("　０１２３４５６７８９　"), "　０１２３４５６７８９　");
        assertEquals(StringUtils.toFullOfNumber("０１２３４　５６７８９"), "０１２３４　５６７８９");

        assertEquals(StringUtils.toFullOfNumber("0123456789"), "０１２３４５６７８９");
        assertEquals(StringUtils.toFullOfNumber(" 0123456789"), " ０１２３４５６７８９");
        assertEquals(StringUtils.toFullOfNumber("0123456789 "), "０１２３４５６７８９ ");
        assertEquals(StringUtils.toFullOfNumber(" 0123456789 "), " ０１２３４５６７８９ ");
        assertEquals(StringUtils.toFullOfNumber("01234 56789"), "０１２３４ ５６７８９");
        assertEquals(StringUtils.toFullOfNumber("　0123456789"), "　０１２３４５６７８９");
        assertEquals(StringUtils.toFullOfNumber("0123456789　"), "０１２３４５６７８９　");
        assertEquals(StringUtils.toFullOfNumber("　0123456789　"), "　０１２３４５６７８９　");
        assertEquals(StringUtils.toFullOfNumber("01234　56789"), "０１２３４　５６７８９");

        assertEquals(StringUtils.toFullOfNumber("００１１２２３３４４５５６６７７８８９９"), "００１１２２３３４４５５６６７７８８９９");
        assertEquals(StringUtils.toFullOfNumber("0123456789"), "０１２３４５６７８９");
        assertEquals(StringUtils.toFullOfNumber("あいうえお"), "あいうえお");
        assertEquals(StringUtils.toFullOfNumber("あいうえお１かきくけこ"), "あいうえお１かきくけこ");
        assertEquals(StringUtils.toFullOfNumber("あいうえお1かきくけこ"), "あいうえお１かきくけこ");

        assertEquals(StringUtils.toFullOfNumber("ＡＢＣ"), "ＡＢＣ");
        assertEquals(StringUtils.toFullOfNumber("abc"), "abc");
    }

    @Test
    public void fillSpaceTest() throws Exception {
        assertEquals(StringUtils.fillSpace(null, 0), null);
        assertEquals(StringUtils.fillSpace(null, 4), "    ");
        assertEquals(StringUtils.fillSpace(null, -4), "    ");
        assertEquals(StringUtils.fillSpace("", 0), "");
        assertEquals(StringUtils.fillSpace("", 4), "    ");
        assertEquals(StringUtils.fillSpace("", -4), "    ");

        assertEquals(StringUtils.fillSpace("123", 0), "123");
        assertEquals(StringUtils.fillSpace("123", 4), "    123");
        assertEquals(StringUtils.fillSpace("123", -4), "123    ");
    }

    @Test
    public void fillZeroTest() throws Exception {
        assertEquals(StringUtils.fillZero(null, 0), null);
        assertEquals(StringUtils.fillZero(null, 4), "0000");
        assertEquals(StringUtils.fillZero(null, -4), "0000");
        assertEquals(StringUtils.fillZero("", 0), "");
        assertEquals(StringUtils.fillZero("", 4), "0000");
        assertEquals(StringUtils.fillZero("", -4), "0000");

        assertEquals(StringUtils.fillZero("123", 0), "123");
        assertEquals(StringUtils.fillZero("123", 4), "0000123");
        assertEquals(StringUtils.fillZero("123", -4), "1230000");
    }

    @Test
    public void repeatTest() throws Exception {
        assertEquals(StringUtils.repeat(null, -1), null);
        assertEquals(StringUtils.repeat(null,  0), null);
        assertEquals(StringUtils.repeat(null,  1), null);
        assertEquals(StringUtils.repeat(null,  4), null);

        assertEquals(StringUtils.repeat("", -1), null);
        assertEquals(StringUtils.repeat("",  0), null);
        assertEquals(StringUtils.repeat("",  1), "");
        assertEquals(StringUtils.repeat("",  4), "");

        assertEquals(StringUtils.repeat(" ", -1), null);
        assertEquals(StringUtils.repeat(" ",  0), null);
        assertEquals(StringUtils.repeat(" ",  1), " ");
        assertEquals(StringUtils.repeat(" ",  4), "    ");

        assertEquals(StringUtils.repeat("a", -1), null);
        assertEquals(StringUtils.repeat("a",  0), null);
        assertEquals(StringUtils.repeat("a",  1), "a");
        assertEquals(StringUtils.repeat("a",  4), "aaaa");

        assertEquals(StringUtils.repeat("あ", -1), null);
        assertEquals(StringUtils.repeat("あ",  0), null);
        assertEquals(StringUtils.repeat("あ",  1), "あ");
        assertEquals(StringUtils.repeat("あ",  4), "ああああ");

        assertEquals(StringUtils.repeat("ＡＢＣ", -1), null);
        assertEquals(StringUtils.repeat("ＡＢＣ",  0), null);
        assertEquals(StringUtils.repeat("ＡＢＣ",  1), "ＡＢＣ");
        assertEquals(StringUtils.repeat("ＡＢＣ",  4), "ＡＢＣＡＢＣＡＢＣＡＢＣ");
    }

    @Test
    public void isMS932Test() throws Exception {
        assertEquals(StringUtils.isMS932("０１２３４５６７８９"), true);
        assertEquals(StringUtils.isMS932("ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ"), true);
        assertEquals(StringUtils.isMS932("ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ"), true);
        assertEquals(StringUtils.isMS932("あいうえお"), true);
        assertEquals(StringUtils.isMS932("アイウエオ"), true);

        assertEquals(StringUtils.isMS932("Καλημέρα κόσμε"), false);
    }

    @Test
    public void isUTF8Test() throws Exception {
        assertEquals(StringUtils.isUTF8("０１２３４５６７８９"), true);
        assertEquals(StringUtils.isUTF8("ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ"), true);
        assertEquals(StringUtils.isUTF8("ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ"), true);
        assertEquals(StringUtils.isUTF8("あいうえお"), true);
        assertEquals(StringUtils.isUTF8("アイウエオ"), true);

        assertEquals(StringUtils.isUTF8("Καλημέρα κόσμε"), true);
    }

    @Test
    public void splitReturnCodeTest() throws Exception {
        { /* null */
            List<String> actual = StringUtils.splitReturnCode(null);
            assertNull(actual);
        }

        { /* 空文字 */
            List<String> actual = StringUtils.splitReturnCode("");
            assertNull(actual);
        }

        { /* 空白 */
            List<String> actual = StringUtils.splitReturnCode(" ");
            assertNotNull(actual);
            assertEquals(actual.size(), 1);
            assertEquals(actual.get(0), " ");
        }

        { /* 空白 */
            List<String> actual = StringUtils.splitReturnCode(" \n ");
            assertNotNull(actual);
            assertEquals(actual.size(), 2);
            assertEquals(actual.get(0), " ");
            assertEquals(actual.get(1), " ");
        }

        { /* 空白 */
            List<String> actual = StringUtils.splitReturnCode("　");
            assertNotNull(actual);
            assertEquals(actual.size(), 1);
            assertEquals(actual.get(0), "　");
        }

        { /* 空白 */
            List<String> actual = StringUtils.splitReturnCode("　\n　");
            assertNotNull(actual);
            assertEquals(actual.size(), 2);
            assertEquals(actual.get(0), "　");
            assertEquals(actual.get(1), "　");
        }

        { /* 改行コードなし */
            List<String> actual = StringUtils.splitReturnCode("aaabbb");
            assertNotNull(actual);
            assertEquals(actual.size(), 1);
            assertEquals(actual.get(0), "aaabbb");
        }

        { /* 改行コードのみ */
            List<String> actual;

            actual = StringUtils.splitReturnCode("\n");
            assertNotNull(actual);
            assertEquals(actual.size(), 0);

            actual = StringUtils.splitReturnCode("\r\n");
            assertNotNull(actual);
            assertEquals(actual.size(), 0);

            actual = StringUtils.splitReturnCode("\r");
            assertNotNull(actual);
            assertEquals(actual.size(), 0);
        }

        { /* 改行コードあり('\r\n') */
            List<String> actual;

            actual = StringUtils.splitReturnCode(" \r\n ");
            assertNotNull(actual);
            assertEquals(actual.size(), 2);
            assertEquals(actual.get(0), " ");
            assertEquals(actual.get(1), " ");

            actual = StringUtils.splitReturnCode("\r\naaa");
            assertNotNull(actual);
            assertEquals(actual.size(), 2);
            assertEquals(actual.get(0), "");
            assertEquals(actual.get(1), "aaa");

            actual = StringUtils.splitReturnCode("aaa\r\n");
            assertNotNull(actual);
            assertEquals(actual.size(), 1);
            assertEquals(actual.get(0), "aaa");

            actual = StringUtils.splitReturnCode("aaa\r\nbbb");
            assertNotNull(actual);
            assertEquals(actual.size(), 2);
            assertEquals(actual.get(0), "aaa");
            assertEquals(actual.get(1), "bbb");

            actual = StringUtils.splitReturnCode("aaa\r\n\r\nccc");
            assertNotNull(actual);
            assertEquals(actual.size(), 3);
            assertEquals(actual.get(0), "aaa");
            assertEquals(actual.get(1), "");
            assertEquals(actual.get(2), "ccc");
        }

        { /* 改行コードあり('\n') */
            List<String> actual;

            actual = StringUtils.splitReturnCode(" \n ");
            assertNotNull(actual);
            assertEquals(actual.size(), 2);
            assertEquals(actual.get(0), " ");
            assertEquals(actual.get(1), " ");

            actual = StringUtils.splitReturnCode("\naaa");
            assertNotNull(actual);
            assertEquals(actual.size(), 2);
            assertEquals(actual.get(0), "");
            assertEquals(actual.get(1), "aaa");

            actual = StringUtils.splitReturnCode("aaa\n");
            assertNotNull(actual);
            assertEquals(actual.size(), 1);
            assertEquals(actual.get(0), "aaa");

            actual = StringUtils.splitReturnCode("aaa\nbbb");
            assertNotNull(actual);
            assertEquals(actual.size(), 2);
            assertEquals(actual.get(0), "aaa");
            assertEquals(actual.get(1), "bbb");

            actual = StringUtils.splitReturnCode("aaa\n\nccc");
            assertNotNull(actual);
            assertEquals(actual.size(), 3);
            assertEquals(actual.get(0), "aaa");
            assertEquals(actual.get(1), "");
            assertEquals(actual.get(2), "ccc");
        }

        { /* 改行コードあり('\r') */
            List<String> actual;

            actual = StringUtils.splitReturnCode(" \r ");
            assertNotNull(actual);
            assertEquals(actual.size(), 2);
            assertEquals(actual.get(0), " ");
            assertEquals(actual.get(1), " ");

            actual = StringUtils.splitReturnCode("\raaa");
            assertNotNull(actual);
            assertEquals(actual.size(), 2);
            assertEquals(actual.get(0), "");
            assertEquals(actual.get(1), "aaa");

            actual = StringUtils.splitReturnCode("aaa\r");
            assertNotNull(actual);
            assertEquals(actual.size(), 1);
            assertEquals(actual.get(0), "aaa");

            actual = StringUtils.splitReturnCode("aaa\rbbb");
            assertNotNull(actual);
            assertEquals(actual.size(), 2);
            assertEquals(actual.get(0), "aaa");
            assertEquals(actual.get(1), "bbb");

            actual = StringUtils.splitReturnCode("aaa\r\rccc");
            assertNotNull(actual);
            assertEquals(actual.size(), 3);
            assertEquals(actual.get(0), "aaa");
            assertEquals(actual.get(1), "");
            assertEquals(actual.get(2), "ccc");
        }

        { /* 改行コードあり */
            List<String> actual;

            actual = StringUtils.splitReturnCode(" \r\n\n\r ");
            assertNotNull(actual);
            assertEquals(actual.size(), 4);
            assertEquals(actual.get(0), " ");
            assertEquals(actual.get(1), "");
            assertEquals(actual.get(2), "");
            assertEquals(actual.get(3), " ");

            actual = StringUtils.splitReturnCode("aaa\r\nbbb\nccc\rddd");
            assertNotNull(actual);
            assertEquals(actual.size(), 4);
            assertEquals(actual.get(0), "aaa");
            assertEquals(actual.get(1), "bbb");
            assertEquals(actual.get(2), "ccc");
            assertEquals(actual.get(3), "ddd");
        }
    }

    @Test
    public void convertJsonPropertyTest() throws Exception {
		{ // 大文字
	        final String target = "{" +
	            "\"TEST1\":1,\"TEST2\":\"VALUE2\",\"TEST_KEY3\":\"TEST_VALUE3\",\"TEST_KEY4\":\"TEST_VALUE4\",\"TEST_KEY5\":\"TEST_VALUE5\"," +
	            "\"TESTKEY6\":\"TESTVALUE6\",\"TESTKEY7\":\"TESTVALUE7\"" +
	            "}";
	        System.out.println("StringUtils.convertJsonProperty: " + StringUtils.convertJsonProperty(target));

	        final String expect = "{" +
	            "\"test1\":1,\"test2\":\"VALUE2\",\"testKey3\":\"TEST_VALUE3\",\"testKey4\":\"TEST_VALUE4\",\"testKey5\":\"TEST_VALUE5\"," +
	            "\"testkey6\":\"TESTVALUE6\",\"testkey7\":\"TESTVALUE7\"" +
	            "}";

	        assertEquals(StringUtils.convertJsonProperty(target), expect);
		}

		{ // 小文字
	        final String target = "{" +
	            "\"test1\":1,\"test2\":\"value2\",\"test_key3\":\"test_value3\",\"test_Key4\":\"test_Value4\",\"Test_Key5\":\"Test_Value5\"," +
	            "\"testKey6\":\"testValue6\",\"TestKey7\":\"TestValue7\"" +
	            "}";
	        System.out.println("StringUtils.convertJsonProperty: " + StringUtils.convertJsonProperty(target));

	        final String expect = "{" +
	            "\"test1\":1,\"test2\":\"value2\",\"testKey3\":\"test_value3\",\"testKey4\":\"test_Value4\",\"testKey5\":\"Test_Value5\"," +
	            "\"testkey6\":\"testValue6\",\"testkey7\":\"TestValue7\"" +
	            "}";

	        assertEquals(StringUtils.convertJsonProperty(target), expect);
		}

    }

    @Test
    public void encryptTest() throws Exception {
        System.out.println("StringUtils.encrypt(\"1234567890\") : " + StringUtils.encrypt("1234567890"));
        System.out.println("StringUtils.encrypt(\"password\") : " + StringUtils.encrypt("password"));
        System.out.println("StringUtils.encrypt(\"pass\") : " + StringUtils.encrypt("pass"));
        System.out.println("StringUtils.encrypt(\"2FP$ eL-kx}2Ni}6fq\") : " + StringUtils.encrypt("2FP$ eL-kx}2Ni}6fq"));

        assertEquals(StringUtils.encrypt("pass"), "YYnIWK_2AafjfvkKaqKRwA==");
        assertEquals(StringUtils.encrypt("2FP$ eL-kx}2Ni}6fq"), "eoMintzQihaFD80nlRHOumEwKOIDntSY5tFm8dh_Q6s=");

        assertNotEquals(StringUtils.encrypt("1234567890"), "1234567890");
    }

    @Test
    public void decryptTest() throws Exception {
        assertEquals(StringUtils.decrypt("YYnIWK_2AafjfvkKaqKRwA=="), "pass");
        assertEquals(StringUtils.decrypt("eoMintzQihaFD80nlRHOumEwKOIDntSY5tFm8dh_Q6s="), "2FP$ eL-kx}2Ni}6fq");

        assertEquals(StringUtils.decrypt("1234567890"), "1234567890");
    }
}
