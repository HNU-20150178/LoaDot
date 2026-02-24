package com.loadot.util;

public class DataUtil {

    /**
     * "1,620.00" -> 1620.00 (Double 변환)
     * 아이템 레벨, 전투력 변환용
     */
    public static Double parseStringToDouble(String levelStr) {
        if (levelStr == null || levelStr.isEmpty()) return 0.0;
        try {
            return Double.parseDouble(levelStr.replace(",", ""));
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

}
