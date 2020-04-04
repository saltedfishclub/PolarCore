package cc.sfclub.polar.utils;

import java.util.LinkedList;
import java.util.List;

public class CatCode {
    /**
     * Spilt into array.
     * For example:
     * x[x]xx[x]xx ->
     * x
     * [x]
     * xx
     * [x]
     * xx
     *
     * @param str message contains catCode
     * @return string array
     */
    public static String[] spilt(String str) {
        char[] ch = str.toCharArray();
        List<String> list = new LinkedList<>();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            area_1:
            if (c == '[') {
                if (i > 0 && ch[i - 1] == '\\') break area_1;
                if (builder.length() > 0) list.add(builder.toString());
                builder = new StringBuilder();
                builder.append(c);
                continue;
            } else if (c == ']') {
                if (i > 0 && ch[i - 1] == '\\') break area_1;
                builder.append(c);
                list.add(builder.toString());
                builder = new StringBuilder();
                continue;
            }
            builder.append(c);
        }
        if (builder.length() > 0) list.add(builder.toString());
        String[] strs = new String[list.size()];
        for (int i = 0; i < strs.length; i++) {
            strs[i] = list.get(i);
        }
        return strs;
    }


}
