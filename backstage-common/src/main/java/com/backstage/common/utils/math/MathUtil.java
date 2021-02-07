package com.backstage.common.utils.math;


import java.math.BigDecimal;

/**
 * 计算工具类
 *
 * @author yangfeng
 */
public class MathUtil {


    /**
     * 两个数相除并四舍五入保留两位小数
     *
     * @param dividend 被除数
     * @param divider  除数
     * @return
     */
    public static Double divideAndRound(Double dividend, Double divider) {
        return divider > 0 ? new BigDecimal(dividend).divide(new BigDecimal(divider), 2,
                BigDecimal.ROUND_HALF_UP).doubleValue() : null;
    }

    /**
     * 两个数相除并四舍五入保留两位小数
     *
     * @param dividend
     * @param divider
     * @return
     */
    public static Double divideAndRound(BigDecimal dividend, BigDecimal divider) {
        return (divider != null && divider.doubleValue() > 0) ? dividend.divide(divider, 2,
                BigDecimal.ROUND_HALF_UP).doubleValue() : null;
    }


    /**
     * 两数相除取整
     *
     * @param dividend
     * @param divider
     * @return
     */
    public static Integer divide2Int(BigDecimal dividend, BigDecimal divider) {
        return (divider != null && divider.intValue() > 0) ? dividend.divide(divider, 0,
                BigDecimal.ROUND_HALF_UP).intValue() : null;
    }

    /**
     * 两个数相除并保留两位小数 返回百分比
     *
     * @param dividend
     * @param divider
     * @return
     */
    public static String dividePercent(BigDecimal dividend, BigDecimal divider) {
        Double result = divideAndRound(dividend.multiply(new BigDecimal(100)), divider);
        return result != null ? result + "%" : null;
    }

    /**
     * 两个数相除只保留整数 返回百分比
     *
     * @param dividend
     * @param divider
     * @return
     */
    public static String divide2IntPercent(BigDecimal dividend, BigDecimal divider) {
        Integer result = divide2Int(dividend.multiply(new BigDecimal(100)), divider);
        return result != null ? result + "%" : null;
    }

    /**
     * 两个数相加
     *
     * @return
     */
    public static int add(Integer number1, Integer number2) {
        BigDecimal bigDecimal1 = new BigDecimal(number1);
        BigDecimal bigDecimal2 = new BigDecimal(number2);
        return bigDecimal1.add(bigDecimal2).intValue();
    }

    public static Double add(Double number1, Double number2) {
        BigDecimal bigDecimal1 = new BigDecimal(number1);
        BigDecimal bigDecimal2 = new BigDecimal(number2);
        return bigDecimal1.add(bigDecimal2).doubleValue();
    }
}
