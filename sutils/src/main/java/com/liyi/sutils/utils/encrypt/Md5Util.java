package com.liyi.sutils.utils.encrypt;

import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密工具类
 * <p>
 * 参考链接：http://www.cnblogs.com/whoislcj/p/5885006.html
 */
public final class Md5Util {

    private Md5Util() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 计算字符串的 MD5 值
     *
     * @param plaintext 明文
     * @return 密文
     */
    public static String encrypt(String plaintext) {
        if (TextUtils.isEmpty(plaintext)) {
            return "";
        }
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(plaintext.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 计算文件的 MD5 值
     *
     * @param file 需要加密的文件
     * @return 加密后的数据
     */
    public static String encrypt(File file) {
        if (file == null || !file.isFile() || !file.exists()) {
            return "";
        }
        FileInputStream in = null;
        String result = "";
        byte buffer[] = new byte[8192];
        int len;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer)) != -1) {
                md5.update(buffer, 0, len);
            }
            byte[] bytes = md5.digest();

            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 采用nio方式进行 MD5 加密
     *
     * @param file 需要加密的文件
     * @return 加密后的数据
     */
    public static String encryptByNio(File file) {
        String result = "";
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(byteBuffer);
            byte[] bytes = md5.digest();
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 对字符串进行对此 MD5 加密，提高安全性
     *
     * @param string 需要加密的数据
     * @param times  加密次数
     * @return 加密后的数据
     */
    public static String encrypt(String string, int times) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        String md5 = encrypt(string);
        for (int i = 0; i < times - 1; i++) {
            md5 = encrypt(md5);
        }
        return encrypt(md5);
    }

    /**
     * MD5加盐
     * <p>
     * string + key（盐值 key）然后进行 MD5 加密
     *
     * @param string 需要加密的数据
     * @param slat 盐
     * @return 加密后的数据
     */
    public static String encrypt(String string, String slat) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest((string + slat).getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
