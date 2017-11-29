package org.deluxegaming.dxgmenchantments.utility;

import java.util.Hashtable;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberUtils {

    // Converting from roman numerals
    private final static Pattern pattern = Pattern.compile("M|CM|D|CD|C|XC|L|XL|X|IX|V|IV|I");
    private final static Hashtable<String, Integer> romanToValue = new Hashtable<>();

    // Converting to roman numerals
    private final static TreeMap<Integer, String> valueToRoman = new TreeMap<>();

    static {
        // Converting from roman numerals
        romanToValue.put("M", 1000);
        romanToValue.put("CM", 900);
        romanToValue.put("D", 500);
        romanToValue.put("CD", 400);
        romanToValue.put("C", 100);
        romanToValue.put("XC", 90);
        romanToValue.put("L", 50);
        romanToValue.put("XL", 40);
        romanToValue.put("X", 10);
        romanToValue.put("IX", 9);
        romanToValue.put("V", 5);
        romanToValue.put("IV", 4);
        romanToValue.put("I", 1);

        // Converting to roman numerals
        valueToRoman.put(1000, "M");
        valueToRoman.put(900, "CM");
        valueToRoman.put(500, "D");
        valueToRoman.put(400, "CD");
        valueToRoman.put(100, "C");
        valueToRoman.put(90, "XC");
        valueToRoman.put(50, "L");
        valueToRoman.put(40, "XL");
        valueToRoman.put(10, "X");
        valueToRoman.put(9, "IX");
        valueToRoman.put(5, "V");
        valueToRoman.put(4, "IV");
        valueToRoman.put(1, "I");
    }


    public static int convertFromRoman(String value) {
        if (value == null || value.isEmpty() || !value.matches("^(M{0,3})(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$")) {
            return -1;
        }

        Matcher matcher = pattern.matcher(value);
        int result = 0;

        while (matcher.find()) {
            for (Map.Entry<String, Integer> romanNumeral : romanToValue.entrySet()) {
                if (romanNumeral.getKey().equals(matcher.group(0))) {
                    result += romanNumeral.getValue();
                }
            }
        }

        return result;
    }

    public static String convertToRoman(int value) {
        int l = valueToRoman.floorKey(value);

        if (value == l) {
            return valueToRoman.get(value);
        }

        return valueToRoman.get(l) + convertToRoman(value - l);
    }

    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    public static boolean isRomanNumeral(String value) {
        for (char c : value.toCharArray()) {
            switch (c) {
                case 'M':
                case 'D':
                case 'C':
                case 'L':
                case 'X':
                case 'V':
                case 'I':
                    break;
                default:
                    return false;
            }
        }

        return true;
    }

}
