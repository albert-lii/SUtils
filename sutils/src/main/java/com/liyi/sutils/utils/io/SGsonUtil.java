package com.liyi.sutils.utils.io;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class SGsonUtil {
    private static Gson mGson = null;

    static {
        if (mGson == null) {
            mGson = new Gson();
        }
    }

    private static void checkGson() {
        if (mGson == null) {
            mGson = new Gson();
        }
    }

    /**
     * 将json数据转化为bean
     *
     * @param jsonStr
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T json2Bean(String jsonStr, Class<T> cls) {
        checkGson();
        T t = mGson.fromJson(jsonStr, cls);
        return t;
    }

    /**
     * 将json数据转换为list
     *
     * @param jsonStr
     * @param type
     * @param <T>
     * @return
     */
    public static <T> List<T> json2List(String jsonStr, Type type) {
        checkGson();
        List<T> list = mGson.fromJson(jsonStr, type);
        return list;
    }

    /**
     * 将json数据转化为map
     *
     * @param jsonStr
     * @param <T>
     * @return
     */
    public static <T> Map<String, T> json2Map(String jsonStr) {
        checkGson();
        Map<String, T> map = mGson.fromJson(jsonStr, new TypeToken<Map<String, T>>() {
        }.getType());
        return map;
    }

    /**
     * 将json数据转化为map元素的list
     *
     * @param jsonStr
     * @param <T>
     * @return
     */
    public static <T> List<Map<String, T>> json2ListMap(String jsonStr) {
        checkGson();
        List<Map<String, T>> list = mGson.fromJson(jsonStr, new TypeToken<List<Map<String, T>>>() {
        }.getType());
        return list;
    }

    /**
     * 将对象转换成string数据
     *
     * @param obj
     * @return
     */
    public static String obj2String(Object obj) {
        checkGson();
        return mGson.toJson(obj);
    }
}
