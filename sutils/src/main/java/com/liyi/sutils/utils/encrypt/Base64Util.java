package com.liyi.sutils.utils.encrypt;

import android.text.TextUtils;
import android.util.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Base64 加密工具类
 * <p>
 * 参考链接：http://www.cnblogs.com/whoislcj/p/5887859.html
 */
public final class Base64Util {

    private Base64Util() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * base64 加密字符串
     *
     * @param plaintext 明文
     * @return 加密后的数据
     */
    public static String encrypt(String plaintext) {
        if (TextUtils.isEmpty(plaintext)) {
            return "";
        }
        return Base64.encodeToString(plaintext.getBytes(), Base64.DEFAULT);
    }

    /**
     * base64 解码字符串
     *
     * @param ciphertext
     * @return 解密后的数据
     */
    public static String decrypt(String ciphertext) {
        if (TextUtils.isEmpty(ciphertext)) {
            return "";
        }
        return new String(Base64.decode(ciphertext, Base64.DEFAULT));
    }

    /**
     * base64 加密文件
     *
     * @param file 需要加密的文件
     * @return 解密后的数据
     */
    public static String encrypt(File file) {
        FileInputStream inputFile = null;
        try {
            inputFile = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length()];
            inputFile.read(buffer);
            inputFile.close();
            return Base64.encodeToString(buffer, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * base64解密文件
     *
     * @param ciphertext 密文
     * @param file       解码后的文件
     */
    public static void decrypt(String ciphertext, File file) {
        if (TextUtils.isEmpty(ciphertext) || file == null) {
            return;
        }
        FileOutputStream fos = null;
        try {
            byte[] decodeBytes = Base64.decode(ciphertext.getBytes(), Base64.DEFAULT);
            fos = new FileOutputStream(file);
            fos.write(decodeBytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
