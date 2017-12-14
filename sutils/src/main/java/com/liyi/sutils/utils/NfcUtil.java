package com.liyi.sutils.utils;

import android.content.Intent;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * NFC 相关的工具类
 * <p>
 * SDK最低版本为14
 */
@RequiresApi(Build.VERSION_CODES.GINGERBREAD_MR1)
public class NfcUtil {
    private final String TAG = this.getClass().getSimpleName();
    private NfcAdapter mAdapter;
    /* NDEF 标签读取监听 */
    private OnReadFromNdefListener mReadNdefListener;
    /* NDEF 标签写入监听 */
    private OnWriteToNdefListener mWriteNdefListener;
    /* NDEF 标签删除监听 */
    private OnDeleteNdefListener mDeleteNdefListener;

    public static NfcUtil getInstance() {
        return NfcUtil.NfcUtilHolder.INSTANCE;
    }

    private static final class NfcUtilHolder {
        private static final NfcUtil INSTANCE = new NfcUtil();
    }

    private NfcUtil() {
        mAdapter = NfcAdapter.getDefaultAdapter(SUtils.getApp());
    }

    /**
     * 判断设备是否支持 NFC 功能
     *
     * @return {@code true}: 支持<br>{@code false}: 不支持
     */
    public boolean isSupportNfc() {
        if (mAdapter != null) {
            return true;
        }
        return false;
    }

