package cc.sfclub.catcode;

import cc.sfclub.catcode.entities.At;
import cc.sfclub.catcode.entities.Image;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class CatCodeHelper {
    private static final Pattern number = Pattern.compile("^([-+])?\\d+(\\.\\d+)?E?$");

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

    /**
     * Parse "at" to a entity.
     *
     * @param strAt CatCodeHelper
     * @return At
     */
    public static At parseAt(String strAt) {
        if (!(strAt.startsWith("[") && strAt.endsWith("]"))) {
            return null;
        }
        String str = strAt.replaceFirst("\\[", "");
        str = str.replaceAll("]", "");
        if ("AtAll".equals(str)) {
            At at = new At();
            at.all = true;
            return at;
        }
        String[] args = str.split(",");
        At at = new At();
        long usrId;
        String[] a;
        if (args.length == 0) {
            a = str.split(":");
        } else {
            a = args[0].split(":");
        }
        if (!isNumeric(a[1])) {
            return null;
        }
        usrId = Long.parseLong(a[1]);
        at.userId = usrId;
        return at;
    }

    /**
     * Parse Img to Entity
     *
     * @param strImg cCode
     * @return image entity
     */
    public static Image parseImg(String strImg) {
        if (!(strImg.startsWith("[") && strImg.endsWith("]"))) {
            return null;
        }
        String str = strImg.replaceFirst("\\[", "");
        str = str.replaceAll("]", "");
        String[] args = str.split(",");
        Image img = new Image();
        String[] a;
        if (args.length == 0) {
            a = str.split(":");
        } else {
            a = args[0].split(":");
        }
        if (a[1].endsWith(".jpg") || a[1].endsWith(".png")) {
            img.ID = a[1];
            return img;
        }
        img.URI = Base64.URLSafe.decode(a[1]);
        return img;
    }

    /**
     * return true if str is number.
     *
     * @param str string
     * @return result
     */
    public static boolean isNumeric(String str) {
        return number.matcher(str).matches();
    }
}