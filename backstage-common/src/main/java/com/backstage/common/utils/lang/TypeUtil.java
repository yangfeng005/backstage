package com.backstage.common.utils.lang;

import java.util.Date;

public class TypeUtil {

    /**
     * object转为string
     *
     * @param o
     * @return
     */
    public static String objectToString(Object o) {
        if (o == null) {
            return null;
        }
        if (o instanceof Date) {
            return DateTimeUtil.format((Date) o, DateTimeUtil.YEAR_MONTH_DAY_HOUR_MINUTE_SECOND);
        } else {
            return String.valueOf(o);
        }
    }

}
