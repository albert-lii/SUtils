package com.liyi.sutil.parse;

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
     * Parse the json data into a bean
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
     * Parse the json data into a list
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
     * Parse the json data to map
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
     * Parse the json data to a list with map
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
     * Parse the object data to string
     *
     * @param obj
     * @return
     */
    public static String parse2String(Object obj) {
        checkGson();
        return mGson.toJson(obj);
    }
}
