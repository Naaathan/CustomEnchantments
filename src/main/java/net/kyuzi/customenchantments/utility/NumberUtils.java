package net.kyuzi.customenchantments.utility;

import java.util.Hashtable;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberUtils {

    // Converting from roman numerals
    private final static Pattern pattern = Pattern.compile("M|CM|D|CD|C|XC|L|XL|X|IX|V|IV|I");
    private final static Hashtable<String, Long> romanToValue = new Hashtable<>();

    // Converting to roman numerals
    private final static TreeMap<Long, String> valueToRoman = new TreeMap<>();

    static {
        // Converting from roman numerals
        romanToValue.put("M", 1000L);
        romanToValue.put("CM", 900L);
        romanToValue.put("D", 500L);
        romanToValue.put("CD", 400L);
        romanToValue.put("C", 100L);
        romanToValue.put("XC", 90L);
        romanToValue.put("L", 50L);
        romanToValue.put("XL", 40L);
        romanToValue.put("X", 10L);
        romanToValue.put("IX", 9L);
        romanToValue.put("V", 5L);
        romanToValue.put("IV", 4L);
        romanToValue.put("I", 1L);

        // Converting to roman numerals
        valueToRoman.put(1000L, "M");
        valueToRoman.put(900L, "CM");
        valueToRoman.put(500L, "D");
        valueToRoman.put(400L, "CD");
        valueToRoman.put(100L, "C");
        valueToRoman.put(90L, "XC");
        valueToRoman.put(50L, "L");
        valueToRoman.put(40L, "XL");
        valueToRoman.put(10L, "X");
        valueToRoman.put(9L, "IX");
        valueToRoman.put(5L, "V");
        valueToRoman.put(4L, "IV");
        valueToRoman.put(1L, "I");
    }


    public static int convertFromRoman(String value) {
        if (value == null || value.isEmpty() || !value.matches("^(M{0,3})(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$")) {
            return -1;
        }

        Matcher matcher = pattern.matcher(value);
        int result = 0;

        while (matcher.find()) {
            for (Map.Entry<String, Long> romanNumeral : romanToValue.entrySet()) {
                if (romanNumeral.getKey().equals(matcher.group(0))) {
                    result += romanNumeral.getValue();
                }
            }
        }

        return result;
    }

    public static String convertToRoman(long value) {
        long l = valueToRoman.floorKey(value);

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

    public static boolean isLong(String value) {
        try {
            Long.parseLong(value);
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
