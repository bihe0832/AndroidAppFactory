package com.bihe0832.android.lib.utils;

import android.text.TextUtils;

import java.util.List;
import java.util.regex.Pattern;

/**
 * 常用工具类
 */
public class ConvertUtils {


    /**
     * 解析字符串为整数, 转换出错返回指定默认值
     *
     * @param str
     * @param defaultValue
     * @return
     */
    public static int parseInt(String str, int defaultValue) {
        int value = defaultValue;

        if (!android.text.TextUtils.isEmpty(str)) {
            try {
                Pattern pattern = Pattern.compile("^[-\\+]?[\\d]+$");
                if (pattern.matcher(str).matches()) {
                    value = Integer.parseInt(str);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return value;
    }

    /**
     * 解析字符串为整数, 转换出错返回-1
     *
     * @param str
     * @return
     */
    public static int parseInt(String str) {
        return parseInt(str, -1);
    }

    /**
     * 解析字符串为整数, 转换出错返回指定默认值
     *
     * @param str
     * @param defaultValue
     * @return
     */
    public static long parseLong(String str, long defaultValue) {
        long value = defaultValue;
        if (!android.text.TextUtils.isEmpty(str)) {
            try {
                Pattern pattern = Pattern.compile("^[-\\+]?[\\d]+$");
                if (pattern.matcher(str).matches()) {
                    value = Long.parseLong(str);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return value;
    }

    /**
     * 解析字符串为整数, 转换出错返回指定默认值
     *
     * @param str
     * @param defaultValue
     * @return
     */
    public static boolean parseBoolean(String str, boolean defaultValue) {
        boolean value = defaultValue;
        if (!android.text.TextUtils.isEmpty(str)) {
            if ("true".equalsIgnoreCase(str) || "1".equalsIgnoreCase(str)) {
                return true;
            } else {
                return false;
            }
        }
        return value;
    }

    /**
     * 解析字符串为浮点数, 转换出错返回指定默认值
     *
     * @param str
     * @param defaultValue
     * @return
     */
    public static float parseFloat(String str, float defaultValue) {
        float value = defaultValue;

        if (!android.text.TextUtils.isEmpty(str)) {
            try {
                Pattern pattern = Pattern.compile("^[-+]?[\\d]*\\.?[\\d]+$");
                if (pattern.matcher(str).matches()) {
                    value = Float.parseFloat(str);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return value;
    }

    /**
     * 解析字符串为浮点数, 转换出错返回指定默认值
     *
     * @param str
     * @param defaultValue
     * @return
     */
    public static double parseDouble(String str, double defaultValue) {
        double value = defaultValue;
        if (!android.text.TextUtils.isEmpty(str)) {
            try {
                Pattern pattern = Pattern.compile("^[-+]?[\\d]*\\.?[\\d]+$");
                if (pattern.matcher(str).matches()) {
                    value = Double.parseDouble(str);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return value;
    }

    public static float[] floatArrayList2Array(List<Float> origin) {
        if (origin == null || origin.size() <= 0) {
            return null;
        }

        float[] result = new float[origin.size()];
        for (int i = 0; i < origin.size(); i++) {
            result[i] = origin.get(i);
        }

        return result;
    }

    public static long getUnsignedInt(int x) {
        return x & 0x00000000FFFFFFFFL;
    }

    public static String getSafeValueFromArray(String[] valueList, int index, String defaultValue) {
        if (valueList != null && index > -1 && index < valueList.length && !TextUtils.isEmpty(valueList[index])) {
            return valueList[index];
        }
        return defaultValue;
    }

    public static String getSafeValueFromList(List<String> valueList, int index, String defaultValue) {
        if (valueList != null && index > -1 && index < valueList.size() && !TextUtils.isEmpty(valueList.get(index))) {
            return valueList.get(index);
        }
        return defaultValue;
    }
}
