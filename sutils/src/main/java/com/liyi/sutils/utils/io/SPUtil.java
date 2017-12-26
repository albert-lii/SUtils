package com.liyi.sutils.utils.io;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.liyi.sutils.utils.SUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * SharedPreferences 工具类
 */
@SuppressLint("ApplySharedPref")
public class SPUtil {
    private static final String DEF_FILENAME = "SPUtil";
    private static final int DEF_MODE = Context.MODE_PRIVATE;
    /* 存储所有的 SharedPreferences 实例 */
    private static Map<String, SPUtil> mInstanceMap;
    private static SharedPreferences mSp;
    private static SharedPreferences.Editor mEditor;

    private SPUtil(String fileName, int mode) {
        super();
        mSp = SUtils.getApp().getSharedPreferences(fileName, mode);
        mEditor = mSp.edit();
    }

    public static SPUtil getInstance() {
        return getInstance(DEF_FILENAME, DEF_MODE);
    }

    public static SPUtil getInstance(@NonNull String fileName) {
        return getInstance(fileName, DEF_MODE);
    }

    public static SPUtil getInstance(@NonNull String fileName, @NonNull int mode) {
        if (mInstanceMap == null) {
            mInstanceMap = new HashMap<String, SPUtil>();
        }
        // 先获取 SharedPreferences 实例
        SPUtil manager = mInstanceMap.get(fileName + "_" + mode);
        // 如果获取不到，则重新创建
        if (manager == null) {
            manager = new SPUtil(fileName, mode);
            mInstanceMap.put(fileName + "_" + mode, manager);
        }
        return manager;
    }

    /**
     * 保存单个数据
     *
     * @param key    键值
     * @param object 存储的内容
     */
    public void put(String key, Object object) {
        if (object instanceof String) {
            mEditor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            mEditor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            mEditor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            mEditor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            mEditor.putLong(key, (Long) object);
        } else {
            mEditor.putString(key, object.toString());
        }
        mEditor.commit();
    }

    /**
     * 同时保存多条数据
     *
     * @param map 存储的数据
     */
    public void add(Map<String, Object> map) {
        Set<String> set = map.keySet();
        for (String key : set) {
            Object object = map.get(key);
            if (object instanceof String) {
                mEditor.putString(key, (String) object);
            } else if (object instanceof Integer) {
                mEditor.putInt(key, (Integer) object);
            } else if (object instanceof Boolean) {
                mEditor.putBoolean(key, (Boolean) object);
            } else if (object instanceof Float) {
                mEditor.putFloat(key, (Float) object);
            } else if (object instanceof Long) {
                mEditor.putLong(key, (Long) object);
            } else {
                mEditor.putString(key, object.toString());
            }
        }
        mEditor.commit();
    }

    /**
     * 获取存储的数据
     *
     * @param key    键值
     * @param object 默认返回值
     * @return 存储的内容
     */
    public Object get(String key, Object object) {
        if (object instanceof String) {
            return mSp.getString(key, (String) object);
        } else if (object instanceof Integer) {
            return mSp.getInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            return mSp.getBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            return mSp.getFloat(key, (Float) object);
        } else if (object instanceof Long) {
            return mSp.getLong(key, (Long) object);
        }
        return null;
    }

    /**
     * 根据 key 删除数据
     *
     * @param key 键值
     */
    public void delete(String key) {
        mEditor.remove(key);
        mEditor.commit();
    }

    /**
     * 清除所有的数据
     */
    public void clear() {
        mEditor.clear();
        mEditor.commit();
    }

    public SharedPreferences.Editor getEditor() {
        return mEditor;
    }
}