    /**
     * 是否已经打开 NFC 功能
     *
     * @return {@code true}: 已经打开<br>{@code false}: 未打开
     */
    public boolean isNfcEnabled() {
        if (mAdapter != null && mAdapter.isEnabled()) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否有 NFC 相关的 intent
     * <p>
     * <p>NDEF_DISCOVERED: 只过滤固定格式的 NDEF 数据，例如：纯文本、指定协议（http、ftp、smb等）的URI等</p>
     * <p>
     * <p>TECH_DISCOVERED: 当 ACTION_NDEF_DISCOVERED 指定的过滤机制无法匹配 Tag 时，就会使用这种过滤机制进行匹配，
     * <p>这种过滤机制并不是通过 Tag 中的数据格式进行匹配的，而是根据 Tag 支持的数据存储格式进行匹配，
     * <p>因此这种过滤机制的范围更广</p>
     * <p>
     * <p>TAG_DISCOVERED: 当前面两种过滤机制都匹配失败后，系统就会利用这种过滤机制来处理，
     * <p>这种过滤机制用来处理未识别的 Tag（数据格式不对，而且 Tag 支持的格式也不匹配）</p>
     *
     * @param intent
     */
    public boolean isHasNfcIntent(Intent intent) {
        boolean isHasIntent = false;
        // NFC 的三重过滤机制
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())
                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())
                || NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            isHasIntent = true;
        }
        return isHasIntent;
    }

    /**
     * 获取 NFC 的适配器
     *
     * @return NfcAdapter
     */
    public NfcAdapter getNfcAdapter() {
        if (mAdapter == null) {
            mAdapter = NfcAdapter.getDefaultAdapter(SUtils.getApp());
        }
        return mAdapter;
    }

    /**
     * 从 NDEF 标签中读取数据
     *
     * @param tag
     * @return 读取到的标签信息
     */
    private String readFromNdef(Tag tag) {
        try {
            if (tag != null) {
                // 解析 Tag 获取到 NDEF 实例
                Ndef ndef = Ndef.get(tag);
                // 打开连接
                ndef.connect();
                // 获取 NDEF 消息
                NdefMessage message = ndef.getNdefMessage();
                // 将消息转换成字节数组
                byte[] data = message.toByteArray();
                // 将字节数组转换成字符串
                String str = new String(data, Charset.forName("UTF-8"));
                // 关闭连接
                ndef.close();
                return str;
            } else {
                if (mReadNdefListener != null) {
                    mReadNdefListener.unConnect();
                }
            }
        } catch (IOException e) {
            if (mReadNdefListener != null) {
                mReadNdefListener.readFail();
            }
            e.printStackTrace();
        } catch (FormatException e) {
            if (mReadNdefListener != null) {
                mReadNdefListener.readFail();
            }
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 向 NDEF 标签中写入信息
     *
     * @param tag
     * @param ndefRecords
     */
    public void writeToNdef(Tag tag, NdefRecord[] ndefRecords) {
        if (tag == null) {
            return;
        }
        NdefMessage ndefMessage = new NdefMessage(ndefRecords);
        // 获得写入大小
        int size = ndefMessage.toByteArray().length;
        // 判断是否是 NDEF 标签
        try {
            // 解析 TAG 获取到 NDEF 实例
            Ndef ndef = Ndef.get(tag);
            if (ndef != null) {
                // 开始连接
                ndef.connect();
                // 判断是否可写
                if (!ndef.isWritable()) {
                    if (mWriteNdefListener != null) {
                        mWriteNdefListener.unWritable();
                    }
                    return;
                }
                // 判断大小
                if (ndef.getMaxSize() < size) {
                    if (mWriteNdefListener != null) {
                        mWriteNdefListener.sizeOver(ndef);
                    }
                    return;
                }
                // 写入NDEF信息
                ndef.writeNdefMessage(ndefMessage);
                // 关闭连接
                ndef.close();
            } else {
                if (mWriteNdefListener != null) {
                    mWriteNdefListener.unConnect();
                }
            }
        } catch (IOException e) {
            if (mWriteNdefListener != null) {
                mWriteNdefListener.writeFail();
            }
            e.printStackTrace();
        } catch (FormatException e) {
            if (mWriteNdefListener != null) {
                mWriteNdefListener.writeFail();
            }
            e.printStackTrace();
        }
    }

    /**
     * 删除 NDEF 标签中的信息
     *
     * @param tag
     */
    private void deleteNdef(Tag tag) {
        try {
            if (tag != null) {
                // 新建一个里面无任何信息的 NdefRecord 实例
                NdefRecord nullNdefRecord = new NdefRecord(NdefRecord.TNF_MIME_MEDIA,
                        new byte[]{}, new byte[]{}, new byte[]{});
                NdefRecord[] records = {nullNdefRecord};
                NdefMessage message = new NdefMessage(records);
                // 解析 TAG 获取到 NDEF 实例
                Ndef ndef = Ndef.get(tag);
                // 打开连接
                ndef.connect();
                // 写入信息
                ndef.writeNdefMessage(message);
                // 关闭连接
                ndef.close();
            } else {
                if (mDeleteNdefListener != null) {
                    mDeleteNdefListener.unConnect();
                }
            }
        } catch (IOException e) {
            if (mDeleteNdefListener != null) {
                mDeleteNdefListener.deleteFail();
            }
            e.printStackTrace();
        } catch (FormatException e) {
            if (mDeleteNdefListener != null) {
                mDeleteNdefListener.deleteFail();
            }
            e.printStackTrace();
        }
    }

    /**
     * 设置 NDEF 标签读取监听
     *
     * @param listener
     */
    public void setReadNdefListener(OnReadFromNdefListener listener) {
        this.mReadNdefListener = listener;
    }

    /**
     * 设置 NDEF 标签写入监听
     *
     * @param listener
     */
    public void setWriteNdefListener(OnWriteToNdefListener listener) {
        this.mWriteNdefListener = listener;
    }

    /**
     * 设置 NDEF 标签信息删除监听
     *
     * @param listener
     */
    public void setDeleteNdefListener(OnDeleteNdefListener listener) {
        this.mDeleteNdefListener = listener;
    }

    /**
     * 从 NDEF 标签中读取数据监听
     */
    public interface OnReadFromNdefListener {
        /**
         * 没有连接成功
         */
        void unConnect();

        /**
         * 读取失败
         */
        void readFail();
    }

    /**
     * 向 NDEF 标签中写入数据监听
     */
    public interface OnWriteToNdefListener {
        /**
         * 没有连接成功
         */
        void unConnect();

        /**
         * 当前设备不支持写入
         */
        void unWritable();

        /**
         * 要写入的信息容量大于标签支持的最大容量
         *
         * @param ndef
         */
        void sizeOver(Ndef ndef);

        /**
         * 写入失败
         */
        void writeFail();
    }

    /**
     * 删除 NDEF 标签信息监听
     */
    public interface OnDeleteNdefListener {
        /**
         * 没有连接成功
         */
        void unConnect();

        /**
         * 删除失败
         */
        void deleteFail();
    }
}
