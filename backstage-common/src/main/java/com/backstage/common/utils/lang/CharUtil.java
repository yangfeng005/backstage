package com.backstage.common.utils.lang;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具
 *
 * @author yangfeng
 */
public class CharUtil {

    /**
     * 判断字符串中是否包含中文
     *
     * @param str 待校验字符串
     * @return 是否为中文
     * @warn 不能校验是否为中文标点符号
     */
    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    /**
     * 将字符串转为List
     *
     * @param str
     * @return
     */
    public static List<Integer> castStringToList(String str) {
        List<Integer> result = new ArrayList<>();
        char[] strArray = str.toCharArray();
        if (strArray != null && strArray.length > 0) {
            for (char s : strArray) {
                result.add(new Integer(String.valueOf(s)));
            }
        }
        return result;
    }
}
