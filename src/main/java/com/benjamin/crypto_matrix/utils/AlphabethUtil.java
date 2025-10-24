package com.benjamin.crypto_matrix.utils;

import java.util.HashMap;
import java.util.Map;

public class AlphabethUtil {
     
    private static final char[] ALPHABETS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ_".toCharArray();
    private static final Map<Character, Integer> CHAR_TO_INDEX = new HashMap<>();
    private static final Map<Integer, Character> INDEX_TO_CHAR = new HashMap<>();

    static {
        for (int i = 0; i < ALPHABETS.length; i++) {
            CHAR_TO_INDEX.put(ALPHABETS[i], i);
            INDEX_TO_CHAR.put(i, ALPHABETS[i]);
        }
    }

    public static int charToIndex(char c) {
        c = Character.toUpperCase(c);
        if (!CHAR_TO_INDEX.containsKey(c)) {
            throw new IllegalArgumentException("Unsupported character: " + c);
        }
        return CHAR_TO_INDEX.get(c);
    }

    public static char indexToChar(int index) {
        if (!INDEX_TO_CHAR.containsKey(index)) {
            throw new IllegalArgumentException("Invalid index: " + index);
        }
        return INDEX_TO_CHAR.get(index);
    }

    public static int size() {
        return ALPHABETS.length;
    }
}
