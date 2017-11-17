package com.liyi.sutils.utils;

import android.annotation.SuppressLint;

import java.lang.reflect.Field;


/**
 * 反射工具类
 */
public class ReflectUtil {
    /**
     * 修改单个的值
     *
     * @param clz   域所在的类
     * @param key   域的名称（即属性名称）
     * @param value 修改后的值
     */
    @SuppressLint("WrongConstant")
    public static void modifyValNoFinal(Class<?> clz, String key, Object value) {
        try {
            Field field = clz.getDeclaredField(key);
            // 将字段的访问权限设置为true，从而消除了私有修改器的影响
            field.setAccessible(true);
//            // 删除最终修饰符的效果，并设置字段可修改（测试结果无效）
//            Field modifiersField = Field.class.getDeclaredField("modifiers");
//            modifiersField.setAccessible(true);
//            modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
            field.set(clz, value);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 在类中修改多个字段值
     *
     * @param clz
     * @param oldStr
     * @param replaceStr
     */
    @SuppressLint("WrongConstant")
    public static void modifyValsNoFinal(Class<?> clz, String oldStr, String replaceStr) {
        try {
            // 获取实体类的所有属性并返回字段数组
            Field[] fields = clz.getFields();
            for (int i = 0; i < fields.length; i++) {
                // 获取属性名
                String name = fields[i].getName();
                // 获取属性类型
                String type = fields[i].getGenericType().toString();
                // 如果类型是类类型，则前面包含“类”，后面是类名
                if (type.equals("class java.lang.String")) {
                    Field field = fields[i];
                    field.setAccessible(true);
                    String value = field.get(null).toString();
                    if (value.contains(oldStr)) {
                        value = value.replace(oldStr, replaceStr);
                    }
                    field.set(null, value);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
