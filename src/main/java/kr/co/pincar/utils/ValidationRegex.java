package kr.co.pincar.utils;

import java.util.regex.Pattern;

public class ValidationRegex {

    public static boolean isRegexPhoneNumber(String target) {
        String pattern = "^\\d{3}-\\d{3,4}-\\d{4}$";
        boolean regex = Pattern.matches(pattern, target);
        return regex;
    }


    public static boolean isStringEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean isRegexDate(String target) {
        String pattern = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$";
        boolean regex = Pattern.matches(pattern, target);
        return regex;
    }
}
