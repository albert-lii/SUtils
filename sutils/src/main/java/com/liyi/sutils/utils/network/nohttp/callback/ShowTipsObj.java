package com.liyi.sutils.utils.network.nohttp.callback;

import android.content.Context;

import com.liyi.sutils.R;
import com.liyi.sutils.constants.SConstants;
import com.liyi.sutils.utils.prompt.SToastUtil;
import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.error.NotFoundCacheError;
import com.yanzhenjie.nohttp.error.TimeoutError;
import com.yanzhenjie.nohttp.error.URLError;
import com.yanzhenjie.nohttp.error.UnKnownHostError;
import com.yanzhenjie.nohttp.rest.Response;

import java.net.ProtocolException;


public class ShowTipsObj implements OnShowTipsListener {
    private Context mContext;

    public ShowTipsObj(Context context) {
        super();
        this.mContext = context.getApplicationContext();
    }

    @Override
    public void onShowTips(int what, int flag, Response response) {
        if (flag == SConstants.TIP_EMPTY) {
            SToastUtil.show(mContext, "暂无数据");
        } else if (flag == SConstants.TIP_SUCCESS) {
            int reaponseCode = response.responseCode();
            if (reaponseCode == 400) {
                SToastUtil.show(mContext, R.string.network_request_failed);
            } else if (reaponseCode == 404) {
                SToastUtil.show(mContext, R.string.network_not_found_page);
            } else if (reaponseCode == 405) {
                SToastUtil.show(mContext, R.string.network_request_refused);
            } else if (reaponseCode == 0 || reaponseCode == 500) {
                SToastUtil.show(mContext, R.string.network_failed_to_connect_server);
            }
        } else if (flag == SConstants.TIP_FAIL) {
            if (mContext == null || response == null) {
                return;
            }
            Exception exception = response.getException();
            // 网络信号不好
            if (exception instanceof NetworkError) {
                SToastUtil.show(mContext, R.string.network_bad_signal);
            }
            // 请求超时
            else if (exception instanceof TimeoutError) {
                SToastUtil.show(mContext, R.string.network_timeout);
            }
            // 无法连接到服务器
            else if (exception instanceof UnKnownHostError) {
                SToastUtil.show(mContext, R.string.network_failed_to_connect_server);
            }
            // URL是错的
            else if (exception instanceof URLError) {
                SToastUtil.show(mContext, R.string.network_error_url);
            }
            // 这个异常只会在仅仅查找缓存时没有找到缓存时返回
            else if (exception instanceof NotFoundCacheError) {
                SToastUtil.show(mContext, R.string.network_not_found_cache);
            }
            // 系统不支持的请求方法
            else if (exception instanceof ProtocolException) {
                SToastUtil.show(mContext, R.string.network_system_unsupport_method);
            }
            // 未知的错误
            else {
                SToastUtil.show(mContext, exception.getMessage());
            }
        }
    }
}
