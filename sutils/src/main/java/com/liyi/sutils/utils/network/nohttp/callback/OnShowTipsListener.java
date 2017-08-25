package com.liyi.sutils.utils.network.nohttp.callback;

import com.yanzhenjie.nohttp.rest.Response;

/**
 * Created by albertlii on 2017/8/25.
 */

public interface OnShowTipsListener {
    /**
     * @param what
     * @param flag     0: empty data   1: success  2: fail
     * @param response
     */
    void onShowTips(int what, int flag, Response response);
}
