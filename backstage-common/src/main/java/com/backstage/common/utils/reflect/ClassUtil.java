package com.backstage.common.utils.reflect;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 类操作工具类
 *
 * @author yangfeng
 */
public class ClassUtil {

    /**
     * 获取所有属性字段，包含父类
     *
     * @param object
     * @return
     */
    public static Field[] getAllFields(Object object) {
        Class clazz = object.getClass();
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null) {
            fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        return fields;
    }


    /**
     * 根据类名获取属性
     *
     * @param fieldName
     * @param allFields
     * @return
     */
    public static Field getField(String fieldName, Field[] allFields) {
        if (allFields != null && allFields.length > 0) {
            for (Field field : allFields) {
                if (field.getName().equalsIgnoreCase(fieldName)) {
                    return field;
                }
            }
        }
        return null;
    }

    /**
     * 根据属性名设置属性值
     *
     * @param fieldName
     * @param object
     * @return
     */
    public static void setFieldValueByFieldName(String fieldName, Object object, String value) {
        try {
            Field[] allFields = getAllFields(object);
            // 获取该类的成员变量,包括父类字段
            Field f = getField(fieldName.toLowerCase(), allFields);
            // 取消语言访问检查
            f.setAccessible(true);
            // 给变量赋值
            f.set(object, value);
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
}
