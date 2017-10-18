package com.liyi.sutils.utils.network.nohttp.callback;


/**
 * 解析nohttp网络请求返回的数据
 *
 * @param <T> nohttp网络请求返回的数据的类型
 */
public interface OnParseResultListener<K,T> {
    K onParseResult(int what, T t);
}
