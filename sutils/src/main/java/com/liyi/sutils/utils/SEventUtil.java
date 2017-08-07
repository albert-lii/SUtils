package com.liyi.sutils.utils;


import com.liyi.sutils.utils.prompt.SLogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.meta.SubscriberInfoIndex;

public class SEventUtil {
    private static final String TAG = SEventUtil.class.getSimpleName();

    /**
     * Use index acceleration
     * <p>
     * Recommended for use in application
     *
     * @param index
     */
    public static void installIndex(SubscriberInfoIndex index) {
        EventBus.builder().addIndex(index).installDefaultEventBus();
    }

    /**
     * Register eventbus
     *
     * @param subscriber
     */
    public static void register(Object subscriber) {
        if (!EventBus.getDefault().isRegistered(subscriber)) {
            EventBus.getDefault().register(subscriber);
        } else {
            SLogUtil.e(TAG, "Failed to register eventbus");
        }
    }

    /**
     * Unregister the eventbus
     *
     * @param subscriber
     */
    public static void unregister(Object subscriber) {
        EventBus.getDefault().unregister(subscriber);
    }

    /**
     * Publish a subscription event
     * <p>
     * you must register first, then publish the event to receive;
     * It's kind of like the startActivityForResult method.
     */
    public static void post(Object event) {
        EventBus.getDefault().post(event);
    }

    /**
     * Publish sticky subscription events (you can publish events first, after registration, and then receive)
     * <p>
     * StickyEvent keeps the latest information in memory, cancels the original message, performs the latest news,
     * only after registration, and if it is not registered, the message will remain in memory
     */
    public static void postSticky(Object event) {
        EventBus.getDefault().postSticky(event);
    }

    /**
     * Removes the specified sticky subscription event
     *
     * @param eventType
     */
    public static <T> void removeStickyEvent(Class<T> eventType) {
        T stickyEvent = EventBus.getDefault().getStickyEvent(eventType);
        if (stickyEvent != null) {
            EventBus.getDefault().removeStickyEvent(stickyEvent);
        }
    }

    /**
     * Remove all sticky subscription events
     */
    public static void removeAllStickyEvents() {
        EventBus.getDefault().removeAllStickyEvents();
    }

    /**
     * Abort event passing, and subsequent events are not called (
     * <p>
     * only invoked when the event is passed
     *
     * @param event
     */
    public static void cancelEventDelivery(Object event) {
        EventBus.getDefault().cancelEventDelivery(event);
    }

    public EventBus getEventBus() {
        return EventBus.getDefault();
    }
}
