package com.liyi.sutils.utils.network.nohttp.callback;


/**
 * 解析nohttp网络请求返回的数据
 *
 * @param <K> nohttp网络请求返回的数据的类型
 * @param <V> 解析网络请求数据后返回的结果
 */
public interface OnParseResultListener<K,V> {
    V onParseResult(int what, K k);
}
