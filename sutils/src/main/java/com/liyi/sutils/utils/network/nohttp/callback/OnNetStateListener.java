package com.liyi.sutils.utils.network.nohttp.callback;


import com.yanzhenjie.nohttp.rest.Response;

/**
 * nohttp网络回调接口
 *
 * @param <T> 回调返回的数据类型
 */
public interface OnNetStateListener<T> {
    void onSucceed(int what, T t);

    void onFailed(int what, Response<T> response);
}
