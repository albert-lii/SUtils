package com.liyi.sutils.utils.io;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;


/**
 * gson 工具类
 */
public class GsonUtil {
    private static Gson mGson;

    static {
        if (mGson == null) {
            mGson = new Gson();
        }
    }

    /**
     * 将 json 数据转化为 bean
     *
     * @param jsonStr json 字符串
     * @param cls     转换成的 bean 类型
     * @param <T>     返回的 bean 类型
     * @return bean
     */
    public static <T> T json2Bean(String jsonStr, Class<T> cls) {
        T t = mGson.fromJson(jsonStr, cls);
        return t;
    }

    /**
     * 将 json 数据转换为 list
     *
     * @param jsonStr json 字符串
     * @param type    转换成的对象类型
     * @param <T>     返回的类型
     * @return List<?>
     */
    public static <T> List<T> json2List(String jsonStr, Type type) {
        List<T> list = mGson.fromJson(jsonStr, type);
        return list;
    }

    /**
     * 将 json 数据转化为 map
     *
     * @param jsonStr json 字符串
     * @param <T>     转换成的 Map<String,?> 类型
     * @return Map<String,?>
     */
    public static <T> Map<String, T> json2Map(String jsonStr) {
        Map<String, T> map = mGson.fromJson(jsonStr, new TypeToken<Map<String, T>>() {
        }.getType());
        return map;
    }

    /**
     * 将 json 数据转化为 map 元素的 list
     *
     * @param jsonStr json 字符串
     * @param <T>     转换成的 List<Map<String,?>> 类型
     * @return List<Map<String,?>
     */
    public static <T> List<Map<String, T>> json2ListMap(String jsonStr) {
        List<Map<String, T>> list = mGson.fromJson(jsonStr, new TypeToken<List<Map<String, T>>>() {
        }.getType());
        return list;
    }

    /**
     * 将对象转换成 string 数据
     *
     * @param obj
     * @return string 数据
     */
    public static String obj2String(Object obj) {
        return mGson.toJson(obj);
    }
}
