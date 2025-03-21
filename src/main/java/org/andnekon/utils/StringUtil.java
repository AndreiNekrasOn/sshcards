package org.andnekon.utils;

/**
 * StringUtil
 */
public class StringUtil {

    /**
      * @param lines Text to be split. Can contain {@code '\n'}
      * @param width length of each line in the result text
      * @return lines, split into hyphenated lines of length {@code width}
      *
      */
    public static String wrap(String lines, int width) {
        StringBuilder sb = new StringBuilder();
        int cnt = 0;
        for (char s : lines.toCharArray()) {
            if (s == '\n') {
                cnt = 0;
                sb.append('\n');
                continue;
            } else if (cnt == width) {
                cnt = 0;
                sb.append('\n');
            }
            sb.append(s);
            cnt++;
        }
        return sb.toString();
    }

    // https://stackoverflow.com/a/770069
    public static int countSubstr(String str, String needle) {
        return str.split(needle, -1).length - 1;
    }

    public static int countChar(String str, char c) {
        return str.length() - str.replace(String.valueOf(c), "").length();
    }


}
