package com.liyi.sutils.utils.network.nohttp;


import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;

public class SCallNetUtil {
    private static RequestQueue mRequestQueue;

    private SCallNetUtil() {
        super();
    }

    public static SCallNetUtil get() {
        return SCallNetUtil.SCallNetHolder.INSTANCE;
    }

    private static final class SCallNetHolder {
        private static final SCallNetUtil INSTANCE = new SCallNetUtil();
    }

    public <T> void sendRequest(int what, Object cancelsign, Request<T> request, OnResponseListener<T> onResponseListener) {
        request.cancelBySign(cancelsign);
        mRequestQueue.add(what, request, onResponseListener);
    }

    /**
     * 检查请求队列，防止未空
     *
     * @return
     */
    public void checkRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = NoHttp.newRequestQueue();
        }
    }


    /**
     * 取消指定请求
     *
     * @param canclesign 标记
     */
    public void cancelBySign(Object canclesign) {
        checkRequestQueue();
        mRequestQueue.cancelBySign(canclesign);
    }

    /**
     * 取消所有请求
     */
    public void cancelAll() {
        checkRequestQueue();
        mRequestQueue.cancelAll();
    }

    /**
     * 停止所有请求
     */
    public void stopAll() {
        checkRequestQueue();
        mRequestQueue.stop();
    }
}
