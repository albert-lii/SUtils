package com.liyi.sutil.utils;

import android.annotation.SuppressLint;

import java.lang.reflect.Field;

public class SReflectUtil {
    /**
     * Modify the value of a single field
     *
     * @param clz   The class that the field is in
     * @param key   The field name
     * @param value Modified field values
     */
    @SuppressLint("WrongConstant")
    public static void modifyVal(Class<?> clz, String key, Object value) {
        try {
            Field field = clz.getField(key);
            // Set the access permission of the field to true, which removes the impact of the private modifier
            field.setAccessible(true);
//            // Remove the effects of the final modifier and set the fields to be modifiable
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
     * Modifying multiple field values in a class (which can be used to switch server environments)
     *
     * @param clz
     * @param oldStr
     * @param replaceStr
     */
    @SuppressLint("WrongConstant")
    public static void modifyVals(Class<?> clz, String oldStr, String replaceStr) {
        try {
            /** Gets all the properties of the entity class and returns the Field array */
            Field[] fields = clz.getFields();
            for (int i = 0; i < fields.length; i++) {
                // Get the name of the property
                String name = fields[i].getName();
                // Get the type of the property
                String type = fields[i].getGenericType().toString();
                // If type is a class type, the front contains "class", followed by the class name
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
