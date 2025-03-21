package org.andnekon.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.andnekon.utils.StringUtil;
import org.junit.jupiter.api.Test;


/**
 * TestStringUtil
 */
public class StringUtilTest {

    @Test
    void testWrap() {
        String input, expected, actual;
        int width;
        input = "Hello,\nMy name is Andrei Nekrasov.\nThis is\nmy wrap string function\n"; width = 6;
        expected = "Hello,\nMy nam\ne is A\nndrei \nNekras\nov.\nThis i\ns\nmy wra\np stri\nng fun\nction\n";
        actual = StringUtil.wrap(input, width);
        assertEquals(expected, actual);;
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
