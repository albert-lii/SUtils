package com.liyi.sutils.utils.network.nohttp;


import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;

public class CallServer {
    private static RequestQueue mRequestQueue;

    private CallServer() {
        super();
        checkRequestQueue();
    }

    public static CallServer get() {
        return SCallServerHolder.INSTANCE;
    }

    private static final class SCallServerHolder {
        private static final CallServer INSTANCE = new CallServer();
    }

    public <T> void sendRequest(int what, Request<T> request, OnResponseListener<T> onResponseListener) {
        mRequestQueue.add(what, request, onResponseListener);
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
    private void checkRequestQueue() {
        if (mRequestQueue == null) {
            // 设置五个并发，此处可以传入并发数量。
            mRequestQueue = NoHttp.newRequestQueue(5);
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
    public void stopQueue() {
        checkRequestQueue();
        // 完全退出app时，调用这个方法释放CPU。
        mRequestQueue.stop();
    }

    /**
     * 恢复请求队列
     */
    public void startQueue() {
        checkRequestQueue();
        mRequestQueue.start();
    }

    /**
     * 获取请求队列
     *
     * @return
     */
    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }
}
