package com.olande.common.util;

import java.util.UUID;

/**
 * 字符串处理工具
 *
 * @author chengfr
 */
public final class StringUtils {

    public static final String EMPTY_STR = "";

    private StringUtils() {
        super();
    }

    /**
     * 将字符串中全角字符转成半角字符
     *
     * @param str
     * @return
     */
    public final static String converSBCtoDBC(String str) {
        if (null == str) {
            return str;
        }
        int length = str.length();
        int[] codePoints = new int[length];
        for (int i = 0; i < length; i++) {
            if (str.codePointAt(i) == 12288) {
                codePoints[i] = str.codePointAt(i) - 12256; //ASCII码对应的数据相减
                continue;
            }
            if (str.codePointAt(i) > 65280 && str.codePointAt(i) < 65375) {
                codePoints[i] = str.codePointAt(i) - 65248;
            } else {
                codePoints[i] = str.codePointAt(i);
            }
        }
        return new String(codePoints, 0, length);
    }

    /**
     * 过滤字符串所有空格
     *
     * @param str
     * @return
     */
    public final static String filterAllBlank(String str) {
        if (null == str) {
            return str;
        }
        char bc = ' ';
        str = converSBCtoDBC(str);
        String bStr = String.valueOf(bc);
        str = str.replaceAll(bStr, bStr.trim());//trim()方法去空格
        return str;
    }

    /**
     * 字符串转基本数据类型int
     *
     * @param str
     * @return
     */
    public final static int converToInt(String str) {
        char bc = ' ';
        String bStr = String.valueOf(bc);
        str = str.replaceAll(bStr, bStr.trim());
        return Integer.valueOf(str).intValue();
    }

    /**
     * 字符串转基本数据类型long
     *
     * @param str
     * @return
     */
    public final static long converToLong(String str) {
        char bc = ' ';
        String bStr = String.valueOf(bc);
        str = str.replaceAll(bStr, bStr.trim());
        return Long.valueOf(str);
    }

    /**
     * 判断是否为空字符串
     *
     * @param str
     * @return
     */
    public final static boolean emptyStr(String str) {
        boolean isEmpty = null == str;
        if (!isEmpty) {
            str = filterAllBlank(str);
            isEmpty = 0 == str.length();
        }
        return isEmpty;
    }

    public final static boolean emptyStrs(String... strs) {
        if (strs == null || 0 == strs.length) {
            return true;
        }
        for (String str : strs) {
            if (emptyStr(str)) {
                return true;
            }
        }
        return false;
    }



    /**
     * 字符串首字母大写
     *
     * @param str
     * @return
     */
    public final static String firstCharToUpperCase(String str) {
        if (null == str) {
            return null;
        }
        String fCh = String.valueOf(Character.toUpperCase(str.charAt(0)));
        return fCh + str.substring(1);
    }

    /**
     * 获取空字符串
     *
     * @return
     */
    public final static String getEmptyStr() {
        return String.valueOf(' ').trim();
    }

    /**
     * 获取空json格式字符串:{}
     *
     * @return
     */
    public final static String getEmptyJsonStr() {
        return String.valueOf('{') + String.valueOf('}');
    }

    /**
     * Get 32 bit UUID
     *
     * @return
     */
    public final static String getUUID32() {
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();
        id = id.replaceAll("-", "");
        return id;
    }

    public final static String convertString(Object obj) {
        if (null == obj) {
            return "";
        }
        return obj.toString();
    }
}
