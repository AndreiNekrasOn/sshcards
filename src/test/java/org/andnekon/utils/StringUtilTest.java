package org.andnekon.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/** TestStringUtil */
public class StringUtilTest {

    @Test
    void testWrap() {
        String input, expected, actual;
        int width;
        input = "Hello,\nMy name is Andrei Nekrasov.\nThis is\nmy wrap string function\n";
        width = 6;
        expected =
                "Hello,\n"
                        + "My nam\n"
                        + "e is A\n"
                        + "ndrei \n"
                        + "Nekras\n"
                        + "ov.\n"
                        + "This i\n"
                        + "s\n"
                        + "my wra\n"
                        + "p stri\n"
                        + "ng fun\n"
                        + "ction\n";
        actual = StringUtil.wrap(input, width);
        assertEquals(expected, actual);
        ;
    }

    @Test
    void testCountSubstr() {
        String input, substr;
        int expected, actual;
        input = "helloheheeh";
        substr = "he";
        expected = 3;
        actual = StringUtil.countSubstr(input, substr);
        assertEquals(expected, actual);
    }

    @Test
    void testCountChar() {
        String input, substr;
        int expected, actual;
        input = "helloheheeh";
        substr = "h";
        expected = 4;
        actual = StringUtil.countSubstr(input, substr);
        assertEquals(expected, actual);
    }
}
