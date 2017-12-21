package com.liyi.sutils.utils;


import com.liyi.sutils.utils.log.LogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.meta.SubscriberInfoIndex;


/**
 * EventBus 3.0 工具类
 */
public final class EventBusUtil {
    private static final String TAG = EventBusUtil.class.getSimpleName();

    /**
     * 使用索引加速
     * <p>
     * 推荐在 application 中使用
     *
     * @param index
     */
    public static void installIndex(SubscriberInfoIndex index) {
        EventBus.builder().addIndex(index).installDefaultEventBus();
    }

    /**
     * 注册 eventbus
     *
     * @param subscriber 绑定对象
     */
    public static void register(Object subscriber) {
        if (!EventBus.getDefault().isRegistered(subscriber)) {
            EventBus.getDefault().register(subscriber);
        } else {
            LogUtil.e(TAG, "Failed to register eventbus");
        }
    }

    /**
     * 取消注册 eventbus
     *
     * @param subscriber 绑定对象
     */
    public static void unregister(Object subscriber) {
        EventBus.getDefault().unregister(subscriber);
    }

    /**
     * 发布一个订阅事件
     * <p>
     * 必须先注册，才能接收到发布的事件，有点类似于 startActivityForResult（）方法
     */
    public static void post(Object event) {
        EventBus.getDefault().post(event);
    }

    /**
     * 发布粘性事件（可以先发布事件，在注册后在接收）
     * <p>
     * 粘性事件将最新的信息保存在内存中，取消原始消息，执行最新的消息；
     * 只有注册后，才能接收消息，如果没有注册，消息将保留在内存中。
     */
    public static void postSticky(Object event) {
        EventBus.getDefault().postSticky(event);
    }

    /**
     * 移除指定的粘性订阅事件
     *
     * @param eventType 事件类型
     */
    public static <T> void removeStickyEvent(Class<T> eventType) {
        T stickyEvent = EventBus.getDefault().getStickyEvent(eventType);
        if (stickyEvent != null) {
            EventBus.getDefault().removeStickyEvent(stickyEvent);
        }
    }

    /**
     * 移除所有的粘性订阅事件
     */
    public static void removeAllStickyEvents() {
        EventBus.getDefault().removeAllStickyEvents();
    }

    /**
     * 优先级高的订阅者可以终止事件往下传递
     * <p>
     * 只有在事件通过时才能调用（即在事件接收方法中调用）
     *
     * @param event
     */
    public static void cancelEventDelivery(Object event) {
        EventBus.getDefault().cancelEventDelivery(event);
    }

    /**
     * 获取 eventbus 单例
     *
     * @return
     */
    public static EventBus getEventBus() {
        return EventBus.getDefault();
    }
}
