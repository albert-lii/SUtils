package com.liyi.sutils.utils;

import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Handler 相关工具类
 */
public class HandlerUtil {
    private static Handler mHandler;
    private static List<OnReceiveMessageListener> mReceiveMsgListenerList;

    private HandlerUtil() {
        init();
    }

    public static HandlerUtil getInstance() {
        return HandlerUtilHolder.INSTANCE;
    }

    private static final class HandlerUtilHolder {
        private static final HandlerUtil INSTANCE = new HandlerUtil();
    }

    private void init() {
        if (mReceiveMsgListenerList == null)
            mReceiveMsgListenerList = new ArrayList<OnReceiveMessageListener>();
        if (mHandler == null) {
            mHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    if (mReceiveMsgListenerList != null && mReceiveMsgListenerList.size() > 0) {
                        for (OnReceiveMessageListener listener : mReceiveMsgListenerList) {
                            listener.handlerMessage(msg);
                        }
                    }
                }
            };
        }
    }

    /**
     * 注册 Handler
     *
     * @param listener 收到消息回调接口
     */
    public void register(OnReceiveMessageListener listener) {
        init();
        mReceiveMsgListenerList.add(listener);
    }

    /**
     * 取消注册 Handler
     *
     * @param index 收到消息回调接口的下标
     */
    public void unregister(int index) {
        if (mReceiveMsgListenerList != null
                && index >= 0 && index < mReceiveMsgListenerList.size()) {
            mReceiveMsgListenerList.remove(index);
        }
    }

    /**
     * 取消注册 Handler
     *
     * @param listener 收到消息回调接口
     */
    public void unregister(OnReceiveMessageListener listener) {
        if (mReceiveMsgListenerList != null && listener != null) {
            mReceiveMsgListenerList.remove(listener);
        }
    }

    /**
     * 获取 Handler 对象
     *
     * @return
     */
    public Handler getHandler() {
        return mHandler;
    }

    /**
     * 获取所有事件
     *
     * @return 事件列表
     */
    public List<OnReceiveMessageListener> getReceiveMsgListenerList() {
        return mReceiveMsgListenerList;
    }

    /**
     * 清除所有事件
     */
    public void clear() {
        if (mReceiveMsgListenerList != null && mReceiveMsgListenerList.size() > 0) {
            mReceiveMsgListenerList.clear();
        }
    }

    /**
     * 收到消息回调接口
     */
    public interface OnReceiveMessageListener {
        void handlerMessage(Message msg);
    }
}
