package cc.sfclub.polar.utils;

import lombok.Getter;

import java.math.BigDecimal;

public class MathUtils {
    @Getter
    private static final MathUtils instance = new MathUtils();

    private MathUtils() {
    }

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
