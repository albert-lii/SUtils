package com.liyi.sutils.utils.device;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.nfc.NfcAdapter;

/**
 * Nfc的工具类
 */
@TargetApi(10)
public class NfcUtil {
    private volatile static NfcUtil INSTANCE;
    private NfcAdapter mAdapter;

    private NfcUtil(Activity activity) {
        super();
        mAdapter = NfcAdapter.getDefaultAdapter(activity);
    }

    public static NfcUtil getInstance(Activity activity) {
        if (INSTANCE == null) {
            synchronized (NfcUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new NfcUtil(activity);
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 判断设备是否支持NFC功能
     *
     * @return
     */
    public boolean isSupportNfc() {
        if (mAdapter != null) {
            return true;
        }
        return false;
    }

    /**
     * 是否打开NFC功能
     *
     * @return
     */
    public boolean isNfcEnabled() {
        if (mAdapter != null && mAdapter.isEnabled()) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否有NFC相关的intent
     *
     * @param intent
     */
    public boolean isHasNfcIntent(Intent intent) {
        boolean isHasIntent = false;
        // NFC的三重过滤机制
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())
                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())
                || NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            isHasIntent = true;
        }
        return isHasIntent;
    }
}
