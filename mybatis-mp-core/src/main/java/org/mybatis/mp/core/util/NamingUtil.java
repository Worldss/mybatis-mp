package org.mybatis.mp.core.util;

import java.util.Objects;

/**
 * 驼峰 下划线 工具类
 */
public final class NamingUtil {

    private static boolean isBlank(String str) {
        if (Objects.isNull(str)) {
            return true;
        } else if (StringPool.EMPTY.equals(str)) {
            return true;
        }
        return false;
    }

    /**
     * 字符串驼峰转下划线格式
     *
     * @param name
     * @return
     */
    public static String camelToUnderline(String name) {
        if (isBlank(name)) {
            return name;
        }
        int length = name.length();
        StringBuilder sb = new StringBuilder(length + length / 4);
        for (char c : name.toCharArray()) {
            char lower = Character.toLowerCase(c);
            if (lower != c && sb.length() > 0) {
                sb.append(StringPool.UNDERLINE);
            }
            sb.append(lower);
        }
        return sb.toString();
    }

    /**
     * 字符串下划线转驼峰格式
     *
     * @param name
     * @return
     */
    public static String underlineToCamel(String name) {
        if (isBlank(name)) {
            return name;
        }
        int length = name.length();
        StringBuilder sb = new StringBuilder(length);
        boolean matchedUnderline = false;
        for (char c : name.toCharArray()) {
            if (matchedUnderline) {
                if (c == StringPool.UNDERLINE) {
                    continue;
                }
                sb.append(Character.toUpperCase(c));
                matchedUnderline = false;
            } else {
                matchedUnderline = c == StringPool.UNDERLINE;
                if (!matchedUnderline) {
                    sb.append(c);
                }
            }

        }
        return sb.toString();
    }

    /**
     * 首字母小写
     *
     * @return
     */
    public static String firstToLower(String name) {
        if (isBlank(name)) {
            return name;
        }

        char first = name.charAt(0);
        if (Character.isLowerCase(first)) {
            return name;
        }

        char[] chars = name.toCharArray();
        chars[0] = Character.toLowerCase(first);
        return new String(chars);
    }

    public static void main(String[] args) {
        System.out.println(camelToUnderline("UserTable"));
        System.out.println(camelToUnderline("user_table"));
        System.out.println(camelToUnderline("User_table"));

        System.out.println(underlineToCamel("UserTable"));
        System.out.println(underlineToCamel("user_table"));
        System.out.println(underlineToCamel("User_table"));
    }
}
