package org.andnekon.utils;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import java.util.List;

public class KeyStrokeUtil {

    private KeyStrokeUtil() {}

    public static boolean isLeftMotion(KeyStroke key) {
        return compareType(key, KeyType.ArrowLeft) || compareChars(key, "ha");
    }

    public static boolean isDownMotion(KeyStroke key) {
        return compareType(key, KeyType.ArrowDown) || compareChars(key, "js");
    }

    public static boolean isUpMotion(KeyStroke key) {
        return compareType(key, KeyType.ArrowUp) || compareChars(key, "kw");
    }

    public static boolean isRightMotion(KeyStroke key) {
        return compareType(key, KeyType.ArrowRight) || compareChars(key, "ld");
    }

    public static boolean compareChar(KeyStroke key, char c) {
        return key != null && compareType(key, KeyType.Character) && key.getCharacter().equals(c);
    }

    public static boolean compareChars(KeyStroke key, String s) {
        return s.chars().anyMatch(c -> compareChar(key, (char) c));
    }

    public static boolean compareType(KeyStroke key, KeyType type) {
        return key != null && key.getKeyType().equals(type);
    }

    public static boolean isControl(KeyStroke key) {
        if (key == null) {
            return false;
        }
        return switch (key.getKeyType()) {
            case Backspace, Delete, Enter, Escape -> true;
            default -> false;
        };
    }

    public static boolean isCharacter(KeyStroke key) {
        if (key == null) {
            return false;
        }
        return key.getKeyType() == KeyType.Character;
    }

    public static String keysToString(List<KeyStroke> keys) {
        StringBuilder sb = new StringBuilder();
        for (var key : keys) {
            if (isCharacter(key)) {
                sb.append(key.getCharacter());
            } else if (key.getKeyType() == KeyType.Enter) {
                sb.append('\n');
            }
        }
        return sb.toString();
    }
}
