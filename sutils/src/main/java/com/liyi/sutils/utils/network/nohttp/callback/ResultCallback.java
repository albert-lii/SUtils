package com.liyi.sutils.utils.network.nohttp.callback;

import android.text.TextUtils;

import com.liyi.sutils.constants.SConstants;
import com.liyi.sutils.utils.log.LogUtil;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

import java.net.HttpURLConnection;

/**
 * nohttp网络请求回调
 */
public abstract class ResultCallback<T> implements OnResponseListener<T> {
    private final String TAG = this.getClass().getSimpleName();
    private OnNetStateListener mNetStateListener;

    public ResultCallback(OnNetStateListener netStateListener) {
        super();
        this.mNetStateListener = netStateListener;
    }

    @Override
    public void onStart(int what) {
        loadAction(what, SConstants.LOAD_START);
    }

    @Override
    public void onSucceed(int what, Response<T> response) {
        int reapondCode = response.responseCode();
        if (reapondCode == HttpURLConnection.HTTP_OK || reapondCode == 304) {
            if (TextUtils.isEmpty(response.get().toString())) {
                showTips(what, SConstants.TIP_EMPTY, response);
                return;
            }
            if (mNetStateListener != null) {
                mNetStateListener.onSucceed(what, response.get());
            }
        } else {
            if (mNetStateListener != null) {
                mNetStateListener.onFailed(what, response);
            }
            showTips(what, SConstants.TIP_SUCCESS, response);
            String msg = String.format("http request success2fail ---> <what> is %s, <requestCode> is %s, <response> is %s",
                    String.valueOf(what), String.valueOf(reapondCode), response.get().toString());
            LogUtil.e(TAG, msg);
        }
    }

    @Override
    public void onFailed(int what, Response<T> response) {
        if (mNetStateListener != null) {
            mNetStateListener.onFailed(what, response);
        }
        showTips(what, SConstants.TIP_FAIL, response);
        Exception exception = response.getException();
        int responseCode = response.responseCode();
        String msg = String.format("http request failed ---> <what> is %s , <responseCode> is %s, <exception> is %s",
                String.valueOf(what), String.valueOf(responseCode), exception.getMessage());
        LogUtil.e(TAG, msg);
    }

    @Override
    public void onFinish(int what) {
        loadAction(what, SConstants.LOAD_FINISH);
    }

    /**
     * 网络加载数据时的操作
     *
     * @param what
     * @param flag
     */
    public abstract void loadAction(int what, int flag);

    /**
     * 根据网络请求的结果做提示
     *
     * @param what
     * @param flag
     * @param response
     */
    public abstract void showTips(int what, int flag, Response response);
}
