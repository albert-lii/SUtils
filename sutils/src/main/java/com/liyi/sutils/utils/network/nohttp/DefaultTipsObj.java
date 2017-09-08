package com.liyi.sutils.utils.network.nohttp;

import android.content.Context;
import android.support.annotation.NonNull;

import com.liyi.sutils.R;
import com.liyi.sutils.constants.SConstants;
import com.liyi.sutils.utils.other.ToastUtil;
import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.error.NotFoundCacheError;
import com.yanzhenjie.nohttp.error.TimeoutError;
import com.yanzhenjie.nohttp.error.URLError;
import com.yanzhenjie.nohttp.error.UnKnownHostError;
import com.yanzhenjie.nohttp.rest.Response;

import java.net.ProtocolException;

/**
 * 封装有默认的网络回调提示的对象
 */
public class DefaultTipsObj {
    /**
     * 显示默认的网络回调提示
     *
     * @param context
     * @param what
     * @param flag
     * @param response
     */
    public static void showTips(@NonNull Context context, int what, int flag, Response response) {
        if (flag == SConstants.TIP_EMPTY) {
            ToastUtil.show(context, "暂无数据");
        } else if (flag == SConstants.TIP_SUCCESS) {
            int reaponseCode = response.responseCode();
            if (reaponseCode == 400) {
                ToastUtil.show(context, R.string.network_request_failed);
            } else if (reaponseCode == 404) {
                ToastUtil.show(context, R.string.network_not_found_page);
            } else if (reaponseCode == 405) {
                ToastUtil.show(context, R.string.network_request_refused);
            } else if (reaponseCode == 0 || reaponseCode == 500) {
                ToastUtil.show(context, R.string.network_failed_to_connect_server);
            }
        } else if (flag == SConstants.TIP_FAIL) {
            if (context == null || response == null) {
                return;
            }
            Exception exception = response.getException();
            // 网络信号不好
            if (exception instanceof NetworkError) {
                ToastUtil.show(context, R.string.network_bad_signal);
            }
            // 请求超时
            else if (exception instanceof TimeoutError) {
                ToastUtil.show(context, R.string.network_timeout);
            }
            // 无法连接到服务器
            else if (exception instanceof UnKnownHostError) {
                ToastUtil.show(context, R.string.network_failed_to_connect_server);
            }
            // URL是错的
            else if (exception instanceof URLError) {
                ToastUtil.show(context, R.string.network_error_url);
            }
            // 这个异常只会在仅仅查找缓存时没有找到缓存时返回
            else if (exception instanceof NotFoundCacheError) {
                ToastUtil.show(context, R.string.network_not_found_cache);
            }
            // 系统不支持的请求方法
            else if (exception instanceof ProtocolException) {
                ToastUtil.show(context, R.string.network_system_unsupport_method);
            }
            // 未知的错误
            else {
                ToastUtil.show(context, exception.getMessage());
            }
        }
    }
}
