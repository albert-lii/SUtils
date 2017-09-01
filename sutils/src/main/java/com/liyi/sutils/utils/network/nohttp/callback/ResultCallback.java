package com.liyi.sutils.utils.network.nohttp.callback;

import android.content.Context;
import android.text.TextUtils;

import com.liyi.sutils.constants.SConstants;
import com.liyi.sutils.utils.log.LogUtil;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

import java.net.HttpURLConnection;

/**
 * Created by albertlii on 2017/8/24.
 */

public class ResultCallback<T> implements OnResponseListener<T> {
    private final String TAG = this.getClass().getSimpleName();
    private OnLoadListener mLoadListener;
    private OnShowTipsListener mShowTipsListener;
    private OnNetStateListener mNetStateListener;

    public ResultCallback(OnNetStateListener netStateListener) {
        super();
        this.mNetStateListener = netStateListener;
    }

    public ResultCallback(OnNetStateListener netStateListener, Context context) {
        super();
        this.mNetStateListener = netStateListener;
        if (context != null) {
            mShowTipsListener = new ShowTipsObj(context);
        }
    }

    public ResultCallback setOnLoadListener(OnLoadListener loadListener) {
        this.mLoadListener = loadListener;
        return this;
    }

    public ResultCallback OnShowTipsListener(OnShowTipsListener showTipsListener) {
        this.mShowTipsListener = showTipsListener;
        return this;
    }

    @Override
    public void onStart(int what) {
        if (mLoadListener != null) {
            mLoadListener.showLoading(what, SConstants.LOAD_START);
        }
    }

    @Override
    public void onSucceed(int what, Response<T> response) {
        int reapondCode = response.responseCode();
        if (reapondCode == HttpURLConnection.HTTP_OK || reapondCode == 304) {
            if (TextUtils.isEmpty(response.get().toString())) {
                if (mShowTipsListener != null) {
                    mShowTipsListener.onShowTips(what, SConstants.TIP_EMPTY, response);
                }
                return;
            }
            if (mNetStateListener != null) {
                mNetStateListener.onSucceed(what, response.get());
            }
        } else {
            if (mNetStateListener != null) {
                mNetStateListener.onFailed(what, response);
            }
            if (mShowTipsListener != null) {
                mShowTipsListener.onShowTips(what, SConstants.TIP_SUCCESS, response);
            }
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
        if (mShowTipsListener != null) {
            mShowTipsListener.onShowTips(what, SConstants.TIP_FAIL, response);
        }
        Exception exception = response.getException();
        int responseCode = response.responseCode();
        String msg = String.format("http request failed ---> <what> is %s , <responseCode> is %s, <exception> is %s",
                String.valueOf(what), String.valueOf(responseCode), exception.getMessage());
        LogUtil.e(TAG, msg);
    }

    @Override
    public void onFinish(int what) {
        if (mLoadListener != null) {
            mLoadListener.showLoading(what, SConstants.LOAD_FINISH);
        }
    }
}
