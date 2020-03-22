package cc.sfclub.polar.utils;

import lombok.Getter;

import java.math.BigDecimal;

public class MathUtils {
    @Getter
    private static final MathUtils instance = new MathUtils();

    private MathUtils() {
    }

    /**
     * return true if str is number.
     *
     * @param str string
     * @return result
     */
    public static boolean isNumeric(String str) {
        String bigStr;
        try {
            bigStr = new BigDecimal(str).toString();
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
