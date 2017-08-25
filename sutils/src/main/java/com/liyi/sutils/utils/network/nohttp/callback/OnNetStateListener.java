package com.liyi.sutils.utils.network.nohttp.callback;


import com.yanzhenjie.nohttp.rest.Response;

public interface OnNetStateListener<T> {
    void onSucceed(int what, T t);

    void onFailed(int what, Response<T> response);
}
