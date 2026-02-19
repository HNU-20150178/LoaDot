package com.loadot.util;

public class DataUtil {

    /**
     *
     * @param value
     * @return
     */
    public static Long stringToLong(String value) {
        if (value == null || value.isEmpty()) return 0L;
        try {
            return Long.parseLong(value.replace(",", ""));
        } catch (NumberFormatException e) {
            return 0L;
        }
    }

    /**
     * "1,620.00" -> 1620.0 (Double 변환)
     * 아이템 레벨 변환용
     */
    public static Double parseLevelToDouble(String levelStr) {
        if (levelStr == null || levelStr.isEmpty()) return 0.0;
        try {
            return Double.parseDouble(levelStr.replace(",", ""));
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    /**
     * "150,000" -> 150000 (Long 변환)
     * 전투력 변환용
     * @param powerStr 전투력Stirng형태
     */
    public static Long parsePowerToLong(String powerStr) {
        if (powerStr == null || powerStr.isEmpty()) return 0L;
        try {
            // 소수점이 포함된 경우(예: 150000.00)를 대비해 먼저 Double로 바꾼 후 Long으로 캐스팅하거나
            // 단순히 콤마만 제거합니다.
            String cleanStr = powerStr.replace(",", "");
            if (cleanStr.contains(".")) {
                return (long) Double.parseDouble(cleanStr);
            }
            return Long.parseLong(cleanStr);
        } catch (NumberFormatException e) {
            return 0L;
        }
    }
}
