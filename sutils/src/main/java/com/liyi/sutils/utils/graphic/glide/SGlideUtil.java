package com.liyi.sutils.utils.graphic.glide;


import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.TransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.liyi.sutils.constants.SConstants;
import com.liyi.sutils.utils.log.SLogUtil;


public class SGlideUtil {
    private final String TAG = this.getClass().getSimpleName();

    private SGlideUtil() {
        super();
    }

    public static SGlideUtil get() {
        return SGlideUtil.SGlideUtilHolder.INSTANCE;
    }

    private static class SGlideUtilHolder {
        private static final SGlideUtil INSTANCE = new SGlideUtil();
    }

    public void loadImage(@NonNull Object target, Object source, ImageView imageView,
                          int type, float sizeMultiplier,
                          RequestOptions options, TransitionOptions transitionOptions,
                          RequestListener listener) {
        RequestBuilder builder = getRequestBuilder(getRequestManager(target), type);
        if (builder != null) {
            builder.load(source);
            if (options != null) {
                builder.apply(options);
            }
            if (sizeMultiplier >= 0 && sizeMultiplier <= 1) {
                builder.thumbnail(sizeMultiplier);
            }
            if (transitionOptions != null) {
                builder.transition(transitionOptions);
            }
            if (listener != null) {
                builder.listener(listener);
            }
            builder.into(imageView);
        }
    }

    public RequestBuilder getRequestBuilder(RequestManager manager, int type) {
        if (manager == null) {
            return null;
        }
        if (type == SConstants.AS_FILE) {
            return manager.asFile();
        } else if (type == SConstants.AS_BITMAP) {
            return manager.asBitmap();
        } else if (type == SConstants.AS_GIF) {
            return manager.asGif();
        } else if (type == SConstants.AS_DRAWABLE) {
            return manager.asDrawable();
        } else {
            return null;
        }
    }

    public RequestManager getRequestManager(@NonNull Object target) {
        RequestManager manager = null;
        if (target instanceof Activity) {
            manager = Glide.with((Activity) target);
        } else if (target instanceof FragmentActivity) {
            manager = Glide.with((FragmentActivity) target);
        } else if (target instanceof android.app.Fragment) {
            manager = Glide.with((android.app.Fragment) target);
        } else if (target instanceof android.support.v4.app.Fragment) {
            manager = Glide.with((android.support.v4.app.Fragment) target);
        } else if (target instanceof Context) {
            manager = Glide.with((Activity) target);
        } else if (target instanceof View) {
            manager = Glide.with((View) target);
        }
        if (manager == null) {
            SLogUtil.e(TAG, "Glide中引入的上下文对象格式错误");
        }
        return manager;
    }

    /**
     * 恢复请求，一般在停止滚动的时候
     */
    public void resumeRequests(Context context) {
        Glide.with(context).resumeRequests();
    }

    /**
     * 暂停请求 正在滚动的时候
     */
    public void pauseRequests(Context context) {
        Glide.with(context).pauseRequests();
    }

    /**
     * 清除磁盘缓存
     */
    public void clearDiskCache(final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 清理磁盘缓存 需要在子线程中执行
                Glide.get(context).clearDiskCache();
            }
        }).start();
    }

    /**
     * 清除内存缓存
     */
    public void clearMemory(Context context) {
        // 清理内存缓存  可以在UI主线程中进行
        Glide.get(context).clearMemory();
    }
}
