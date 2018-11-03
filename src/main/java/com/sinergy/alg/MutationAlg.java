package com.sinergy.alg;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class MutationAlg {

    private static Pattern latin = Pattern.compile("[a-zA-Z]+");

    private static Pattern cyrillic = Pattern.compile("[а-яА-Я]+");

    public static Set<String> getMutations(String value) {
        Set<String> ret = new HashSet<>();

        if (isEnglish(value)) {
            for (char c = 'A'; c < 'Z'; c++) {
                ret.addAll(getMutationsForLetter(value, c));
            }
        } else {
            for (char c = 'А'; c < 'Я'; c++) {
                ret.addAll(getMutationsForLetter(value, c));
            }
        }

        return ret;
    }

    private static Set<String> getMutationsForLetter(String value, char letter) {
        Set<String> ret = new HashSet<>();
        for (int i = 0; i < value.length(); i++) {
            String n = value.substring(0, i) + letter + value.substring(i);
            String n2 = substring(value, 0, i) + letter + substring(value, i + 1, value.length());
            String n3 = substring(value, 0, i) + substring(value, i + 1, value.length());
            ret.add(n);
            ret.add(n2);
            ret.add(n3);
        }
        for (int i = 0; i < value.length() - 1; i++) {
            String begin = "";
            if (i > 0) {
                begin = value.substring(0, i);
            }
            String end = value.substring(i + 2);
            String res = begin + value.charAt(i + 1) + value.charAt(i) + end;
            ret.add(res);
        }

        ret.add(value + letter);

        return ret;
    }

    private static boolean isEnglish(String value) {
        return latin.matcher(value).find();
    }

    private static String substring(String value, int begin, int end) {
        if (end < 0 || begin > end) {
            return "";
        }
        return value.substring(begin, end);
    }


}
